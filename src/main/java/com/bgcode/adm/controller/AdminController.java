package com.bgcode.adm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bgcode.adm.dao.UserRepository;
import com.bgcode.adm.domain.Duty;
import com.bgcode.adm.service.RegisterException;
import com.bgcode.adm.service.SuserService;

@Controller
public class AdminController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private SuserService uservice;

	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
	 request.setAttribute("includePath", "/admin/business/home");
		return "/admin/business/index";
	}

	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public ModelAndView admin() {
		List<Duty> dutys = this.userRepo.findAll();
		ModelAndView modelAndView = new ModelAndView("admin/system/user");
		modelAndView.addObject("dutys", dutys);
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegist(@Valid Duty user, Errors errors,HttpServletRequest request,HttpServletResponse resp) {
		if (errors.hasErrors()) {
			return "login_reg";
		}
		// userRepo.save(user);
		user.setLoginip(request.getRemoteAddr());
		try{
		uservice.regst(user);
		}catch (RegisterException e){
			//request.getSession().setAttribute("rmsg", "用户名已经存在!");
			//request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,e);
			request.setAttribute("rmsg", "用户名已经存在!");
			//request.getSession().setAttribute("rmsg","用户名已经存在!");
			return "login_reg";
		}
		return "redirect:/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		// System.out.println("***********" + request.getRequestURL() +
		// "******************");
		return "login_reg";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registForm() {
		return "login_reg";
	}
	@RequestMapping(value = "/ctgrmng", method = RequestMethod.GET)
	public String ctgrMng(HttpServletRequest request) {
		 request.setAttribute("includePath", "/admin/business/cms/ctgrmng");
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
