package com.he.dao;

import java.util.List;

import com.he.po.Role;
import com.he.po.UserRole;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
//    1.根据用户编号查询角色信息
    List<Role> selectRoleByUserId(Integer userid);
    
   
    //2.新增角色以批量方式
    int insertListRole(List<Role> rs);

    
    
    //3.批量删除角色
    int deleteListRole(List<Role> rs);


    
    
   //4.批量修改角色
    int updateListRole(List<Role> rs);
    
    
    //5.获取用户信息，若无参数则获取所有
    List<Role> selectAllRole(Role r);
    
//    6.查重复
    Role selectHasTwoRole(Role r);
}