package com.he.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.UserRoleMapper;
import com.he.dao.UsersMapper;
import com.he.po.Users;
import com.he.service.UsersService;
import com.he.viewpo.NewUsers;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersMapper usersMapper; 
	
	
	@Autowired
	private UserRoleMapper userroleMapper;
	
	@Override
	public Users selectUsersByUserName(String username) {
		// TODO Auto-generated method stub
		return this.usersMapper.selectUsersByUserName(username);
	}

	@Override
	public int initUsers(List<Users> us) {
		// TODO Auto-generated method stub
		return this.usersMapper.initUsers(us);
	}

	@Override
	public List<NewUsers> getAllUsers(NewUsers nu) {
		// TODO Auto-generated method stub
		return this.usersMapper.getAllUsers(nu);
	}

	@Override
	public int deleteListUser(List<Integer> ids) {
		// TODO Auto-generated method stub
		int i=this.usersMapper.deleteListUser(ids);
		if(i>0) {
			List<Users> urs=new ArrayList<>();
			Users u=new Users();
			for(Integer id: ids) {
				u.setId(id);
				urs.add(u);
			}
			u=null;
			//级联删除该用户下的所有用户角色关系
			int j=this.userroleMapper.deleteListUserRoleByUserId(urs);
			urs=null;
			if(j>0) {
				return j;
			}
		}
		return  -1;

	}

	@Override
	public int addUsers(NewUsers nu) {
		// TODO Auto-generated method stub
		return this.usersMapper.addUsers(nu);
	}

	@Override
	public int updateUsers(NewUsers nu) {
		// TODO Auto-generated method stub
		return this.usersMapper.updateUsers(nu);
	}

	@Override
	public Users selectHasTwoUsers(NewUsers u) {
		// TODO Auto-generated method stub
		return this.usersMapper.selectHasTwoUsers(u);
	}

	@Override
	public List<Users> selectnoaddroleuserinfo(String roleId,String type,String username,String utype) {
		// TODO Auto-generated method stub
		return this.usersMapper.selectnoaddroleuserinfo(roleId,type,username,utype);
	}
	
}
