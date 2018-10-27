package com.bgcode.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		System.out.print("******getNavCtgrTree******");
		//List<NavCtgr> navbars = rep.findNav();
		//ObjectMapper mapper = new ObjectMapper();
		//String njson = mapper.writeValueAsString(navbars);
		//request.setAttribute("navbars", njson);
		request.setAttribute("includePath", "/admin/business/cms/ctgrmng");
		return "/admin/business/index";
	}
	@RequestMapping(value = "/admin/cms/navctgr/async", produces = "application/text;charset=UTF-8")
	@ResponseBody
	public String getNavCtgrTreeAsync(HttpServletRequest request) throws JsonProcessingException {
		List<NavCtgr> navbars = rep.findNav();
		ObjectMapper mapper = new ObjectMapper();
		String njson = mapper.writeValueAsString(navbars);
		System.out.print("******getNavCtgrTree/asyc******");
		System.out.print("njson"+njson);
		return njson;
	}

	@RequestMapping(value = "/admin/cms/navctgr/add/{pid}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addNavCtgr(HttpServletRequest request, @PathVariable String pid) throws JsonProcessingException {
		String prm[] = pid.split(",");
		Integer cpid = Integer.parseInt(prm[0]);
		Integer pos = Integer.parseInt(prm[1]);
		int msg = rep.addNav(cpid, pos, prm[2]);
		System.out.print(pid + pos + prm[2]+"--"+msg);
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("msg", msg == 666 ? "增加成功！" : "增加失败！");
		map.put("msg", msg );
		ObjectMapper mapper = new ObjectMapper();
		String m = mapper.writeValueAsString(map);
		return m;

	}

	@RequestMapping(value = "/admin/cms/navctgr/del/{bid}", produces = "application/json;charset=UTF-8")
	@ResponseBody // 光它不返回信息还要JSON
	public String delNavCtgr(HttpServletRequest request, @PathVariable String bid) throws JsonProcessingException {
		Integer cbid = Integer.parseInt(bid);
		int msg = rep.delNav(cbid);
		System.out.println("******delNavCtgr******" + msg);
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("msg", msg == 666 ? "删除成功了" : "删除失败呵");
		map.put("msg", msg );
		ObjectMapper mapper = new ObjectMapper();
		String m = mapper.writeValueAsString(map);
		return m;// 必须返回绑定json对象,否则前台不返回调seccess函数!
	}
}
