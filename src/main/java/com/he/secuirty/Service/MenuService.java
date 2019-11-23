package com.he.secuirty.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.he.dao.MenuMapper;
import com.he.po.Menu;
import com.he.tool.GetLoginUserInfo;

@Service
//开启ehcache缓存
//@CacheConfig(cacheNames="menu_ehcache")
public class MenuService {
	
	@Autowired
	private MenuMapper menuMapper;
	
//  1.根据取出所有菜单选项
//	@Cacheable
    public List<Menu> getAllMenu(){
		
		return this.menuMapper.getAllMenu();
    	
    }

//   2. 获取当前用户的权限
//	@Cacheable
    public List<Menu> getMenusByUsersId(){

		return this.menuMapper.getMenusByUsersId(GetLoginUserInfo.getLoginUserInfo().getId());
//		return this.menuMapper.getMenusByUsersId(1);

    }
    
}

