package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.DormitoryAllocationMapper;
import com.he.dao.DormitoryRegistrationMapper;
import com.he.po.Dormitory;
import com.he.po.DormitoryAllocation;
import com.he.service.DormitoryAllocationService;

@Service
public class DormitoryAllocationServiceImpl implements DormitoryAllocationService {

	@Autowired
	private DormitoryAllocationMapper dormitoryAllocationMapper;
	
	@Autowired
	private DormitoryRegistrationMapper dormitoryRegistrationMapper;
	
	@Override
	public int insertDivideDormitory(List<DormitoryAllocation> das) {
		// TODO Auto-generated method stub
		return this.dormitoryAllocationMapper.insertDivideDormitory(das);
	}

	@Override
	public List<DormitoryAllocation> getDormitoryAllocationByDA(DormitoryAllocation da) {
		// TODO Auto-generated method stub
		return this.dormitoryAllocationMapper.getDormitoryAllocationByDA(da);
	}

	@Override
	public int deleteDormitoryAllocationByDormitoryId(List<Dormitory> ds) {
		// TODO Auto-generated method stub
		this.dormitoryRegistrationMapper.deleteDormitoryRegistrationByDormitoryId(ds);
		return this.dormitoryAllocationMapper.deleteDormitoryAllocationByDormitoryId(ds);
	}

}
