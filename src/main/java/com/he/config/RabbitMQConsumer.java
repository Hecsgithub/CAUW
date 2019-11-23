package com.he.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.he.po.Dormitory;
import com.he.po.DormitoryRegistration;
import com.he.po.StudentStatus;
import com.he.service.DormitoryRegistrationService;
import com.he.service.DormitoryService;
import com.he.service.StudentStatusService;
import com.he.service.TeacherService;
import com.he.tool.ObjectToByte;
import com.he.viewpo.DDMC;
import com.he.viewpo.NewUsers;

@Component
//@RabbitListener(queues = RabbitMQConfig.queueName)
public class RabbitMQConsumer {

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

	// @RabbitHandler监听的队列名称
	@RabbitListener(queues = "topic.messages")
	public void process(byte[] bytes) throws Exception {
		StudentStatus s = (StudentStatus) ObjectToByte.dser(bytes);

		if (s.getStudentId().length() == 8) {// 原始学号
			int result = -1;
			String maxstudentId;

			StudentStatus ss = this.studentStatusService.getMaxStudentId(s);
			if (ss == null) {
				ss = new StudentStatus();
				ss.setId(s.getId());
				ss.setClassId(s.getClassId());
				ss.setC(s.getC());
				ss.setSb(s.getSb());
				maxstudentId = s.getStudentId();
				maxstudentId += "01";
				ss.setStudentId(maxstudentId);
				result = this.studentStatusService.UpdateStudentId(ss);
				if (result > 0) {
					s.setStudentId(maxstudentId);
				}
			} else {
				ss.setId(s.getId());
				maxstudentId = ss.getStudentId();
				maxstudentId = String.valueOf((Integer.parseInt(maxstudentId) + 1));
				ss.setStudentId(maxstudentId);
				result = this.studentStatusService.UpdateStudentId(ss);
				if (result > 0) {
					s.setStudentId(maxstudentId);
				}
			}
			// 分配宿舍
			DDMC ddmc = new DDMC();
			ddmc.setClassId(s.getClassId());
			ddmc.setSex(s.getSb().getSex());
			List<Dormitory> ds = this.dormitoryService.selectAllDormitory(ddmc);
			for (Dormitory d : ds) {
				List<DormitoryRegistration> drs = new ArrayList<>();
				DormitoryRegistration newdr = new DormitoryRegistration();
				newdr.setDormitoryId(d.getId());
				DormitoryRegistration ddr = new DormitoryRegistration();
				ddr.setDormitoryId(d.getId());
				List<DormitoryRegistration> dr = this.dormitoryRegistrationService.getDormitoryRegistrationByDR(ddr);
				if (dr == null || dr.size() < d.getNumber()) {// 该宿舍还有床位
					newdr.setStudentId(s.getStudentId());
					drs.add(newdr);
					int insr = this.dormitoryRegistrationService.insertDormitoryRegistrationList(drs);
					break;
				}
			}
		}
	}
}
