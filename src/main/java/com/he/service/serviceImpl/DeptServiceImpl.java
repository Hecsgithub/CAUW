package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.DeptMapper;
import com.he.po.Dept;
import com.he.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptMapper deptMapper;

	@Override
	public List<Dept> getAllDept() {
		// TODO Auto-generated method stub
		return this.deptMapper.getAllDept();
	}
}
