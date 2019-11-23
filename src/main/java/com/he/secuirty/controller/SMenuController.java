package com.he.secuirty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Menu;
import com.he.secuirty.Service.MenuService;
import com.he.tool.GetLoginUserInfo;

@RestController
public class SMenuController {
	
	@Autowired
	private MenuService menuService;
	
//	获取登录用户的权限
	@RequestMapping("/getloginmenu")
	 public List<Menu> selectMenuByUserID(){
		 return this.menuService.getMenusByUsersId();
	 }
}
