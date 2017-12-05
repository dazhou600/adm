package com.bgcode.adm.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class RoleUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Set<Role> roles ;

	public RoleUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,TreeSet<Role> roles) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.roles = Collections.unmodifiableSet(roles) ;
	}

	public Set<Role> getRoles() {
		return roles;
	}

}
