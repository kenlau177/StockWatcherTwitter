package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

public class TwitterInfo implements Serializable {
	
	private static final long serialVersionUID = 4457180663742547883L;

	private String user;
	
	private String loginURL;

	public TwitterInfo() {}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

	public String getLoginURL() {
		return loginURL;
	};
	
}
	
	
