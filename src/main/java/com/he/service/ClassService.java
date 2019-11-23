package com.he.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.he.po.Class;
import com.he.po.Major;
import com.he.viewpo.MajorAndClass;
import com.he.viewpo.NewClass;

public interface ClassService {
	
	//1.初始化班级表(为每个专业插入班级号，代表)
    int insertIntClass(List<Class> classs);
    
    //2.获取专业名称及编号
    List<Major> selectMajorList(Class c);
    
//	3.获取专业及专业下的班级
    List<MajorAndClass> selectMajorAndClassList();
    
    //3.1.获取班级根据专业编号
    List<Class> selectClassByMajorID(String majorid); 
    
    
//    4.获取班级
    List<NewClass> getAllClass(NewClass nc);
    
    //5.修改某班级班主任   
    int updatedirector(String tid, String id);
    
  //6.根据参数删除班级
    int deleteClass(Class c);
    
    //7.根据班级编号获取班级
    Class selectByPrimaryKey(String classId);
    
    
//  <!--     8. 查询所有班级-->
  List<Class> selectAllClass(Class c);
  
//9.查该班级的专业中所有班级最大的编号的班级
  Class selectMajorMaxClass(String classId);
}
