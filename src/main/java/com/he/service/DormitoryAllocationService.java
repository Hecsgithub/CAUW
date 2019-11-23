package com.he.service;

import java.util.List;

import com.he.po.Dormitory;
import com.he.po.DormitoryAllocation;

public interface DormitoryAllocationService {

	 //1.为某个班分配宿舍
  	int insertDivideDormitory(List<DormitoryAllocation> das);
  	
  //2.根据班级ID或者宿舍ID获取分配记录
  	List<DormitoryAllocation> getDormitoryAllocationByDA(DormitoryAllocation da);
  	
  //3.批量删除 -->
  int deleteDormitoryAllocationByDormitoryId(List<Dormitory> ds);
  	
 
}
