package com.rekoe;

import java.nio.charset.Charset;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.List;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.integration.shiro.NutShiro;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Encoding;
import org.nutz.lang.Mirror;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.plugins.view.freemarker.FreeMarkerConfigurer;
import org.quartz.Scheduler;

import com.rekoe.domain.HMReservation;
import com.rekoe.domain.User;
import com.rekoe.service.AuthorityService;

import freemarker.template.Configuration;

public class MainSetup implements Setup {

	private static final Log log = Logs.get();

	public static PropertiesProxy conf;

	public void init(NutConfig nc) {
		NutShiro.DefaultLoginURL = "/admin/logout";
		if (!Charset.defaultCharset().name().equalsIgnoreCase(Encoding.UTF8)) {
			log.warn("This project must run in UTF-8, pls add -Dfile.encoding=UTF-8 to JAVA_OPTS");
		}
		Ioc ioc = nc.getIoc();
		ioc.get(Configuration.class).setAutoImports(new NutMap().setv("p", "/ftl/pony/index.ftl").setv("s", "/ftl/spring.ftl"));
		ioc.get(FreeMarkerConfigurer.class, "mapTags");
		Dao dao = ioc.get(Dao.class);

		// 为全部标注了@Table的bean建表
		Daos.createTablesInPackage(dao, User.class.getPackage().getName(), false);
		Daos.migration(dao, HMReservation.class, true, true, false);
		// 初始化默认根用户
		if (0 == dao.count(User.class)) {
			FileSqlManager fm = new FileSqlManager("init_system_h2.sql");
			List<Sql> sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
			// 初始化用户密码（全部都是123）及salt
			List<User> userList = dao.query(User.class, null);
			for (User user : userList) {
				RandomNumberGenerator rng = new SecureRandomNumberGenerator();
				String salt = rng.nextBytes().toBase64();
				String hashedPasswordBase64 = new Sha256Hash("123", salt, 1024).toBase64();
				user.setSalt(salt);
				user.setPassword(hashedPasswordBase64);
				dao.update(user);
			}
		}
		// 获取NutQuartzCronJobFactory从而触发计划任务的初始化与启动
		ioc.get(NutQuartzCronJobFactory.class);

		// 权限系统初始化
		ioc.get(AuthorityService.class).initFormPackage(getClass().getPackage().getName());
		Mvcs.disableFastClassInvoker = false;
	}

	public void destroy(NutConfig conf) {
		try {
			Mirror.me(Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread")).invoke(null, "shutdown");
		} catch (Throwable e) {
		}
		try {
			conf.getIoc().get(Scheduler.class).shutdown(true);
		} catch (Exception e) {
		}
		Enumeration<Driver> en = DriverManager.getDrivers();
		while (en.hasMoreElements()) {
			try {
				Driver driver = en.nextElement();
				String className = driver.getClass().getName();
				if ("com.alibaba.druid.proxy.DruidDriver".equals(className) || "com.mysql.jdbc.Driver".equals(className)) {
					log.debug("deregisterDriver: " + className);
					DriverManager.deregisterDriver(driver);
				}
			} catch (Exception e) {
			}
		}
	}
}