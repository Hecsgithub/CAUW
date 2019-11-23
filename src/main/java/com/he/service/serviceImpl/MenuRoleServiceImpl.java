package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.MenuRoleMapper;
import com.he.po.MenuRole;
import com.he.service.MenuRoleService;

@Service
public class MenuRoleServiceImpl  implements MenuRoleService{

	@Autowired
	private MenuRoleMapper menurolemapper;
	
	@Override
	public int insertListMenuRole(List<MenuRole> mrs) {
		// TODO Auto-generated method stub
		return this.menurolemapper.insertListMenuRole(mrs);
	}

	@Override
	public int deleteListMenuRoleById(List<MenuRole> mrs) {
		// TODO Auto-generated method stub
		return this.menurolemapper.deleteListMenuRoleById(mrs);
	}

	@Override
	public int updateListMenuRole(List<MenuRole> mrs) {
		// TODO Auto-generated method stub
		return this.menurolemapper.updateListMenuRole(mrs);
	}

	@Override
	public int deleteListMenuRole(MenuRole mr) {
		// TODO Auto-generated method stub
		return this.menurolemapper.deleteListMenuRole(mr);
	}



}
