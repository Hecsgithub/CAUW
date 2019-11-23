package com.he.service;

import java.util.List;

import com.he.po.Role;

public interface RoleService {
	
//  1.根据用户编号查询角色信息
  List<Role> selectRoleByUserId(Integer userid);
 
  //2.新增角色以批量方式
  int insertListRole(List<Role> rs);
  
  //3.批量删除角色
  int deleteListRole(List<Role> rs);
   
 //4.批量修改角色
  int updateListRole(List<Role> rs);
  

	//5.获取用户信息，若无参数则获取所有
	List<Role> selectAllRole(Role r);
	
//  6.查重复
  Role selectHasTwoRole(Role r);
}
