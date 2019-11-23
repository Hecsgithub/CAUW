package com.he.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.RespBean;
import com.he.po.Role;
import com.he.po.StudentBasic;
import com.he.po.Teacher;
import com.he.po.Users;
import com.he.service.StudentBasicService;
import com.he.service.TeacherService;
import com.he.service.UsersService;
import com.he.tool.GetLoginUserInfo;
import com.he.viewpo.NewUsers;





@RequestMapping("/system/sysuser")
@RestController
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private StudentBasicService sbservice;
	
	@Autowired
	private TeacherService tservice;
	
	Users u=null;
	
	//根据条件获取用户信息，若无则获取全部
	@RequestMapping("/getallusers")
	public List<NewUsers> getAllUsers(NewUsers nu) {
		List<NewUsers> nnu=this.usersService.getAllUsers(nu);
		return nnu;
		
	}
	//批量删除
	@Transactional
	@RequestMapping("/deletelistusers")
	public RespBean deleteListUsers(String id) {	
		u=GetLoginUserInfo.getLoginUserInfo();
		String[] tempid= id.split(",");

		List<Integer>idlist =new ArrayList<>();
		
		for(int s=0;s<tempid.length;s++) {
			if(tempid[s].equals(u.getId())) {
				return RespBean.error("批量删除失败！不能删除自身用户信息");
			}
			idlist.add(Integer.parseInt(tempid[s]));
		}

		int i=this.usersService.deleteListUser(idlist);
		if(i>0) {
			return RespBean.ok("批量删除成功！");
		}else {
			return RespBean.error("批量删除失败！");
		}	
	}
	
	//新增用户信息
	@Transactional
	@RequestMapping("/addusersinfo")
	public RespBean addUsers(NewUsers nu) {
		Users newu=this.usersService.selectHasTwoUsers(nu);
		if(newu!=null) {
			return RespBean.error("新增失败！该用户已存在！");
		}
		int i=this.usersService.addUsers(nu);
		if(i>0) {
			return RespBean.ok("成功新增用户！");
		}else {
			return RespBean.error("新增失败！");
		}	
	}
	
	//查询用户信息
		@RequestMapping("/selectusersinfo")
		public RespBean selectUsersInfo(NewUsers nu) {
			if(nu.getType().equals("student_basic")) {
	        	StudentBasic sb=this.sbservice.selectByIdNumber(nu.getUsername());
	        	return  RespBean.ok("新生",sb);
	        }
	        Teacher t=this.tservice.selectTeacherById(nu.getUsername()); 
	       
	        return RespBean.ok("教师",t);
		}
		//修改用户信息
		@Transactional
		@RequestMapping("/updateusersinfo")
		public RespBean updateUsersInfo(NewUsers nu) {
			int i=this.usersService.updateUsers(nu);
			if(i>0) {
				return RespBean.ok("成功修改新增用户！");
			}else {
				return RespBean.error("修改失败！");
			}	
		}
		
		//查询没有连接用户的账户信息
				@RequestMapping("/getnousersinfo")
				public List getnousersinfo() {
					List list=new ArrayList<>();
					
					List<Teacher> ts=this.tservice.selectNoUsersTeacher();
					
					List<StudentBasic> sbs=this.sbservice.selectNoUsersStudentBasic();
					list.add(ts);
					list.add(sbs);
					return list;
				}
		
		
}
