package com.he.service;

import java.util.List;

import com.he.po.Users;
import com.he.viewpo.NewUsers;

public interface UsersService {
	
//  1.根据username查询Users
  Users selectUsersByUserName(String username);
  
//  2.初始化用户表，将student_basic与teacher表中数据取到users表中
  int initUsers(List<Users> us);
  
//  3.获取所有用户
  List<NewUsers> getAllUsers(NewUsers nu);
  
//4.批量删除用户    
int deleteListUser(List<Integer> ids);

//5.新增用户
int addUsers(NewUsers nu);

//6.修改用户信息
int updateUsers(NewUsers nu);

//7.根据username与密码、类型查询Users
Users selectHasTwoUsers(NewUsers u);

// 8.查询不拥有某权限的用户
List<Users> selectnoaddroleuserinfo(String roleId,String type,String username,String utype);

}
