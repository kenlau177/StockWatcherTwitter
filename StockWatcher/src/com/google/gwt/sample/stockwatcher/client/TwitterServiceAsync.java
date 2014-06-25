package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TwitterServiceAsync {
	void login(AsyncCallback<TwitterInfo> callback);
}
