package com.he.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.RoleMapper;
import com.he.dao.UserRoleMapper;
import com.he.po.Role;
import com.he.po.UserRole;
import com.he.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserRoleMapper userroleMapper;
	
	@Override
	public List<Role> selectRoleByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return this.roleMapper.selectRoleByUserId(userid);
	}

	@Override
	public int insertListRole(List<Role> rs) {
		// TODO Auto-generated method stub
		return this.roleMapper.insertListRole(rs);
	}

	@Override
	public int deleteListRole(List<Role> rs) {
		// TODO Auto-generated method stub
		int i=this.roleMapper.deleteListRole(rs);
		if(i>0) {
			//级联删除该角色下的所有用户角色关系
			int j=this.userroleMapper.deleteListUserRoleByRoleId(rs);
			if(j>0) {
				return j;
			}
		}
		return  -1;
	}

	@Override
	public int updateListRole(List<Role> rs) {
		// TODO Auto-generated method stub
		return  this.roleMapper.updateListRole(rs);
	}

	@Override
	public List<Role> selectAllRole(Role r) {
		// TODO Auto-generated method stub
		return this.roleMapper.selectAllRole(r);
	}

	@Override
	public Role selectHasTwoRole(Role r) {
		// TODO Auto-generated method stub
		return this.roleMapper.selectHasTwoRole(r);
	}

}
