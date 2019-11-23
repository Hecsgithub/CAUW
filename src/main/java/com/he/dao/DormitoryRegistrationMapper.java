package com.he.dao;

import java.util.List;

import com.he.po.Dormitory;
import com.he.po.DormitoryAllocation;
import com.he.po.DormitoryRegistration;

public interface DormitoryRegistrationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DormitoryRegistration record);

    int insertSelective(DormitoryRegistration record);

    DormitoryRegistration selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DormitoryRegistration record);

    int updateByPrimaryKey(DormitoryRegistration record);
    
	//1.根据学生ID或者宿舍ID获取分配记录
  	List<DormitoryRegistration> getDormitoryRegistrationByDR(DormitoryRegistration dr);
 
  //2.批量删除
  	int deleteDormitoryRegistrationByDormitoryId(List<Dormitory> ds);
//  	3.批量插入
  	int insertDormitoryRegistrationList(List<DormitoryRegistration> dr);
  	
//	4.删除指定学生
	int deleteDormitoryRegistrationByStudentId(String studentId);
}