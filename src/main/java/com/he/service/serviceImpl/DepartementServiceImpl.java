package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.DepartmentMapper;
import com.he.po.Department;
import com.he.service.DepartementService;

@Service
public class DepartementServiceImpl implements DepartementService {

	@Autowired
	private DepartmentMapper departementMapper;

	@Override
	public List<Department> getAllDepartement(Department record) {
		// TODO Auto-generated method stub
		return this.departementMapper.getAllDepartement(record);
	}
	
}
