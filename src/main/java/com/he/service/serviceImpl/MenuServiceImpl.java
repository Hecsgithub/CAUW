package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.MenuMapper;
import com.he.po.Menu;
import com.he.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	
	@Override
	public List<Menu> selectAllMenu() {
		// TODO Auto-generated method stub
		return this.menuMapper.selectAllMenu();
	}


	@Override
	public List<Menu> getAllMenu() {
		// TODO Auto-generated method stub
		return this.menuMapper.getAllMenu();
	}


	@Override
	public List<Menu> getMenusByUsersId(Integer id) {
		// TODO Auto-generated method stub
		return this.menuMapper.getMenusByUsersId(id);
	}


	@Override
	public int insertListMenu(List<Menu> ms) {
		// TODO Auto-generated method stub
		return this.menuMapper.insertListMenu(ms);
	}


	@Override
	public int deleteListMenu(List<Menu> ms) {
		// TODO Auto-generated method stub
		return this.menuMapper.deleteListMenu(ms);
	}


	@Override
	public int updateListMenu(List<Menu> ms) {
		// TODO Auto-generated method stub
		return this.menuMapper.updateListMenu(ms);
	}


	@Override
	public List<Menu> selectnoaddrolemenuinfo(String roleId, String type) {
		// TODO Auto-generated method stub
		return this.menuMapper.selectnoaddrolemenuinfo(roleId, type);
	}


	@Override
	public Menu selectparentis1(int menuid) {
		// TODO Auto-generated method stub
		return this.menuMapper.selectparentis1(menuid);
	}


	@Override
	public Menu selectMenuIdByName(Menu m) {
		// TODO Auto-generated method stub
		return this.menuMapper.selectMenuIdByName(m);
	}

}
