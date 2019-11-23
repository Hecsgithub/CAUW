package com.he.dao;

import java.util.List;

import com.he.po.StudentBasic;
import com.he.po.StudentStatus;

public interface StudentBasicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentBasic record);
//10.插入新生数据
    int insertSelective(StudentBasic record);

// 6.   修改原程序根据basic_id获取整个Basic
    StudentBasic selectByPrimaryKey(Integer id);
    
//    9.修改信息
    int updateByPrimaryKeySelective(StudentBasic record);

    int updateByPrimaryKey(StudentBasic record);
    
 // 1.批量插入
 	int insertExcelStudentBasic(List<StudentBasic> sb);

 	// 2.查询所有
 	List<StudentBasic> selectAllStudentBasic(StudentBasic sta);

 	// 3.按性别与专业查询
 	List<StudentBasic> selectStudentBasicBySexAndMajor(StudentBasic sb);

 	// 4.按专业查询
 	List<StudentBasic> selectStudentBasicByMajor(StudentBasic sb);
 	
 	//5.查专业数量
 	List<String> selectCountMajor();
 	
// 	7.根据身份证号查信息
 	StudentBasic selectByIdNumber(String idNumber);
 	
 	//8.有条件的查询所有
 	List<StudentBasic> selectAllStudentBasicHas(StudentBasic sb);
 	
 	
 	//11.批量删除数据
 	int deleteListStudentBasic(List<Integer> is);
 	
 	/**
   	 * 12.修改已分配好班级学生的分班状态
   	 * @param sss
   	 * @return
   	 */
   	int updateStateByStatus(List<StudentStatus> sss);
 	
   	
   	//13查某班级学生的基本信息
   	
   	List<StudentBasic> selectClassStudentBasic(StudentStatus ss);
   	
   	
   	
   	//14查询没有链接用户的学生
   	
   	List<StudentBasic> selectNoUsersStudentBasic();
   	
   	
   	//15.重置分班状态
   	
   	int updateStudentBasicState(List<Integer> ids);
   	
  //16查重复身份证号，有一个相同就选取
	   List<StudentBasic> selectStudentBasicHasIdentical(StudentBasic sb);
}