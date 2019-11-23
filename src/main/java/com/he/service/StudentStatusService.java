package com.he.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import com.he.po.Major;
import com.he.po.StudentBasic;
import com.he.po.StudentStatus;
import com.he.po.Users;
import com.he.viewpo.DDMC;
import com.he.viewpo.MajorAndStudent;

public interface StudentStatusService {
	
	
		
	/**
	 * 1.初始化学籍表，对应基础信息表
	 */
	
	int insertInitStudentStatus(List<StudentStatus> sss) ;
	
	
	
	/**
	 * 2.修改学籍表，填补初始学号，班级
	 */
	
	int updateInitStudentIDandClass(List<StudentStatus> sss) ;
	
	/**
	 * 3.查询某个班级的所有学生
	 */
   	List<StudentStatus> selectStudentByClassID(String classid);
   	
   	/**
	 * 4.按专业查询分别查男女生人信息，返回专业学生视图
	 */
   	List<MajorAndStudent> selectStudentStatusByMajor();
   	
   	/**
	 * 5.按性别与班级专业查询所有学生学籍信息
	 */
   	List<StudentStatus> selectStudentBySexAndClass(@Param("sex")String sex,@Param("classid") String classid);
   	
   	
   //	6.根据参数查询所有学生
   	List<StudentStatus> getStudentStatus(DDMC ddmc);
   	
//	7.将某些学生移出某个班级
	int updatelistclassformstudent(List<Integer> id,String classId);

 	//8.统计所有专业未加入班级的学生  <专业，数量>
	   List<MajorAndStudent> selectMajorAndStudentSun(); 	
   	
	 //9.查询某专业未加入班级的学生
	   List<StudentStatus> getStudentByMajorId(String classId);
	   
	   
	 //10.根剧学生本身学号信息查询
	   List<StudentStatus> getStudentByStudentStatus( List<StudentStatus> sss);  
	   
	   
	 //11.查学号
	   StudentStatus getMaxStudentId(StudentStatus s);
	   
	   //12修改学号
	   int UpdateStudentId(StudentStatus s);  
	   
	   
	   //13 根据Users查询学籍信息
	   StudentStatus getStudentStatusByUsers(Users u);
	   
	   
	   
	 //14.根据学号查询学生
	    StudentStatus selectByPrimaryKey(String  studentId);
	   
	  //15根据学生基本ID查询学生学籍
	    StudentStatus getStudentStatusByStudentBasic(StudentBasic sb);
	   
	  //16导出某班级学生班级信息 
	    public ResponseEntity<byte[]> exportStudentStatusExcel(HttpServletResponse response
	    		,String ClassId,String filename);
}


