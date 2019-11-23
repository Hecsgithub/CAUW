package com.he.dao;

import java.util.List;

import com.he.po.Dormitory;
import com.he.po.DormitoryAllocation;
import com.he.viewpo.DDMC;

public interface DormitoryAllocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DormitoryAllocation record);

    int insertSelective(DormitoryAllocation record);

    DormitoryAllocation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DormitoryAllocation record);

    int updateByPrimaryKey(DormitoryAllocation record);
    
    //1.为某个班分配宿舍
  	int insertDivideDormitory(List<DormitoryAllocation> das);
    
  	//2.根据班级ID或者宿舍ID获取分配记录
  	List<DormitoryAllocation> getDormitoryAllocationByDA(DormitoryAllocation da);
  	
  	//3.批量删除
  	int deleteDormitoryAllocationByDormitoryId(List<Dormitory> ds);
  	
//  	4.查混合宿舍ID
  	List<DormitoryAllocation> getManyClassDormitory(DDMC ddmc);
  	
//	5.查不是混合宿舍中不满员宿舍
	List<DormitoryAllocation> getSginManyClassDormitory(DDMC ddmc);
  	
  	
}