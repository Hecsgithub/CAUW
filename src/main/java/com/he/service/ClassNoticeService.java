package com.he.service;

import java.util.List;

import com.he.po.ClassNotice;

public interface ClassNoticeService {

	//1.插入通知
    int insert(ClassNotice record);
    
    //2.批量删除
    int deleteListClassNotice(List<Integer> ids);
    
    //3.修改通知
    int updateClassBotice(ClassNotice record);
    
  //4.查询通知
    List<ClassNotice> getAllClassBotice(String classId);
}
