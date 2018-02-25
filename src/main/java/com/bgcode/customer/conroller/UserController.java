package com.bgcode.customer.conroller;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bgcode.adm.dao.UserRepository;
import com.bgcode.adm.domain.Duty;

@Controller
public class UserController {
	
	@RequestMapping(value = {"/index","/","/home"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "/home1" ;
	}




}
