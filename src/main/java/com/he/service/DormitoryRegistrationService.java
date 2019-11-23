package com.he.service;

import java.util.List;

import com.he.po.Dormitory;
import com.he.po.DormitoryRegistration;

public interface DormitoryRegistrationService {
	
	//1.根据学生ID或者宿舍ID获取分配记录
  	List<DormitoryRegistration> getDormitoryRegistrationByDR(DormitoryRegistration dr);

  //2.批量删除根据宿舍号删除
  	int deleteDormitoryRegistrationByDormitoryId(List<Dormitory> ds);
//  	3.批量插入
  	int insertDormitoryRegistrationList(List<DormitoryRegistration> dr);
  	
//  	4.删除指定学生
  	int deleteDormitoryRegistrationByStudentId(String studentId);
  	
}
