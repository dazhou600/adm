package com.bgcode.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
		// List<NavCtgr> navbars = rep.findNav();
		// ObjectMapper mapper = new ObjectMapper();
		// String njson = mapper.writeValueAsString(navbars);
		// request.setAttribute("navbars", njson);
		request.setAttribute("includePath", "/admin/business/cms/ctgrmng");
		return "/admin/business/index";
	}

	@RequestMapping(value = "/admin/cms/navctgr/async", produces = "application/text;charset=UTF-8")
	@ResponseBody
	public String getNavCtgrTreeAsync(HttpServletRequest request) throws JsonProcessingException {
		List<NavCtgr> navbars = rep.findNav();
		ObjectMapper mapper = new ObjectMapper();
		String njson = mapper.writeValueAsString(navbars);
		return njson;
	}

	@RequestMapping(value = "/admin/cms/navctgr/add/{pid}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addNavCtgr(HttpServletRequest request, @PathVariable String pid) throws JsonProcessingException {
		String prm[] = pid.split(",");
		Integer cpid = Integer.parseInt(prm[0]);
		Integer pos = Integer.parseInt(prm[1]);
		int msg = rep.addNav(cpid, pos, prm[2]);
		System.out.print(pid + pos + prm[2] + "--" + msg);
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("msg", msg == 666 ? "增加成功！" : "增加失败！");
		map.put("msg", msg);
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
		map.put("msg", msg);
		ObjectMapper mapper = new ObjectMapper();
		String m = mapper.writeValueAsString(map);
		return m;// 必须返回绑定json对象,否则前台不返回调seccess函数!
	}

	@RequestMapping(value = "/admin/cms/navctgr/mov/{bid}/{tbid}", produces = "application/json;charset=UTF-8")
	@ResponseBody // 光它不返回信息还要JSON
	public String movNavCtgr(HttpServletRequest request, @PathVariable String bid, @PathVariable String tbid)
			throws JsonProcessingException {
		Integer cbid = Integer.parseInt(bid);
		Integer tgtbid = Integer.parseInt(tbid);
		int msg = rep.movNav(cbid, tgtbid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", msg);
		ObjectMapper mapper = new ObjectMapper();
		String m = mapper.writeValueAsString(map);
		return m;
	}

	@RequestMapping(value = "/admin/cms/navctgr/edit/{inf}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String editNavCtgr(HttpServletRequest request, @PathVariable String inf) throws JsonProcessingException {
		System.out.print("************" + inf);
		String prm[] = inf.split(",");
		Integer cpid = Integer.parseInt(prm[0]);
		String rname = prm[1];
		String addrs = prm[2];
		int msg = rep.updateNav(rname, addrs, cpid);
		System.out.print(cpid + rname + addrs + "--" + msg);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", msg);
		ObjectMapper mapper = new ObjectMapper();
		String m = mapper.writeValueAsString(map);
		return m;
	}

	/**
	 * 
	 * 文章管理 获取管理界面
	 */
	@RequestMapping(value = { "/admin/cms/ArticleMng" }, method = RequestMethod.GET)
	public String getArticleMngPage(HttpServletRequest request) {
		request.setAttribute("includePath", "/admin/business/cms/ArticleMng");
		return "/admin/business/index";
	}

	/**
	 * 
	 * 文章管理 上传文章、图片文件并保存
	 */
	@RequestMapping(value = { "/admin/cms/upArticle" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveArticle(@RequestParam String base64Data, HttpServletRequest request) {
		try {
			String dataPrix = "";
			String data = "";
			if (base64Data == null || "".equals(base64Data)) {
				throw new Exception("上传失败，上传图片数据为空");
			} else {
				String[] d = base64Data.split("base64,");
				if (d != null && d.length == 2) {
					dataPrix = d[0];
					data = d[1];
				} else {
					throw new Exception("上传失败，数据不合法");
				}
			}
			String suffix = "";
			if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
				suffix = ".jpg";
			} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {// data:image/x-icon;base64,base64编码的icon图片数据
				suffix = ".ico";
			} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {// data:image/gif;base64,base64编码的gif图片数据
				suffix = ".gif";
			} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {// data:image/png;base64,base64编码的png图片数据
				suffix = ".png";
			} else {
				throw new Exception("上传图片格式不合法");
			}
			String tempFileName = UUID.randomUUID().toString() + suffix;

			// 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
			byte[] bs = Base64Utils.decodeFromString(data);
			try {
				System.out.println(request.getServletContext().getRealPath("/article/upload"));
				FileUtils.writeByteArrayToFile(
						new File(request.getServletContext().getRealPath("/article/upload"), tempFileName), bs);
			} catch (Exception ee) {
				throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
			}
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}

	// @RequestMapping(value = "/admin/cms/file/upload", method =
	// RequestMethod.POST)
	// @ResponseBody
	public String upload(@RequestParam("files") MultipartFile file) {
		if (file.isEmpty()) {
			return "文件为空";
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		System.out.println("上传的文件名为：" + fileName);
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		System.out.println("上传的后缀名为：" + suffixName);
		// 文件上传路径
		String filePath = "/home/dada/applies/adm/target/classes/static/";
		fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		System.out.println("上传后目录为：" + dest.getParentFile());
		try {
			file.transferTo(dest);
			return filePath + fileName;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
