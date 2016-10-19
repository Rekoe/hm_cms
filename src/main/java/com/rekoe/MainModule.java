package com.rekoe;

import org.nutz.integration.shiro.ShiroSessionProvider;
import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SessionBy;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.impl.NutActionChainMaker;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.plugins.view.freemarker.FreemarkerViewMaker;

@SetupBy(value = MainSetup.class)
@IocBy(type = ComboIocProvider.class, args = { "*js", "ioc/", "*anno", "com.rekoe", "*quartz", "*async", "*tx", "*org.nutz.plugins.view.freemarker.FreemarkerIocLoader" })
@Modules(scanPackage = true)
@ChainBy(type = NutActionChainMaker.class, args = { "com/rekoe/mvc/mvc-chains.js" })
@Ok("json:full")
@Fail("jsp:jsp.500")
@Localization(value = "msg/", defaultLocalizationKey = "zh-CN")
@Views({ FreemarkerViewMaker.class })
@SessionBy(ShiroSessionProvider.class)
public class MainModule {

}