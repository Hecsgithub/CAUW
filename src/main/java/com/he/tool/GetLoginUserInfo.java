package com.he.tool;

import org.springframework.security.core.context.SecurityContextHolder;

import com.he.po.StudentBasic;
import com.he.po.Teacher;
import com.he.po.Users;

public class GetLoginUserInfo {
	
	public static Users getLoginUserInfo() {
		return (Users)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
