package com.bgcode;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class }) 
public class Sufiguration {
	@Bean
	public DataSource dataSourcea() {
		org.springframework.jdbc.datasource.DriverManagerDataSource ds = new org.springframework.jdbc.datasource.DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/product?characterEncoding=gbk&useSSL=false");
		ds.setUsername("root");
		ds.setPassword("123456");
		return ds;
	}
	
//    @Bean
//    public TemplateResolver templateResolver() {
//        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
//        templateResolver.setPrefix("/templa#######################################################tes/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        return templateResolver;
//    }
    
//    @Bean
//    public SpringTemplateEngine templateEngine(ServletContextTemplateResolver templateResolver) {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver);
//        return templateEngine;
//    }
    
//    @Bean
//    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver);
//        templateEngine.addDialect(new SpringStandardDialect());
//        templateEngine.addDialect(new LayoutDialect());
//        return templateEngine;
//    }

}
