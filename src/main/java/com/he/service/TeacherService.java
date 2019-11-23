package com.he.service;

import java.util.List;

import com.he.po.Role;
import com.he.po.Teacher;

public interface TeacherService {
	// 1.   根据教职工号查信息
    Teacher selectTeacherById(String id); 
    
    
    //5.根据角色信息查询教师信息
    List<Teacher> getAllTeacherByRole(Role role);
    
    
//6查询没有链接用户的教师
   	
   	List<Teacher> selectNoUsersTeacher();
}
