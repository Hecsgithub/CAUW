package com.he.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.he.po.RespBean;
import com.he.po.StudentBasic;
import com.he.po.StudentStatus;

public interface StudentBasicService {
	//	1.导入excel
	public RespBean addStudentBasic(String filename, MultipartFile file);

	//	2.导出excel
	public ResponseEntity<byte[]> exportExcel(HttpServletResponse response, String tablename);

	// 3.按性别与专业查询
	List<StudentBasic> selectStudentBasicBySexAndMajor(StudentBasic sb);

	// 4.按专业查询
	List<StudentBasic> selectStudentBasicByMajor(StudentBasic sb);

	// 5.查专业数量
	List<String> selectCountMajor();
	
	
// 	7.根据身份证号查信息
 	StudentBasic selectByIdNumber(String idNumber);
 	
 	//8.有条件的查询所有
 	 	List<StudentBasic> selectAllStudentBasicHas(StudentBasic sb);
 	 	
// 	    9.修改信息
 	 	int updateByPrimaryKeySelective(StudentBasic record);
 	 	
 	 //10.插入新生数据
 	    int insertSelective(StudentBasic record);
	
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
 	   	
 	   //16查重复,手机，父母手机，邮箱，身份证号，有一个相同就选取
 	   List<StudentBasic> selectStudentBasicHasIdentical(StudentBasic sb);
 	   	
 	   	
}
