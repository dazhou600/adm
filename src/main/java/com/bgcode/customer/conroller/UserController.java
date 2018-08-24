package com.bgcode.customer.conroller;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bgcode.adm.dao.UserRepository;
import com.bgcode.adm.domain.Duty;
import com.bgcode.cms.dao.NavCtgrRepository;
import com.bgcode.cms.entity.NavCtgr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	

	@Autowired
	NavCtgrRepository rep ;
	
	@RequestMapping(value = {"/index","/","/home"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request) throws JsonProcessingException {
		List<NavCtgr> navbars =  rep.findNav();
		ObjectMapper mapper = new ObjectMapper();
		String njson = mapper.writeValueAsString(navbars);
		request.setAttribute("navbars", njson);
		return "/home" ;
	}

	@RequestMapping(value = {"/repair"}, method = RequestMethod.GET)
	public String weixiu(HttpServletRequest request) {
		return "/repair" ;
	}


}
