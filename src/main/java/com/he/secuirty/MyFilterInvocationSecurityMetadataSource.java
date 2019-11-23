package com.he.secuirty;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import com.he.po.Role;

import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.he.dao.MenuMapper;
import com.he.po.Major;
import com.he.po.Menu;
import com.he.secuirty.Service.MenuService;
import com.he.service.ClassService;

@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private MenuService menuService;
	
	AntPathMatcher antPathMatcher=new AntPathMatcher();
		    
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		String requestUrl=((FilterInvocation)object).getRequestUrl();
		/*
		 * 获取数据库中存放的所有地址与地址对应的角色，与当前地址比较，取出该地址的角色放入
		 */
		if(menuService!=null) {
			List<Menu> ms=this.menuService.getAllMenu();
			for(Menu m: ms) {
				   if (antPathMatcher.match(m.getUrl(), requestUrl)
		                    &&m.getRoles().size()>0) {
		                List<Role> roles = m.getRoles();
		                int size = roles.size();
		                String[] values = new String[size];
		                for (int i = 0; i < size; i++) {
		                    values[i] = roles.get(i).getName();
		                }
		                return SecurityConfig.createList(values);
		            }
		        }
		}
//	        没有匹配上的资源，都是登录访问
	        return SecurityConfig.createList("ROLE_LOGIN");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
