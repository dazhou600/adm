package com.bgcode.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CaptcatFilter implements Filter {
	String failureUrl;
	SimpleUrlAuthenticationFailureHandler failureHandler;

	public CaptcatFilter(String servletPath, String failureUrl) {
		this.failureUrl = failureUrl;
	}

	protected CaptcatFilter() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse rsp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		if ("POST".equalsIgnoreCase(req.getMethod()) && "/login".equals(req.getServletPath())) {
			System.out.println("!!!!!!!!!!!" + req.getSession().getAttribute("yzm"));
			if (req.getSession().getAttribute("yzm") != null) {
				if (req.getParameter("yzm") == null) {
					throw new CaptcatException("请输入验证码！");
				}
				if (!(req.getParameter("yzm").equals(req.getSession().getAttribute("yzm")))) {
					CaptcatException exception = new CaptcatException("验证码错误！！");
					failureHandler = new SimpleUrlAuthenticationFailureHandler(failureUrl);
					failureHandler.onAuthenticationFailure(req, rsp, exception);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
