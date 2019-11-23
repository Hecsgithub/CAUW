package com.he.dao;

import java.util.List;

import com.he.po.Dept;

public interface DeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
    
    //1.获取所有部门信息
    List<Dept> getAllDept();
}