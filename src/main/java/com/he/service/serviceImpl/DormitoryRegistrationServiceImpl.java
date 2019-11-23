package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.DormitoryRegistrationMapper;
import com.he.po.Dormitory;
import com.he.po.DormitoryRegistration;
import com.he.service.DormitoryRegistrationService;

@Service
public class DormitoryRegistrationServiceImpl implements DormitoryRegistrationService {

	@Autowired
	private DormitoryRegistrationMapper dormitoryRegistrationMapper;
	
	@Override
	public List<DormitoryRegistration> getDormitoryRegistrationByDR(DormitoryRegistration dr) {
		// TODO Auto-generated method stub
		return this.dormitoryRegistrationMapper.getDormitoryRegistrationByDR(dr);
	}

	@Override
	public int deleteDormitoryRegistrationByDormitoryId(List<Dormitory> ds) {
		// TODO Auto-generated method stub
		return this.dormitoryRegistrationMapper.deleteDormitoryRegistrationByDormitoryId(ds);
	}

	@Override
	public int insertDormitoryRegistrationList(List<DormitoryRegistration> dr) {
		// TODO Auto-generated method stub
		return this.dormitoryRegistrationMapper.insertDormitoryRegistrationList(dr);
	}

	@Override
	public int deleteDormitoryRegistrationByStudentId(String studentId) {
		// TODO Auto-generated method stub
		return this.dormitoryRegistrationMapper.deleteDormitoryRegistrationByStudentId(studentId);
	}

}
