package com.he.service;

import java.util.List;

import com.he.po.Dormitory;
import com.he.viewpo.DDMC;

public interface DormitoryService {
	
	/**
	 * 1.查询 携带参数查询  宿舍 number属性做判断，为0：不满人  为1 ：满人   为空; 没住人
	 * 	栋、楼属性如不为空则可查询某栋某楼的宿舍    
	 * @param d
	 * @return
	 */
    List<Dormitory> fullDormitory(Dormitory d);
    
    
    //2.批量插入
    int insertListDormitory(List<Dormitory> ds);
    
    
  //3.批量删除
    int deleteinsertListDormitory(List<Dormitory> ds);
    
  //4.批量修改
    int updateListDormitory(List<Dormitory> ds);
    
   //5.根据条件查询所有
    List<Dormitory> selectAllDormitory(DDMC ddmc);
    
  //6.查询男或女所有未满员宿舍
    List<Dormitory> getIinsufficientDormitory(DDMC ddmc);
   
    //7.根据学号查询宿舍
    List<Dormitory> selectDormitoryByStudentId(String studentId);
    
    //8.获取宿舍楼数
    List<String> getDormitoryDong(String sex);
    
    //9.获取宿舍楼层根据楼栋
    List<String> getDormitoryFloor(String dong);
    
  //10.根据宿舍表编号查宿舍
    Dormitory selectByPrimaryKey(Integer id);
}
