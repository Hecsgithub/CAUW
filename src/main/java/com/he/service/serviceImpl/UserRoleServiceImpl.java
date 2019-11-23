package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.UserRoleMapper;
import com.he.po.UserRole;
import com.he.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleMapper userrolemapper;
	
	
	@Override
	public int insertListUserRole(List<UserRole> urs) {
		// TODO Auto-generated method stub
		return this.userrolemapper.insertListUserRole(urs);
	}

	@Override
	public int deleteListUserRole(List<UserRole> urs) {
		// TODO Auto-generated method stub
		return this.userrolemapper.deleteListUserRole(urs);
	}

	@Override
	public int updateListUserRole(List<UserRole> urs) {
		// TODO Auto-generated method stub
		return this.userrolemapper.updateListUserRole(urs);
	}

	@Override
	public UserRole selectByUserRole(UserRole r) {
		// TODO Auto-generated method stub
		return this.userrolemapper.selectByUserRole(r);
	}

}
