package com.he.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Menu;
import com.he.po.RespBean;
import com.he.service.MenuService;

@RequestMapping("/system/sysmenu")
@RestController
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/selectallmenu")
	public List<Menu> selectAllMenu(){
		return this.menuService.selectAllMenu();
	}
	
	@Transactional
	@RequestMapping("/deletemenu")
	public RespBean deleteMenu(Integer id){
		Menu m=new Menu();
		m.setId(id);
		List<Menu> ms=new ArrayList<>();
		ms.add(m);
		int i=this.menuService.deleteListMenu(ms);
		ms=null;
		m=null;
		if(i>0) {
			return RespBean.ok("删除成功！");
		}else {
			return RespBean.error("删除失败！");
		}	
	}
	
	@Transactional
	@RequestMapping("/insertmenu")
	public RespBean insertMenu(Menu m){
		List<Menu> ms=new ArrayList<>();
		ms.add(m);
		int i=this.menuService.insertListMenu(ms);
		ms=null;
		if(i>0) {
			Menu newm=this.menuService.selectMenuIdByName(m);
			return RespBean.ok("新增成功！",newm);
		}else {
			return RespBean.error("新增失败！");
		}	
	}
	
	@Transactional
	@RequestMapping("/updatemenu")
	public RespBean updateMenu(Menu m){
		List<Menu> ms=new ArrayList<>();
		ms.add(m);
		int i=this.menuService.updateListMenu(ms);
		ms=null;
		if(i>0) {
			return RespBean.ok("修改成功！");
		}else {
			return RespBean.error("修改失败！");
		}	
	}
}
