package com.he.dao;

import java.util.List;

import com.he.po.Role;
import com.he.po.UserRole;
import com.he.po.Users;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    //1.新增用户角色关系以批量方式
    int insertListUserRole(List<UserRole> urs);
    

    //2.批量删除用户角色关系
    int deleteListUserRole(List<UserRole> urs);
     

   //3.批量修改用户角色关系
    int updateListUserRole(List<UserRole> urs);

//  4.批量删除用户角色关系  根据用户id
    int deleteListUserRoleByUserId(List<Users> urs);

    
//    5.批量删除用户角色关系  根据角色id
    int deleteListUserRoleByRoleId(List<Role> urs);
    
    
    //6.根据UserRole查询整个授权表
    UserRole selectByUserRole(UserRole r);
    
}