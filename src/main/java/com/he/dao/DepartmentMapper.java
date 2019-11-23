package com.he.dao;

import java.util.List;

import com.he.po.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
//    1.获取所有系信息
   List<Department> getAllDepartement(Department record);
}