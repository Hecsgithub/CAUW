package com.he.controller;

import com.he.po.RespBean;
import com.he.po.Role;
import com.he.po.StudentBasic;
import com.he.po.StudentStatus;
import com.he.po.Teacher;
import com.he.po.Users;
import com.he.service.RoleService;
import com.he.service.StudentBasicService;
import com.he.service.StudentStatusService;
import com.he.service.TeacherService;
import com.he.tool.GetLoginUserInfo;
import com.he.tool.ObjectToByte;

import java.io.IOException;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginSecurity {
	
	@Autowired
	private StudentBasicService sbservice;
	
	@Autowired
	private StudentStatusService ssservice;
	
	@Autowired
	private TeacherService tservice;
	
	@Autowired
	private RoleService rservice;
		
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	   @RequestMapping("/login_p")
	    public RespBean login() {
	        return RespBean.error("尚未登录，请登录!");
	    }   
	   
	   //查询登录用户的详细信息
	   @RequestMapping("/selectuserinfobyid")
	    public RespBean selectuserinfobyid() throws Exception {
	        Users u=(Users)GetLoginUserInfo.getLoginUserInfo();
	        if(u.getType().equals("student_basic")) {
	        	StudentBasic sb=this.sbservice.selectByIdNumber(u.getUsername());
	        	//排队领取学号宿舍
	        	StudentStatus ss=this.ssservice.getStudentStatusByStudentBasic(sb);
	        	if (ss.getStudentId().length() == 8) {// 原始学号,发起排队
//	    			将排队取学号宿舍分配到队列中
	    			byte[] a = ObjectToByte.ser(ss);
	    			this.rabbitTemplate.convertAndSend("exchange", "topic.messages", a);
	        	}
	        	return  RespBean.ok("新生",sb);
	        }
	        Teacher t=this.tservice.selectTeacherById(u.getUsername()); 
	        String roles="";
	        List<Role> rs=u.getRoles();
	        for(Role r: rs){
	        	roles=roles+r.getName()+"、";
	        }
	        roles = roles.substring(0,roles.length() - 1);
	        return RespBean.ok(roles,t);
	    }
	   
}	
