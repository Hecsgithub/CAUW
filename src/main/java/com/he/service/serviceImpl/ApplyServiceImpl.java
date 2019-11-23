package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.ApplyMapper;
import com.he.po.Apply;
import com.he.service.ApplyService;

@Service
public class ApplyServiceImpl implements ApplyService {

	@Autowired
	private ApplyMapper applyMapper;
	@Override
	public int insertApply(Apply apply) {
		// TODO Auto-generated method stub
		return this.applyMapper.insertApply(apply);
	}

	@Override
	public int updateApply(Apply apply) {
		// TODO Auto-generated method stub
		return this.applyMapper.updateApply(apply);
	}

	@Override
	public int deleteApply(Apply apply) {
		// TODO Auto-generated method stub
		return this.applyMapper.deleteApply(apply);
	}

	@Override
	public List<Apply> selectApply(Apply apply) {
		// TODO Auto-generated method stub
		return this.applyMapper.selectApply(apply);
	}

}
