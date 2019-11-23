package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.TeacherMapper;
import com.he.po.Role;
import com.he.po.Teacher;
import com.he.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper tMapper;
	
	@Override
	public Teacher selectTeacherById(String id) {
		// TODO Auto-generated method stub
		return this.tMapper.selectTeacherById(id);
	}

	@Override
	public List<Teacher> getAllTeacherByRole(Role role) {
		// TODO Auto-generated method stub
		return this.tMapper.getAllTeacherByRole(role);
	}

	@Override
	public List<Teacher> selectNoUsersTeacher() {
		// TODO Auto-generated method stub
		return this.tMapper.selectNoUsersTeacher();
	}

}
