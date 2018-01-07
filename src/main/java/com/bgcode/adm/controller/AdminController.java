package com.bgcode.adm.controller;

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
public class AdminController {
	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		request.setAttribute("includePath", "/admin/business/home");
		ModelAndView modelAndView = new ModelAndView("/admin/business/index");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public ModelAndView admin() {
		List<Duty> dutys = this.userRepo.findAll();
		ModelAndView modelAndView = new ModelAndView("admin/system/user");
		modelAndView.addObject("dutys", dutys);
		System.out.println("***************/admin**************");
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		System.out.println("***********" + request.getRequestURL() + "******************");
		return "login_reg";
	}

	// @RequestMapping(value="/loginform", method=RequestMethod.GET)
	// public String slogin(){
	// //System.out.println("*****************************");
	// return "admin/sduty";
	// }
	// @RequestMapping(value = "/admin/email" ,method = RequestMethod.GET)
	// public String getEmail() {
	// return "/admin/business/email";
	// }
}
