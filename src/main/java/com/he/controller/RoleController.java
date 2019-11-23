package com.he.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Menu;
import com.he.po.MenuRole;
import com.he.po.RespBean;
import com.he.po.Role;
import com.he.po.UserRole;
import com.he.po.Users;
import com.he.service.MenuRoleService;
import com.he.service.MenuService;
import com.he.service.RoleService;
import com.he.service.UserRoleService;
import com.he.service.UsersService;
import com.he.tool.GetLoginUserInfo;

@RequestMapping("/system/sysrole")
@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserRoleService userroleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuRoleService menuroleService;

	Users u = null;
	// 根据条件获取角色信息，若无则获取全部

	@RequestMapping("/getallrole")
	public List<Role> getAllRole(Role nu) {
		return this.roleService.selectAllRole(nu);
	}

	// 批量删除
	@Transactional
	@RequestMapping("/deletelistrole")
	public RespBean deleteListRole(String id) {
		u = GetLoginUserInfo.getLoginUserInfo();
		Role r = u.getRoles().get(0);
		String[] tempid = id.split(",");
		;
		List<Role> idlist = new ArrayList<>();
		for (int s = 0; s < tempid.length; s++) {
			if (tempid[s].equals(r.getId())) {
				return RespBean.error("批量删除失败！不能删除自身角色");
			}
			Role newr = new Role();
			newr.setId(Integer.parseInt(tempid[s]));
			idlist.add(newr);
		}

		int i = this.roleService.deleteListRole(idlist);
		if (i > 0) {
			return RespBean.ok("批量删除成功！");
		} else {
			return RespBean.error("批量删除失败！");
		}
	}

	// 新增角色信息
	@Transactional
	@RequestMapping("/addroleinfo")
	public RespBean addRole(Role nu) {
		Role newnu = this.roleService.selectHasTwoRole(nu);
		if (newnu == null) {
			List<Role> idlist = new ArrayList<>();
			idlist.add(nu);
			int i = this.roleService.insertListRole(idlist);
			if (i > 0) {
				return RespBean.ok("成功新增角色！");
			} else {
				return RespBean.error("新增失败！");
			}
		}
		return RespBean.error("新增失败！已有相同角色名或者编码");
	}

	// 修改角色信息
	@Transactional
	@RequestMapping("/updateroleinfo")
	public RespBean updateRoleInfo(Role nu) {
		u = GetLoginUserInfo.getLoginUserInfo();
		for (Role r : u.getRoles()) {
			if (nu.getRoleId().equals(r.getRoleId())) {
				return RespBean.error("修改失败！不能修改自身角色！");
			}
		}

		List<Role> idlist = new ArrayList<>();
		idlist.add(nu);
		int i = this.roleService.updateListRole(idlist);
		if (i > 0) {
			return RespBean.ok("成功修改新增角色！");
		} else {
			return RespBean.error("修改失败！");
		}
	}

	// 有或没有拥有某角色的用户
	@RequestMapping("/getnoaddroleuserinfo")
	public List<Users> getnoaddroleuserinfo(String roleId, String type, String username, String utype) {
		List<Users> us = this.usersService.selectnoaddroleuserinfo(roleId, type, username, utype);
		return us;
	}

	// 有或没有拥有某角色的菜单
	@RequestMapping("/getnoaddrolemenuuserinfo")
	public List<Menu> getnoaddrolemenuuserinfo(String roleId, String type) {
		List<Menu> ms = this.menuService.selectnoaddrolemenuinfo(roleId, type);
		return ms;
	}

	// 对某角色删除或增加界面
	@Transactional
	@RequestMapping("/addordelrolemenu")
	public RespBean addordelrolemenu(String menuid, String roleid, String type) {
		String[] tempid = menuid.split(",");
		;
		List<MenuRole> mrs = new ArrayList<>();
		for (int a = 0; a < tempid.length; a++) {
			// 剔除父节点为1的界面
			Menu m = this.menuService.selectparentis1(Integer.parseInt(tempid[a]));
			if (m == null) {
				MenuRole mr = new MenuRole();
				mr.setRoleId(Integer.parseInt(roleid));
				mr.setMenuId(Integer.parseInt(tempid[a]));
				mrs.add(mr);
				mr = null;
			}
		}

		int i = -1;
		if ("add".equals(type)) {
			i = this.menuroleService.insertListMenuRole(mrs);
			if (i > 0) {
				return RespBean.ok("成功赋予角色界面！");
			} else {
				return RespBean.error("赋予失败！");
			}
		} else {
			for (MenuRole mr : mrs) {
				i = this.menuroleService.deleteListMenuRole(mr);
				if (i < 0) {
					break;
				}
			}
			if (i > 0) {
				return RespBean.ok("成功删除角色界面！");
			} else {
				return RespBean.error("删除失败！");
			}
		}
	}

	// 添加用户角色信息
	@Transactional
	@RequestMapping("/insertuserrole")
	public RespBean insertuserrole(String id, int role_id) {
		String[] tempid = id.split(",");
		;
		List<UserRole> urs = new ArrayList<>();

		for (int a = 0; a < tempid.length; a++) {
			UserRole ur = new UserRole();
			ur.setRoleId(role_id);
			ur.setUserId(Integer.parseInt(tempid[a]));
			urs.add(ur);
			ur = null;
		}
		int i = this.userroleService.insertListUserRole(urs);
		urs = null;
		if (i > 0) {
			return RespBean.ok("成功赋予用户角色！");
		} else {
			return RespBean.error("赋予失败！");
		}
	}

	// 删除用户角色信息
	@Transactional
	@RequestMapping("/deleteinsertuserrole")
	public RespBean deleteinsertuserrole(String id, int role_id) {
		String[] tempid = id.split(",");
		;
		List<UserRole> urs = new ArrayList<>();

		for (int a = 0; a < tempid.length; a++) {
			UserRole ur = new UserRole();
			ur.setRoleId(role_id);
			ur.setUserId(Integer.parseInt(tempid[a]));
			UserRole newur = this.userroleService.selectByUserRole(ur);
			urs.add(newur);
			newur = null;
			ur = null;
		}
		int i = this.userroleService.deleteListUserRole(urs);
		urs = null;
		if (i > 0) {
			return RespBean.ok("成功剥夺用户角色！");
		} else {
			return RespBean.error("剥夺失败！");
		}
	}

}
