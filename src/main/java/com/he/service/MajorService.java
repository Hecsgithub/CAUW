package com.he.service;

import java.util.List;

import com.he.po.Major;
import com.he.po.Users;

public interface MajorService {

	 /**
     * 1.根据专业名称，获取专业id
     */
    String selectMajorIDBuName(String nmae);
    
    
    /**
     *2.根据专业编号，获取id
     */
    String selectIDBuMajorID(String majorid);
    
    /**
     *3.获取所有信息
     */
    List<Major> selectAllMajor(Major record);
    
    /**
     *4.Tree获取所有信息
     */
    List<Major> selectAllTreeMajor(Major record);
    
    /**
     *5.Tree获取所有信息、根据专业
     */
    List<Major> selectAllTreeMajorByUser(Users user);
    
    
//  <!-- 6/根据专业id获取专业 -->
  Major selectByPrimaryKey(String majirId);
}
