package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.ClassNoticeMapper;
import com.he.po.ClassNotice;

@Service
public class ClassNoticeServiceImpl implements com.he.service.ClassNoticeService {

	@Autowired
	private ClassNoticeMapper classNoticeMapper;
	
	@Override
	public int insert(ClassNotice record) {
		// TODO Auto-generated method stub
		return this.classNoticeMapper.insert(record);
	}

	@Override
	public int deleteListClassNotice(List<Integer> ids) {
		// TODO Auto-generated method stub
		return this.classNoticeMapper.deleteListClassNotice(ids);
	}

	@Override
	public int updateClassBotice(ClassNotice record) {
		// TODO Auto-generated method stub
		return this.classNoticeMapper.updateClassBotice(record);
	}

	@Override
	public List<ClassNotice> getAllClassBotice(String classId) {
		// TODO Auto-generated method stub
		return this.classNoticeMapper.getAllClassBotice(classId);
	}

}
