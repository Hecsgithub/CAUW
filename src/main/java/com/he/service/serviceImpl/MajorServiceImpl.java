package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.MajorMapper;
import com.he.po.Major;
import com.he.po.Users;
import com.he.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService {

	@Autowired
	private MajorMapper majorMapper;
	
	 /**
     * 1.根据专业名称，获取专业id
     */
	@Override
	public String selectMajorIDBuName(String nmae) {
		// TODO Auto-generated method stub
		return this.majorMapper.selectMajorIDBuName(nmae);
	}

	
	/**
     *2.根据专业编号，获取id
     */
	@Override
	public String selectIDBuMajorID(String majorid) {
		// TODO Auto-generated method stub
		return this.majorMapper.selectIDBuMajorID(majorid);
		
	}


	@Override
	public List<Major> selectAllMajor(Major major) {
		// TODO Auto-generated method stub
		return this.majorMapper.selectAllMajor(major);
	}


	@Override
	public List<Major> selectAllTreeMajor(Major record) {
		// TODO Auto-generated method stub
		return this.majorMapper.selectAllTreeMajor(record);
	}


	@Override
	public List<Major> selectAllTreeMajorByUser(Users user) {
		// TODO Auto-generated method stub
		return this.majorMapper.selectAllTreeMajorByUser(user);
	}


	@Override
	public Major selectByPrimaryKey(String majirId) {
		// TODO Auto-generated method stub
		return this.majorMapper.selectByPrimaryKey(majirId);
	}

}
