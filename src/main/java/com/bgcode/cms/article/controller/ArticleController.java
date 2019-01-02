package com.bgcode.cms.article.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = { "/admin/cms" })
public class ArticleController {

	@Value("${spring.http.multipart.location}")
	private String picTempDir;

	@Value("${constants.picRegex}")
	private String picRegex;

	@Autowired
	private ArticleRepository repo;

	/**
	 * /admin/cms/ArticleMng 文章管理 获取管理界面
	 */
	@RequestMapping(value = { "/ArticleMng" }, method = RequestMethod.GET)
	public String getArticleMngPage(HttpServletRequest request) {
		request.setAttribute("includePath", "/admin/business/cms/ArticleMng");
		return "/admin/business/index";
	}

	/**
	 * /admin/cms/ArticleMng 文章管理 获取修改文章界面
	 */
	@RequestMapping(value = { "/editArticle" }, method = RequestMethod.GET)
	public String editArticlePage(HttpServletRequest request) {
		request.setAttribute("includePath", "/admin/business/cms/editarticle");
		request.setAttribute("article",repo.findOne(1));
		return "/admin/business/index";
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
		Map<String, String> m = new HashMap<String, String>();
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
					m.put("filename", "http://localhost:8080/imgs/temp/" + fileName);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				m.put("warning", "图片已经存在！");
				m.put("filename", "http://localhost:8080/imgs/temp/" + fileName);
			}
		} else {
			m.put("error", "上传图片名称含非法字符或非图片文件！");
		}

		ObjectMapper mapper = new ObjectMapper();
		String msg = null;
		try {
			msg = mapper.writeValueAsString(m);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 
	 * 文章管理 上传文章标题、作者、及图片文件移动图片文件夹
	 * 
	 * 
	 */
	@RequestMapping(value = { "/saveArticle" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveArticle(@RequestBody @Validated Article article, BindingResult result) {
		String msg = null;
		Map<String, Object> m = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuilder sb = new StringBuilder();
			sb.append("有" + errors.size() + "错误：");
			for (int i = 0; i < errors.size(); i++) {
				sb.append(i + 1);
				sb.append(errors.get(i).getDefaultMessage());
				if (i + 1 < errors.size()) {
					sb.append(",");
				}
			}
			m.put("errors", sb.toString());
		} else {
			if(article.getId()==null){
				article.setCreateDate(new Date());
			}
			Article arti = repo.save(article);
			m.put("entity", arti);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			msg = mapper.writeValueAsString(m);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 删除图片
	 */
	@RequestMapping(value ="/delArticlePic/", method = RequestMethod.POST)
	@ResponseBody
	public String delArticle(@RequestParam("imgSrc") String imgsrc) {
		String msg = null;
		try {
			//对乱码处理
			imgsrc= URLDecoder.decode(imgsrc, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Map<String, String> m = new HashMap<String, String>();
		if(StringUtils.isBlank(imgsrc)||StringUtils.indexOf(imgsrc, "/")==-1){
			return "{\"msg\": \"图片格式："+imgsrc+" 不合法！\"}";
		}else{
			String fileName = StringUtils.substringAfterLast(imgsrc, "/");
				File file = new File(picTempDir+fileName);
				if(file.exists()){
					if(FileUtils.deleteQuietly(file)){
						m.put("msg", "删除图片成功");
					}
				}
		}
		
		System.out.println(imgsrc+"删除图片");
		ObjectMapper mapper = new ObjectMapper();
		try {
			msg = mapper.writeValueAsString(m);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return msg;
	}

}
