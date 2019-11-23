package com.he.secuirty;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// TODO Auto-generated method stub
		Collection<? extends GrantedAuthority> auths=authentication.getAuthorities();
		for(ConfigAttribute configAttribute : configAttributes) {
			if("ROLE_LOGIN".equals(configAttribute.getAttribute())&& authentication 
					instanceof UsernamePasswordAuthenticationToken) {
				return;
			}
			for(GrantedAuthority authority : auths) {
				if(configAttribute.getAttribute().equals(authority.getAuthority())) {
					return ;
				}
			}
		}
		throw new AccessDeniedException("权限不足!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
