package com.he.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.he.po.Menu;

public interface MenuService {

//  1.所有菜单选项
    List<Menu> getAllMenu();
    
//    2.根据用户取得菜单
    List<Menu> getMenusByUsersId(Integer id);
    
    //3.新增用户角色关系以批量方式
    int insertListMenu(List<Menu> ms);
        
    //4.批量删除用户角色关系
    int deleteListMenu(List<Menu> ms);

   //5.批量修改删除用户角色关系
    int updateListMenu(List<Menu> ms);
           		
	 //6.所有菜单，前端显示使用
    List<Menu> selectAllMenu();
    
//  7.查询拥有与否某权限的菜单 
  List<Menu> selectnoaddrolemenuinfo(String roleId,String type);
  
  
//8.查询是否界面父节点为1
  
  Menu selectparentis1(int menuid);
  
  //9根据条件查组件
  Menu selectMenuIdByName(Menu m);
  
}
