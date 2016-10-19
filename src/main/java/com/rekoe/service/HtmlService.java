package com.rekoe.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.plugins.view.freemarker.FreeMarkerConfigurer;

import com.rekoe.domain.ArticleCategory;

import freemarker.template.Configuration;
import freemarker.template.Template;

@IocBean
public class HtmlService {

	private final static Log log = Logs.get();

	@Inject
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Inject
	private ArticleCategoryService articleCategoryService;

	public void makeIndex() throws Exception {
		Map<String, Object> root = new HashMap<>();
		List<ArticleCategory> tags = articleCategoryService.query(Cnd.NEW().limit(1, 3).asc("createDate"));
		root.put("tags", tags);
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template template = cfg.getTemplate("template/html/site/ts2/home.ftl");
		String path = Mvcs.getServletContext().getRealPath("") + File.separator + "index.html";
		if (log.isDebugEnabled())
			log.debug(path);
		File f = Files.createFileIfNoExists(path);
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
		template.process(root, writer);
		writer.flush();
		writer.close();
	}

	public void makeMobile() throws Exception {
		Map<String, Object> root = new HashMap<>();
		root.put("base", "");
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template template = cfg.getTemplate("template/front/site/ts2/mobile.ftl");
		String path = Mvcs.getServletContext().getRealPath("") + File.separator + "mobile.html";
		if (log.isDebugEnabled())
			log.debug(path);
		File f = Files.createFileIfNoExists(path);
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
		template.process(root, writer);
		writer.flush();
		writer.close();
	}
}
