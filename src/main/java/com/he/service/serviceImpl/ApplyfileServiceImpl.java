package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.ApplyfileMapper;
import com.he.po.Apply;
import com.he.po.Applyfile;
import com.he.service.ApplyfileService;

@Service
public class ApplyfileServiceImpl implements ApplyfileService {

	@Autowired
	private ApplyfileMapper applyfileMapper;
	@Override
	public int insertApplyFileByList(List<Applyfile> afs) {
		// TODO Auto-generated method stub
		return this.applyfileMapper.insertApplyFileByList(afs);
	}

	@Override
	public int deleteApplyFileByList(List<Applyfile> afs) {
		// TODO Auto-generated method stub
		return this.applyfileMapper.deleteApplyFileByList(afs);
	}

	@Override
	public List<Applyfile> selectApplyfile(Apply a) {
		// TODO Auto-generated method stub
		return this.applyfileMapper.selectApplyfile(a);
	}

	@Override
	public int updateAppliFile(Applyfile af) {
		// TODO Auto-generated method stub
		return this.applyfileMapper.updateAppliFile(af);
	}

}
