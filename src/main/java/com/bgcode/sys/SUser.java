package com.bgcode.sys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.bgcode.adm.domain.Duty;
import com.bgcode.adm.domain.Menu;
import com.bgcode.adm.domain.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SUser extends Duty implements UserDetails {
	
    private static final long serialVersionUID = 1L;  
    
    private List<Menu> menus  ;
    
    public SUser(Duty suser) {  
        if(suser != null)  
        {  
            this.setId(suser.getId());  
            this.setName(suser.getName());  
            this.setEmail(suser.getEmail());  
            this.setPassword(suser.getPassword());  
            this.setRols(suser.getRols());  
        }         
    }  
      
    @Override  
    public Collection<? extends GrantedAuthority> getAuthorities() {  
          
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
        List<Menu> menus = this.getMenus();  
          
        if(menus != null)  
        {  
            for (Menu menu : menus) {  
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(menu.getPermission());  
                authorities.add(authority);  
            }  
        }  
        return authorities;  
    }  
  
    @Override  
    public String getPassword() {  
        return super.getPassword();  
    }  
  
    @Override  
    public String getUsername() {  
        return super.getName();  
    }  
  
    @Override  
    public boolean isAccountNonExpired() {  
        return true;  
    }  
  
    @Override  
    public boolean isAccountNonLocked() {  
        return true;  
    }  
  
    @Override  
    public boolean isCredentialsNonExpired() {  
        return true;  
    }  
  
    @Override  
    public boolean isEnabled() {  
        return true;  
    }

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}   

}
