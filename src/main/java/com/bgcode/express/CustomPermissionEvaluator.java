package com.bgcode.express;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.bgcode.adm.dao.MenuRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator{ 
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	MenuRepository dao ;

	@Override
	public boolean hasPermission(Authentication authentication, Object target,
			Object permission) {
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authoritys =  (List<GrantedAuthority>)authentication.getAuthorities() ;
		for (GrantedAuthority authority : authoritys) {
			if(NumberUtils.isDigits(authority.getAuthority())){
				
				long lo = Long.valueOf(authority.getAuthority());
				long c = getPermission(dao.findSortByMenuId(target.toString()),permission.toString());
				return (lo&c)==c ;
			}
		}
		logger.warn("Denying 咏 " + authentication.getName() + " permission '"
				+ permission + "' on object " + target);
		
		return false;
	}

	/**
	 * @return false always
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId,
			String targetType, Object permission) {
		logger.warn("Denying 会 " + authentication.getName() + " permission '"
				+ permission + "' on object with Id '" + targetId + ",targetType: " +targetType);
		return true;
	}

	private Long getPermission(int sort,String permission){
		long s = sort;
		long y = 0;
		if(s>0){
			 y = 1<<(sort-1)*4+Opraters.valueOf(permission.toUpperCase()).ordinal();	
		}else{
			throw new IllegalArgumentException("顺序必须大于0") ;
		}
		return y;
	}

}
