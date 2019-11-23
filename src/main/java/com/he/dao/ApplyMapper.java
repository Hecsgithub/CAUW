package com.he.dao;

import java.util.List;

import com.he.po.Apply;

public interface ApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);
    
    //1插入一个请求
    int insertApply(Apply apply);
    
    
    //2修改一个请求
    int updateApply(Apply apply);
    
    
    //3删除一个请求
    int deleteApply(Apply apply);
    
    //4查询请求
    List<Apply> selectApply(Apply apply);
    
}