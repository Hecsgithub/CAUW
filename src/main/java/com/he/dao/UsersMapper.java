package com.he.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.he.po.Users;
import com.he.viewpo.NewUsers;

public interface UsersMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Users record);

	int insertSelective(Users record);

	Users selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Users record);

	int updateByPrimaryKey(Users record);

//    1.根据username查询Users
	Users selectUsersByUserName(String username);

//    2.初始化用户表，将student_basic与teacher表中数据取到users表中
	int initUsers(List<Users> us);

//  3.获取所有用户
	List<NewUsers> getAllUsers(NewUsers nu);

//    4.批量删除用户    
	int deleteListUser(List<Integer> ids);

	// 5.新增用户
	int addUsers(NewUsers nu);

//6.修改用户信息
	int updateUsers(NewUsers nu);

//7.根据username与类型查询Users
	Users selectHasTwoUsers(NewUsers u);

	// 8.查询不拥有某权限的用户
	List<Users> selectnoaddroleuserinfo(@Param("roleId")String roleId,@Param("type")String type,@Param("username")String username,@Param("utype")String utype);

}