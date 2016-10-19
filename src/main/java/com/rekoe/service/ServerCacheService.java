package com.rekoe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class ServerCacheService {

	private final static Log log = Logs.get();

	@Inject("java:$conf.get('web.validate.url','http://192.168.3.101/GmServer/')")
	private String webValidateUrl;

	@Inject("java:$conf.get('mobile.sign','2A6F5A6105F21371')")
	private String mobileSign;

	@Inject("java:$conf.get('mobile.gamecode','3')")
	private String mobileGamecode;

	@Inject("java:$conf.get('mobile.validate.url','http://192.168.4.164:8080/game_login/')")
	private String mobileValidateUrl;

	private List<JavaBean> mobileList = new ArrayList<JavaBean>();

	private List<com.rekoe.service.ItemVO> webList = new ArrayList<>();

	public List<com.rekoe.service.ItemVO> getWebServers() {
		if (Lang.isEmpty(webList)) {
			loadWeb();
		}
		return webList;
	}

	public List<JavaBean> getMobileChannel() {
		if (Lang.isEmpty(mobileList)) {
			loadMobileChannel();
		}
		return mobileList;
	}

	public void loadWeb() {
		String url = webValidateUrl + "servers";
		Response res = Http.get(url);
		if (res.isOK()) {
			webList.clear();
			String json = res.getContent();
			List<String> sids = Json.fromJsonAsList(String.class, json);
			for (String sid : sids) {
				com.rekoe.service.ItemVO javabean = new com.rekoe.service.ItemVO();
				javabean.setId(sid);
				javabean.setName(sid);
				webList.add(javabean);
			}
		}
	}

	public void loadMobileChannel() {
		String safeCheckMd5 = Lang.md5(mobileGamecode + mobileSign).substring(8, 24).toUpperCase();
		String url = String.format(mobileValidateUrl + "fetchChannelInfo.py?gameCode=%s&safeMd5=%s", mobileGamecode, safeCheckMd5);
		Response res = Http.get(url);
		if (res.isOK()) {
			mobileList.clear();
			List<NutMap> maps = Json.fromJsonAsList(NutMap.class, res.getContent());
			for (NutMap map : maps) {
				Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					String key = entry.getKey();
					String value = entry.getValue().toString();
					JavaBean javabean = new JavaBean();
					javabean.setId(NumberUtils.toLong(key));
					javabean.setName(value);
					mobileList.add(javabean);
				}
			}
		}
	}

	private Map<Long, List<JavaBean>> mobileServerList = new HashMap<>();

	public List<JavaBean> getMobileServiceByChannel(long channelid, boolean fetch) {
		List<JavaBean> hist = mobileServerList.get(channelid);
		if (hist == null || fetch) {
			List<JavaBean> list = new ArrayList<JavaBean>();
			String safeCheckMd5 = Lang.md5(channelid + mobileGamecode + mobileSign).substring(8, 24).toUpperCase();
			String url = String.format(mobileValidateUrl + "fetchServerInfo.py?channelId=%s&gameCode=%s&safeMd5=%s", channelid, mobileGamecode, safeCheckMd5);
			try {
				Response res = Http.get(url, 6000);
				if (res.isOK()) {
					String json = res.getContent();
					List<NutMap> maps = Json.fromJsonAsList(NutMap.class, json);
					for (NutMap map : maps) {
						JavaBean javabean = new JavaBean();
						javabean.setId(map.getLong("serverId"));
						javabean.setName(map.getString("name"));
						list.add(javabean);
					}
				}
			} catch (Exception e) {
				log.error(e);
			}
			return list;
		}
		return hist;
	}

	public void loadMobileServers() {
		for (JavaBean jb : mobileList) {
			List<JavaBean> list = getMobileServiceByChannel(jb.getId(), true);
			if (Lang.isEmpty(list)) {
				continue;
			}
			mobileServerList.put(jb.getId(), list);
		}
	}
}
