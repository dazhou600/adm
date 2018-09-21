package com.bgcode.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bgcode.cms.dao.NavCtgrRepository;
import com.bgcode.cms.entity.NavCtgr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CMSController {
	@Autowired
	NavCtgrRepository rep;

	@RequestMapping(value = "/admin/cms/navctgr", method = RequestMethod.GET)
	public String getNavCtgrTree(HttpServletRequest request) throws JsonProcessingException {
		List<NavCtgr> navbars = rep.findNav();
		ObjectMapper mapper = new ObjectMapper();
		String njson = mapper.writeValueAsString(navbars);
		request.setAttribute("navbars", njson);
		request.setAttribute("includePath", "/admin/business/cms/ctgrmng");
		return "/admin/business/index";
	}
}
