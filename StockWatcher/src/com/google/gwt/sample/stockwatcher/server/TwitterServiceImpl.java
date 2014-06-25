package com.google.gwt.sample.stockwatcher.server;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;


import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.google.gwt.sample.stockwatcher.client.TwitterInfo;
import com.google.gwt.sample.stockwatcher.client.TwitterService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TwitterServiceImpl extends RemoteServiceServlet implements
		TwitterService {

	private static final long serialVersionUID = 7569104356958067935L;
	private static final String CONSUMER_KEY = "RFmE2GFXsa0z1KSsVEyYBZY6Y";
	private static final String CONSUMER_SECRET = "W0JrjM1ffjcSs4gKXOTMHkYO7Uf1iqhIG39SWThCjkYvv8PK1A";
	
	@Override
	public TwitterInfo login() {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET); 
		
		HttpSession session = super.getThreadLocalRequest().getSession();
		
		User user = getUserLogin( twitter, session );
		
		if (user != null) {
			TwitterInfo result = new TwitterInfo();
			result.setUser( user.getName() );
			return result;
		} else {
			return requestToken( twitter, session );
		}
	} 
		
 	protected User getUserLogin( Twitter twitter, HttpSession session ) {
	 // try to get credentials
	 User user = null;
	 if ((session.getAttribute("token") != null) && (session.getAttribute("tokenSecret") != null)) {
	
	 try {
		 AccessToken accessToken = twitter.getOAuthAccessToken(
				 (String) session.getAttribute("token"),
		 (String) session.getAttribute("tokenSecret"));
		 twitter.setOAuthAccessToken(accessToken);
	
		 user = twitter.verifyCredentials();
		} catch (TwitterException e) {
			if (e.getStatusCode() == 401) {
				Logger.getLogger( TwitterInfo.class.getName() ).info( e.getMessage() );   
			} else {
				Logger.getLogger( TwitterInfo.class.getName() ).severe( e.getMessage() );
			}
		 }
	 }
	 return user;
	}

	private TwitterInfo requestToken( Twitter twitter, HttpSession session ) {   
		RequestToken requestToken;
		try {
			session.setAttribute("token", null);
			session.setAttribute("tokenSecret", null);
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			Logger.getLogger( TwitterInfo.class.getName() ).severe( e.getMessage() );
			return null;
		}
 
		String token = requestToken.getToken();
		String tokenSecret = requestToken.getTokenSecret();
	
		session.setAttribute("token", token);
		session.setAttribute("tokenSecret", tokenSecret);
	
		TwitterInfo result = new TwitterInfo();
		result.setLoginURL( requestToken.getAuthorizationURL() );
		return result;
	}
 
 
}
