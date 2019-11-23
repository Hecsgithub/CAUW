package com.he.viewpo;

import java.io.Serializable;
import java.util.List;

import com.he.po.Role;

/**
 * 替代原本因继承UserDetails接口无法返回成json数据的users类
 * @author Administrator
 *
 */
public class NewUsers  implements Serializable{
	
	
	 public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	private Integer id;
	
	/**
     * 用户名（学生为身份证号、教职工为教职工号）
     */
    private String username;

    /**
     * 用户密码（学生为姓名、教师为密码）
     */
    private String password;

    /**
     * student_basic或者teacher
     */
    private String type;
    
    private List<Role> roles;
}
