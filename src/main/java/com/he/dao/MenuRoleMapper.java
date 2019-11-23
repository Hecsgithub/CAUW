package com.he.dao;

import java.util.List;

import com.he.po.Menu;
import com.he.po.MenuRole;
import com.he.po.UserRole;

public interface MenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);
    
    //1.新增菜单角色关系以批量方式
    int insertListMenuRole(List<MenuRole> mrs);
    

    
    
    //2.批量删除菜单角色关系
    int deleteListMenuRoleById(List<MenuRole> mrs);
     

    
    
   //3.批量修改菜单角色关系
    int updateListMenuRole(List<MenuRole> mrs);
    
    //4.批量删除菜单角色关系根据角色与菜单
    int deleteListMenuRole(MenuRole mr);
    
}