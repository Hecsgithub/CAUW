package com.he.secuirty.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.he.dao.StudentBasicMapper;
import com.he.dao.TeacherMapper;
import com.he.dao.UsersMapper;
import com.he.po.StudentBasic;
import com.he.po.Teacher;
import com.he.po.Users;
import com.he.tool.GetBCryptEncoder;

@Service
public class UserLoginService implements UserDetailsService {

	@Autowired
	private UsersMapper usersMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users u = this.usersMapper.selectUsersByUserName(username);
		if (u == null) {
			throw new UsernameNotFoundException("用户名不对");
		}
		u.setPassword(GetBCryptEncoder.getBCryptEncoderWord(u.getPassword()));
		return u;
	}
}
