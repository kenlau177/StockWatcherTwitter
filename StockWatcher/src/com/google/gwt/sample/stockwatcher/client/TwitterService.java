package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface TwitterService extends RemoteService {
	
	public TwitterInfo login();
	
}
