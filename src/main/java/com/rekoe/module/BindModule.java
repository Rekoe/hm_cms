package com.rekoe.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.nutz.dao.Cnd;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.WebMobileBind;
import com.rekoe.service.JavaBean;
import com.rekoe.service.ServerCacheService;
import com.rekoe.service.WebMobileBindService;

@IocBean
@At("/api/v1")
public class BindModule {

	private final static org.nutz.log.Log log = Logs.get();

	@Inject("java:$conf.get('mobile.sign','2A6F5A6105F21371')")
	private String mobileSign;

	@Inject("java:$conf.get('mobile.gamecode','3')")
	private String mobileGamecode;

	@Inject("java:$conf.get('mobile.validate.url','http://192.168.4.164:8080/game_login/')")
	private String mobileValidateUrl;

	@Inject("java:$conf.get('web.sign','sy2gm20160918')")
	private String webSign;

	@Inject("java:$conf.get('web.validate.url','http://192.168.3.101/GmServer/')")
	private String webValidateUrl;

	@Inject("java:$conf.getBoolean('mobile.validate','false')")
	private boolean mobileValidate;

	@Inject("java:$conf.getBoolean('web.validate','false')")
	private boolean webValidate;

	@Inject
	private WebMobileBindService webMobileBindService;

	@Inject
	ServerCacheService serverCacheService;

	@At("/bind/view")
	@Ok("fm:template.front.site.ts2.bind_uid.view")
	public void view() {

	}

	/**
	 * 账号绑定
	 * 
	 * @param b
	 * @param req
	 * @return
	 */
	@At("/bind/submit")
	@Ok("json")
	public Message submit(@Param("::b.") WebMobileBind b, HttpServletRequest req) {
		String muid = b.getmUid();
		String wuid = b.getWebUid();
		if (StringUtils.isBlank(muid) || StringUtils.isBlank(wuid)) {
			return Message.error("error", req);
		}
		WebMobileBind entity = webMobileBindService.findByWebId(wuid, muid, b.getmSid());
		boolean isRight = false;
		if (Lang.isEmpty(entity)) {
			// 验证手游服游戏账号
			if (mobileValidate) {
				String safeCheckMd5 = Lang.md5(b.getmUid() + b.getmSid() + mobileGamecode + mobileSign).substring(8, 24).toUpperCase();
				Map<String, String> params = new HashMap<>();
				params.put("humanId", b.getmUid());
				params.put("gameCode", mobileGamecode);
				params.put("serverId", b.getmSid() + "");
				params.put("safeMd5", safeCheckMd5);
				Response res = Http.post2(mobileValidateUrl + "bindCheck.py", new HashMap<String, Object>(params), 6000);
				if (!res.isOK()) {
					return Message.error("无法验证账号状态", req);
				}
				/**
				 * {"code":10001} 10000:成功存在 10001:参数错误 10002:签名错误 10003:不存在对应角色
				 */
				String valiteStr = res.getContent();
				NutMap map = Json.fromJson(NutMap.class, valiteStr);
				String status = map.getString("code", "-1");
				switch (status) {
				case "10000": {
					isRight = true;
					break;
				}
				case "10001": {
					return Message.error("参数错误", req);
				}
				case "10002": {
					return Message.error("签名错误 ", req);
				}
				case "10003": {
					return Message.error("不存在对应角色 ", req);
				}
				default:
					return Message.error("未定义错误", req);
				}
			} else {
				isRight = true;
			}
			// 验证页游服务器账号
			if (webValidate && isRight) {
				Map<String, String> params = new HashMap<>();
				params.put("uid", b.getWebUid());
				params.put("sname", b.getWebSid());
				params.put("key", webSign);
				params.put("time", System.currentTimeMillis() + "");
				params.put("sign", getSignData(params));
				params.remove("key");
				Response res = Http.post2(webValidateUrl + "checkuser", new HashMap<String, Object>(params), 6000);
				if (!res.isOK()) {
					return Message.error("无法验证账号状态", req);
				}
				String valiteStr = res.getContent();
				NutMap map = Json.fromJson(NutMap.class, valiteStr);
				String status = map.getString("code", "-1");
				switch (status) {
				case "1000": {
					isRight = true;
					break;
				}
				case "1001": {
					return Message.error("不存在该玩家", req);
				}
				case "1002": {
					return Message.error("参数有误 ", req);
				}
				case "1003": {
					return Message.error("sign验证失败 ", req);
				}
				default:
					return Message.error("系统有误", req);
				}
				/**
				 * 1000，存在该玩家 1001，不存在该玩家 1002，参数有误 1003，sign验证失败 0，系统有误
				 */
			} else {
				isRight = true;
			}
			if (isRight) {
				b.setAddr(Lang.getIP(req));
				b.setTime(Times.now());
				b.setBind(true);
				long uid = NumberUtils.toLong(muid) * 10000 + b.getmSid();
				b.setmUid(uid + "");
				webMobileBindService.insert(b);
			}
		} else {
			return Message.error("账号已绑定", req);
		}
		return Message.success("ok", req);
	}

	/**
	 * 给web游戏发奖
	 * 
	 * @param req
	 * @return
	 */
	@At("/exchange/web")
	@Ok("json")
	@POST
	public NutMap exchangeWeb(HttpServletRequest req) {
		Map<String, String> params = SocialAuthUtil.getRequestParametersMap(req);
		String uid = params.get("uid");
		String sign = params.get("sign");
		if (log.isDebugEnabled())
			log.debug(params);
		if (Lang.isEmpty(params) || StringUtils.isBlank(uid) || StringUtils.isBlank(sign)) {
			return NutMap.NEW().addv("type", 1).addv("code", 1).addv("msg", "数据不合法");
		}
		params.put("key", mobileSign);
		if (!Lang.equals(sign, getSignData(params))) {
			return NutMap.NEW().addv("type", 1).addv("code", 3).addv("msg", "签名错误");
		}
		WebMobileBind entity = webMobileBindService.fetch(Cnd.where("mUid", "=", uid));
		if (Lang.isEmpty(entity)) {
			return NutMap.NEW().addv("type", 1).addv("code", 2).addv("msg", "账号尚未绑定");
		}
		Map<String, Object> postParams = new HashMap<String, Object>();
		params.put("key", webSign);
		params.put("uid", entity.getWebUid());
		params.put("sname", entity.getWebSid());
		params.put("time", System.currentTimeMillis() + "");
		params.put("sign", getSignData(params));
		params.remove("key");
		postParams.putAll(params);
		if (log.isDebugEnabled())
			log.debug(postParams);
		try {
			Response res = Http.post2(webValidateUrl + "additem", postParams, 6000);
			if (!res.isOK()) {
				return NutMap.NEW().addv("type", 1).addv("code", 4).addv("msg", "无法验证账号状态");
			}
			String valiteStr = res.getContent();
			if (log.isDebugEnabled()) {
				log.debug(valiteStr);
			}
			return NutMap.NEW().addv("type", 2).attach(Json.fromJson(NutMap.class, valiteStr));
		} catch (Exception e) {
			return NutMap.NEW().addv("type", 1).addv("code", 5).addv("msg", "无法到达对方");
		}
	}

	/**
	 * 给手游发奖
	 * 
	 * @param req
	 * @return
	 */
	@At("/exchange/mobile")
	@Ok("json")
	@POST
	public NutMap exchangeMobile(HttpServletRequest req) {
		Map<String, String> params = SocialAuthUtil.getRequestParametersMap(req);
		String uid = params.get("uid");
		String sign = params.get("sign");
		if (Lang.isEmpty(params) || StringUtils.isBlank(uid) || StringUtils.isBlank(sign)) {
			return NutMap.NEW().addv("type", 1).addv("code", 1).addv("msg", "数据不合法");
		}
		params.put("key", webSign);
		if (!Lang.equals(sign, getSignData(params))) {
			return NutMap.NEW().addv("type", 1).addv("code", 3).addv("msg", "签名错误");
		}
		WebMobileBind entity = webMobileBindService.fetch(Cnd.where("webUid", "=", uid));
		if (Lang.isEmpty(entity)) {
			return NutMap.NEW().addv("type", 1).addv("code", 2).addv("msg", "账号尚未绑定");
		}
		Map<String, Object> postParams = new HashMap<String, Object>();
		params.put("key", mobileSign);
		params.put("sid", entity.getmSid() + "");
		params.put("uid", entity.getmUid());
		params.put("sign", getSignData(params));
		params.remove("key");
		postParams.putAll(params);
		if (log.isDebugEnabled())
			log.debug(postParams);
		if (log.isDebugEnabled())
			log.debug(mobileValidateUrl + "giveReward.py");
		try {
			Response res = Http.post2(mobileValidateUrl + "giveReward.py", postParams, 6000);
			if (!res.isOK()) {
				return NutMap.NEW().addv("type", 1).addv("code", 4).addv("msg", "无法验证账号状态");
			}
			String valiteStr = res.getContent();
			if (log.isDebugEnabled()) {
				log.debug(valiteStr);
			}
			return NutMap.NEW().addv("type", 2).attach(Json.fromJson(NutMap.class, valiteStr));
		} catch (Exception e) {
			return NutMap.NEW().addv("type", 1).addv("code", 5).addv("msg", "无法到达对方");
		}
	}

	@At("/bind/getlist")
	@Ok("json")
	public List<JavaBean> getlist() {
		return serverCacheService.getMobileChannel();
	}

	@At("/bind/getlist2/*")
	@Ok("json")
	public List<JavaBean> getlist2(long channelid) {
		return serverCacheService.getMobileServiceByChannel(channelid, false);
	}

	private String getSignData(Map<String, String> params) {
		StringBuffer content = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if ("sign".equals(key)) {
				continue;
			}
			String value = params.get(key);
			if (value != null) {
				content.append((i == 0 ? "" : "&") + key + "=" + value);
			} else {
				content.append((i == 0 ? "" : "&") + key + "=");
			}
		}
		return Lang.md5(content.toString());
	}

	public static void main(String[] args) {
		String channelId = "512";
		String gameCode = "3";
		String safeCheckMd5 = Lang.md5(channelId + gameCode + "2A6F5A6105F21371").substring(8, 24).toUpperCase();
		String url = "http://192.168.4.164:8080/game_login/fetchServerInfo.py?channelId=%s&gameCode=%s&safeMd5=%s";
		System.out.println(String.format(url, channelId, gameCode, safeCheckMd5));
	}

	@At("/bind/webslist")
	@Ok("json")
	public List<com.rekoe.service.ItemVO> webslist() {
		return serverCacheService.getWebServers();
	}

	@At
	@Ok("fm:template.front.site.ts2.bind_uid.web")
	public void web() {
	}
}
