package com.bgcode.authenticationSuccessHandler;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
@Component
public class MultipleAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		Iterator<SimpleGrantedAuthority> it = (Iterator<SimpleGrantedAuthority>) authentication.getAuthorities().iterator();
		while(it.hasNext()){
			if("ROLE_GLY".equals(it.next().getAuthority())){
				setDefaultTargetUrl( "/admin/index");
				break ;
			}
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	

}
