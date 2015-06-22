package com.github.jreddit.oauth.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.RedditPostRequest;

public class RedditHttpClient extends RedditClient {

	/** User agent. */
	private String userAgent;
	
	/** HTTP Client. */
	private HttpClient httpClient;
	
	/**
	 * Constructor.
	 * 
	 * @param userAgent User agent of your application
	 * @param httpClient HTTP client to use for the requests
	 */
	public RedditHttpClient(String userAgent, HttpClient httpClient) {
		this.userAgent = userAgent;
		this.httpClient = httpClient;
	}
	
	@Override
	public String post(RedditToken rToken, RedditPostRequest redditRequest) {
		
	    try {
	    	
	    	// Create post request
	        HttpPost request = new HttpPost(OAUTH_API_DOMAIN + redditRequest.generateRedditURI());

	        // Add parameters to body
	        request.setEntity(new StringEntity(redditRequest.generateBody()));
	        
	        // Add authorization
	        addAuthorization(request, rToken);
	        
	        // Add user agent
	        addUserAgent(request);
	        
	        // Attempt to do execute request
	        HttpResponse response = httpClient.execute(request);
	        
	        // Return response if successful
	        if (response != null) {
		        return EntityUtils.toString(response.getEntity());
	        }
	        
	    } catch (UnsupportedEncodingException uee) {
	    	//LOGGER.log(Level.SEVERE, "Unsupported Encoding Exception thrown", uee);
	    } catch (ClientProtocolException cpe) {
	    	//LOGGER.log(Level.SEVERE, "Client Protocol Exception thrown", cpe);
	    } catch (IOException ioe) {
	    	//LOGGER.log(Level.WARNING, "I/O Exception thrown", ioe);
	    }
	    
	    return null;
	    
	}
	
	@Override
	public String get(RedditToken rToken, RedditGetRequest redditRequest) {
		
	    try {
	    	
	    	// Create post request
	        HttpGet request = new HttpGet(OAUTH_API_DOMAIN + redditRequest.generateRedditURI());
	       
	        // Add authorization
	        addAuthorization(request, rToken);
	        
	        // Add user agent
	        addUserAgent(request);
	        
	        // Attempt to do execute request
	        HttpResponse response = httpClient.execute(request);//, context
	        
	        // Return response if successful
	        if (response != null) {
		        return EntityUtils.toString(response.getEntity());
	        }
	        
	    } catch (UnsupportedEncodingException uee) {
	    	//LOGGER.log(Level.SEVERE, "Unsupported Encoding Exception thrown", uee);
	    } catch (ClientProtocolException cpe) {
	    	//LOGGER.log(Level.SEVERE, "Client Protocol Exception thrown", cpe);
	    } catch (IOException ioe) {
	    	//LOGGER.log(Level.WARNING, "I/O Exception thrown", ioe);
	    }
	    
	    return null;
	    
	}
	
	/**
	 * Add authorization to the HTTP request.
	 * 
	 * @param request HTTP request
	 * @param rToken Reddit token (generally of the "bearer" type)
	 */
	private void addAuthorization(HttpRequest request, RedditToken rToken) {
		request.addHeader("Authorization", rToken.getTokenType() + " " + rToken.getAccessToken());
	}
	
	/**
	 * Add user agent to the HTTP request.
	 * 
	 * @param request HTTP request
	 */
	private void addUserAgent(HttpRequest request) {
		request.addHeader("User-Agent", userAgent);
	}

}