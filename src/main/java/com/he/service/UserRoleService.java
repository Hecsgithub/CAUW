package com.he.service;

import java.util.List;


import com.he.po.UserRole;


public interface UserRoleService {

	//1.新增用户角色关系以批量方式
    int insertListUserRole(List<UserRole> urs);
    

    //2.批量删除用户角色关系
    int deleteListUserRole(List<UserRole> urs);
     

   //3.批量修改删除用户角色关系
    int updateListUserRole(List<UserRole> urs);
    
    
  //6.根据UserRole查询整个授权表
    UserRole selectByUserRole(UserRole r);
}
