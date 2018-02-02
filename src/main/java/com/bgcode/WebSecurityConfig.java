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
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("WZGL","QXYHGL","XTSZ","FWQJK")
		.antMatchers("/sys/**").hasAnyRole("QXYHGL","XTSZ","FWQJK")
		.anyRequest().permitAll()
				// .anyRequest().authenticated().withObjectPostProcessor(getExpressionHandler())
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/index").successHandler(AuthenticationSuccessHandler())
				.and().addFilterBefore(new CaptcatFilter("/login", "/login?error"), UsernamePasswordAuthenticationFilter.class);
		// http.authorizeRequests().anyRequest().authenticated().expressionHandler(webSecurityExpressionHandler());
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT u_name , password , isactive FROM duty where u_name=? ")
.authoritiesByUsernameQuery("SELECT u_name ,r_name FROM user_role where u_name=?")
.groupAuthoritiesByUsername("SELECT o_id,u_name,operat FROM operatiom where u_name=?")
//				.authoritiesByUsernameQuery("SELECT distinct u.u_name uname, m.permission permission "
//						+ "FROM duty u, menu m, role r, role_menu rm, user_role ur "
//						+ "where u.u_name=? and u.u_id=ur.user_id and ur.role_id=r.r_id and r.r_id=rm.role_id and rm.menu_id=m.menu_id")
//				.groupAuthoritiesByUsername(
//						"SELECT r.r_id,r.data_operation,r.r_name " + "FROM product.role r,user_role ur ,duty d "
//								+ "where d.u_name=? and d.u_id=ur.user_id and ur.role_id=r.r_id")
				.passwordEncoder(new StandardPasswordEncoder("wztq"));
		// auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());//
		// //不删除凭据，以便记住用户
		// auth.eraseCredentials(false);
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

	//登录跳转
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

	private AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setHideUserNotFoundExceptions(false);
		return provider;

	}

	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	public JdbcTokenRepositoryImpl tokenRepository() {
		JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
		j.setDataSource(dataSource);
		return j;
	}

	// @Bean
	// public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	// return new JdbcTemplate(dataSource);
	// }
	// public LoginSuccessHandler loginSuccessHandler() {
	// return new LoginSuccessHandler();// code6 }
	// }
}
