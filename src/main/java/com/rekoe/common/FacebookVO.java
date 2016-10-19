package com.rekoe.common;

import java.io.Serializable;

public class FacebookVO implements Serializable {

	private static final long serialVersionUID = 6286981086440847759L;

	private long appid;
	private String secret;
	private boolean isProxy;
	private String proxy_host;
	private int proxy_port;

	public long getAppid() {
		return appid;
	}

	public void setAppid(long appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

	public String getProxy_host() {
		return proxy_host;
	}

	public void setProxy_host(String proxy_host) {
		this.proxy_host = proxy_host;
	}

	public int getProxy_port() {
		return proxy_port;
	}

	public void setProxy_port(int proxy_port) {
		this.proxy_port = proxy_port;
	}

}
