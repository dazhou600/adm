package com.bgcode;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.bgcode.authenticationSuccessHandler.MultipleAuthenticationSuccessHandler;
import com.bgcode.express.CustomPermissionEvaluator;
import com.bgcode.filter.CaptcatFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("dataSourcea")
	private DataSource dataSource;
	@Autowired
	private CustomPermissionEvaluator permissionEvaluator;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("WZGL", "QXYHGL", "XTSZ", "FWQJK")
				.antMatchers("/sys/**").hasAnyRole("QXYHGL", "XTSZ", "FWQJK").anyRequest().permitAll()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/index")
				.successHandler(AuthenticationSuccessHandler()).and().addFilterBefore(
						new CaptcatFilter("/login", "/login?error"), UsernamePasswordAuthenticationFilter.class);
		// http.authorizeRequests().anyRequest().authenticated().expressionHandler(webSecurityExpressionHandler());
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT u_name , password , isactive FROM duty where u_name=? ")
				.authoritiesByUsernameQuery("SELECT u_name ,r_name FROM user_role where u_name=?")
				.groupAuthoritiesByUsername("SELECT o_id,u_name,operat FROM operatiom where u_name=?")
				.passwordEncoder(new StandardPasswordEncoder("wztq"));
	}

	/*
	 * 表达式控制器
	 */
	@Bean(name = "expressionHandler")
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		webSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator);
		return webSecurityExpressionHandler;
	}

	// 登录跳转
	@Bean
	public AuthenticationSuccessHandler AuthenticationSuccessHandler() {
		AuthenticationSuccessHandler successHandler = new MultipleAuthenticationSuccessHandler();
		return successHandler;
	}

	@Bean
	public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new SpringSecurityDialect());
		return templateEngine;
	}

	// public LoginSuccessHandler loginSuccessHandler() {
	// return new LoginSuccessHandler();// code6 }
	// }
}
