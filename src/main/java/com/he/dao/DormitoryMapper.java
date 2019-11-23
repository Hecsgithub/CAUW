package com.he.dao;

import java.util.List;

import com.he.po.Dormitory;
import com.he.viewpo.DDMC;

public interface DormitoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dormitory record);

    int insertSelective(Dormitory record);

    //10.根据宿舍表编号查宿舍
    Dormitory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dormitory record);

    int updateByPrimaryKey(Dormitory record);
    
    //    1.查询 携带参数查询  宿舍 number属性必不能为空，只能为0：不满人或者1满人
    List<Dormitory> fullDormitory(Dormitory d);
    
    
    //2.批量插入
    int insertListDormitory(List<Dormitory> ds);
    
    
  //3.批量删除
    int deleteinsertListDormitory(List<Dormitory> ds);
    
  //4.批量修改
    int updateListDormitory(List<Dormitory> ds);
    
   //5.根据专业或班级或宿舍条件查询所有
    List<Dormitory> selectAllDormitory(DDMC ddmc);
    
    //7.根据学号查询宿舍
    List<Dormitory> selectDormitoryByStudentId(String studentId);
    
    //8.获取宿舍楼数
    List<String> getDormitoryDong(String sex);
    
    //9.获取宿舍楼层根据楼栋
    List<String> getDormitoryFloor(String dong);
    
}