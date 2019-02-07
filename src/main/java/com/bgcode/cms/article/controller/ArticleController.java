package com.bgcode.cms.article.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.FieldError;
import com.bgcode.cms.dao.ArticleRepository;
import com.bgcode.cms.entity.Article;
import com.bgcode.cms.service.ArticleSevice;
import com.bgcode.websoketmsg.service.ReturnMsgUtile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = { "/admin/cms" })
public class ArticleController {

	@Value("${spring.http.multipart.location}")
	private String picTempDir;

	@Value("${constants.picRegex}")
	private String picRegex;

	@Value("${constants.template}")
	private String baseTemplate;

	@Autowired
	private ArticleRepository repo;

	@Autowired
	private ArticleSevice articlesSevice;

	/**
	 * /admin/cms/ArticleMng 文章管理 获取文章列表
	 */
	@RequestMapping(value = { "/ArticleMng" }, method = RequestMethod.GET)
	public String getArticleMngPage(HttpServletRequest request) {
		request.setAttribute("includePath", "/admin/business/cms/articlelist");
		// request.setAttribute("includePath",
		// "/admin/business/cms/ArticleMng");
		return baseTemplate;
	}

	@RequestMapping(value = { "/listArticle" }, method = RequestMethod.POST)
	@ResponseBody
	public String listArticle(HttpServletRequest request) {
		List<Article> articles = repo.findAll();
		int total = articles.size();
		String aa = "{\"data\":[" + StringUtils.join(articles, ",") + "]}";
		return aa;
	}

	/**
	 * 获取增加文章界面
	 */
	@RequestMapping(value = { "/addArticle" }, method = RequestMethod.GET)
	public String getAddArticlePage(HttpServletRequest request) {
		request.setAttribute("includePath", "/admin/business/cms/addArticle");
		return baseTemplate;
	}

	/**
	 * 文章管理 获取修改文章界面
	 */
	@RequestMapping(value = { "/editArticle/{id}" }, method = RequestMethod.GET)
	public String editArticlePage(HttpServletRequest request, @PathVariable String id) {
		request.setAttribute("includePath", "/admin/business/cms/editarticle");
		Integer aid = Integer.parseInt(id);
		request.setAttribute("article", repo.findArticle(aid).get(0));
		return baseTemplate;
	}

	/**
	 * 
	 * 文章管理 上传图片文件保存到临时文件夹
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = { "/upArticlePic" }, method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
		// System.out.println("上传的文件：" + file);
		Map<String, String> msgMap = new HashMap<String, String>();
		if (file.isEmpty()) {
			throw new IOException("File '" + file + "' 文件不存在!");
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 编译正则表达式
		Pattern pattern = Pattern.compile(picRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(fileName);
		if (matcher.matches()) {
			File dest = new File(picTempDir + fileName);
			System.out.println("上传的文件名为：" + picTempDir + fileName);
			if (!dest.exists()) {// 文件不存在
				// 检测是否存在目录
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				try {
					file.transferTo(dest);
					msgMap.put("filename", "http://localhost:8080/upload/pic/" + fileName);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				msgMap.put("warning", "图片已经存在！");
				msgMap.put("filename", "http://localhost:8080/upload/pic/" + fileName);
			}
		} else {
			msgMap.put("error", "上传图片名称含非法字符或非图片文件！");
		}

		return ReturnMsgUtile.toJSONMsg(msgMap);
	}

	/**
	 * 
	 * 文章管理 上传文章标题、作者、及图片文件
	 * 
	 * 
	 */
	@RequestMapping(value = { "/saveArticle" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveArticle(@RequestBody @Validated Article article, BindingResult result) {
		String msg = null;
		Map<String, String> msgMap = new HashMap<String, String>();
		msg = ReturnMsgUtile.extrMsg(result);
		if (msg != null) {
			msgMap.put("errors", msg);
			return ReturnMsgUtile.toJSONMsg(msgMap);
		}
		int count = articlesSevice.saveArticle(article);
		if (count == 1) {
			msgMap.put("seccess", article.getTitle());
		} else {
			msgMap.put("errors", article.getTitle() + ":更新失败");
		}
		return ReturnMsgUtile.toJSONMsg(msgMap);
	}

	/**
	 * 删除图片
	 */
	@RequestMapping(value = "/delArticlePic/{imgSrc}", method = RequestMethod.GET)
	@ResponseBody
	public String delArticlePic(@PathVariable  String imgsrc) {
//		try {
//			// 对乱码处理
//			imgsrc = URLDecoder.decode(imgsrc, "utf-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		System.out.println(imgsrc);
		Map<String, String> msgMap = new HashMap<String, String>();
		if (StringUtils.isBlank(imgsrc) || StringUtils.indexOf(imgsrc, "/") == -1) {
			return "{\"msg\": \"图片格式：" + imgsrc + " 不合法！\"}";
		} else {
			String fileName = StringUtils.substringAfterLast(imgsrc, "/");
			File file = new File(picTempDir + fileName);
			if (file.exists()) {
				if (FileUtils.deleteQuietly(file)) {
					msgMap.put("msg", "删除图片成功");
				}
			}
		}
		return ReturnMsgUtile.toJSONMsg(msgMap);
	}

	/**
	 * 删除文章
	 */
	@RequestMapping(value = { "/delArticle/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public String delArticle(HttpServletRequest request, @PathVariable String id) {
		Map<String, String> msgMap = new HashMap<String, String>();
		try {
			Integer aid = Integer.parseInt(id);
			repo.delete(aid);
			msgMap.put("msg", "文章：" + aid + "删除成功！");
		} catch (NumberFormatException e) {
			msgMap.put("error", e.getMessage());
		}
		return ReturnMsgUtile.toJSONMsg(msgMap);
	}
}
