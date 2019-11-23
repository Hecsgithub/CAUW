package com.he.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.config.RabbitMQConfig;
import com.he.po.Class;
import com.he.po.ClassNotice;
import com.he.po.RespBean;
import com.he.po.StudentStatus;
import com.he.po.Teacher;
import com.he.po.Users;
import com.he.po.Dormitory;
import com.he.po.DormitoryRegistration;

import com.he.viewpo.DDMC;
import com.rabbitmq.client.AMQP.Channel;
import com.he.service.ClassNoticeService;
import com.he.service.DormitoryRegistrationService;
import com.he.service.DormitoryService;
import com.he.service.StudentStatusService;
import com.he.service.TeacherService;
import com.he.tool.GetLoginUserInfo;
import com.he.tool.ObjectToByte;

@RequestMapping("/student/status")
@RestController
public class StudentStatusController {

	@Autowired
	private StudentStatusService studentStatusService;

	@Autowired
	private DormitoryService dormitoryService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private DormitoryRegistrationService dormitoryRegistrationService;

	@Autowired
	private ClassNoticeService classNoticeService;
	
	
	/**
	 * 3.根据班级id获取全班同学数据
	 * 
	 * @return
	 */
	@RequestMapping("/selectstudentbyclass")
	public List<StudentStatus> selectstudentbyclass(Class clas) {
		return this.studentStatusService.selectStudentByClassID(clas.getClassId());
	}

	// 编排学号
	@RequestMapping("/initStudent")
	public List getMaxStudentId() throws IOException, Exception {
		List list = new ArrayList<>();
		Users u = GetLoginUserInfo.getLoginUserInfo();
		StudentStatus s = this.studentStatusService.getStudentStatusByUsers(u);
		Teacher t = this.teacherService.selectTeacherById(s.getC().getDirector());
		List<ClassNotice> cns=this.classNoticeService.getAllClassBotice(s.getClassId());
		
		look: if (s.getStudentId().length() == 8) {// 原始学号
//			将排队取学号宿舍分配到队列中
			byte[] a = ObjectToByte.ser(s);
			this.rabbitTemplate.convertAndSend("exchange", "topic.messages", a);

			System.out.println("开始延时");
			Thread.sleep(1000);//延时1秒等待结果
			System.out.println("结束延时");
			StudentStatus s1 = this.studentStatusService.getStudentStatusByUsers(u);
			if (s1.getStudentId().length() > 8) {// 该学生学号已领取到手，
				List<Dormitory> drs = this.dormitoryService.selectDormitoryByStudentId(s1.getStudentId());
				if (drs.size() > 0) {// 宿舍也领取到
					list.add(drs.get(0));
					list.add(s1);
					break look;
				} else {// 学号到手，宿舍没有，重置学号为初始学号，重新排队领取学号宿舍
					s1.setStudentId(s.getStudentId());
					this.studentStatusService.UpdateStudentId(s1);
					this.rabbitTemplate.convertAndSend("exchange", "topic.messages", a);
				}
			}

		} else {
			list.add(s);
			List<Dormitory> drs = this.dormitoryService.selectDormitoryByStudentId(s.getStudentId());
			if(drs.size()>0) {
				list.add(drs.get(0));
			}else {
				list.add(null);
			}
		}
		
		list.add(t);
		list.add(cns);
		return list;
	}

}
