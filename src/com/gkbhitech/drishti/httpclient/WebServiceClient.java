/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gkbhitech.drishti.httpclient;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.gkbhitech.drishti.common.Constant;
import com.google.gson.JsonObject;
/**
 *
 * @author root
 */
public class WebServiceClient {
	private static final String tag="WebServiceClient";
    public static String CONTENT_TYPE_FORM_ENCODED="application/x-www-form-urlencoded";
    public static String CONTENT_TYPE_JSON="application/json";
    private String m_BaseUri;
    public WebServiceClient(String baseUri){
        m_BaseUri=baseUri;
    }
    
    public String doGet(String service, HashMap<String, Object> params)throws NetworkUnavailableException,Exception{
		try {
			return getRequest(service, encodeUrl(params), CONTENT_TYPE_JSON);
		/*} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();*/
		} catch (NetworkUnavailableException ioe) {
			throw new NetworkUnavailableException(ioe.getMessage());
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
    
    public String doPost(String service,HashMap<String,Object>params)throws Exception{
    	try{
            String serviceUri=m_BaseUri+"/"+service;
            return postRequest(serviceUri,encodeUrl(params),CONTENT_TYPE_JSON);
        }catch (NetworkUnavailableException ioe) {
			throw new NetworkUnavailableException(ioe.getMessage());
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }
     public String doPost(String service,JsonObject params)throws Exception {
		try{
			 String serviceUri=m_BaseUri+"/"+service;
			 return postRequest(serviceUri,params.toString(),CONTENT_TYPE_JSON);
		}catch (NetworkUnavailableException ioe) {
			throw new NetworkUnavailableException(ioe.getMessage());
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }
     public String doPost(String service,String params)throws Exception {
    	 try{
    		 String serviceUri=m_BaseUri+"/"+service;
    		 return postRequest(serviceUri,params.toString(),CONTENT_TYPE_JSON);
    	 }catch (NetworkUnavailableException ioe) {
 			throw new NetworkUnavailableException(ioe.getMessage());
 		}catch (Exception e) {
 			throw new Exception(e.getMessage());
 		}
    }
     public String doPost(String service)throws Exception{
        try{
        	String serviceUri = m_BaseUri+"/"+service;
        	return postRequest(serviceUri,null,CONTENT_TYPE_JSON);
        }catch (NetworkUnavailableException ioe) {
			throw new NetworkUnavailableException(ioe.getMessage());
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }
    public String getRequest(String url,String params, String contentType)
    		throws ClientProtocolException, IOException, NetworkUnavailableException, Exception{
    	
    	String serviceUri;
    	if(params == ""){
    		serviceUri=m_BaseUri+"/"+url;
    	}else{
    		serviceUri=m_BaseUri+"/"+url+"?"+params;
    	}
    	
        if(Constant.log) Log.i(tag, "Request : "+serviceUri);
        
        final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Constant.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, Constant.SOCKET_TIMEOUT);
        
        HttpClient client = new DefaultHttpClient(httpParams);
        
        HttpGet get = new HttpGet(serviceUri);
        get.setHeader(HTTP.CONTENT_TYPE, contentType);
        
        //client.getParams().setParameter("http.socket.timeout", new Integer(Constant.SOCKET_TIMEOUT));
		//client.getParams().setParameter("http.connection.timeout", new Integer(Constant.CONNECTION_TIMEOUT));
        
        try{
	        HttpResponse response = client.execute(get);
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	String r = EntityUtils.toString(response.getEntity());
	        	if(Constant.log) Log.i(tag, "Response : "+r);
	        	return r;
	        }else{
	        	throw new Exception(response.getStatusLine().getReasonPhrase());
	        }
        }catch (SocketException e) {
        	throw new NetworkUnavailableException(e.getMessage());
        }catch (SocketTimeoutException e) {
        	throw new NetworkUnavailableException(e.getMessage());
        }catch (ConnectTimeoutException e) {
        	throw new NetworkUnavailableException(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
    }
    
    /*public String getRequestTest()
    		throws ClientProtocolException, IOException, NetworkUnavailableException, Exception{
    	
    	String serviceUri = "https://api.parse.com/1/classes/City";
    	
        System.out.println("Service Uri:GET " +serviceUri);
        
        final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Constant.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, Constant.SOCKET_TIMEOUT);
        
        HttpClient client = new DefaultHttpClient(httpParams);
        
        HttpGet get = new HttpGet(serviceUri);
        get.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON);
        get.setHeader("x-parse-rest-api-key",	"6gsVTEzL5sE66JRmMyqgL4xBNKbtE3SO8zuvFVHL");
        get.setHeader("x-parse-application-id",	"g47sC54OqO8flnHAU4ly2urHZ7DiKCdbpBlnvoUu");
        
        //client.getParams().setParameter("http.socket.timeout", new Integer(Constant.SOCKET_TIMEOUT));
		//client.getParams().setParameter("http.connection.timeout", new Integer(Constant.CONNECTION_TIMEOUT));
        
        try{
	        HttpResponse response = client.execute(get);
	        System.out.println("Response : "+response.toString());
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	String r = EntityUtils.toString(response.getEntity());
	        	Log.i(tag, "Response :"+r);
	        	return r;
	        }else{
	        	System.out.println("Response : "+response.toString());
	        	//throw new Exception(response.getStatusLine().getReasonPhrase());
	        }
        }catch (ConnectTimeoutException e) {
        	e.printStackTrace();
			// TODO: handle exception
        	//Log.i(tag, "...socket time out.....");
        	//throw new NetworkUnavailableException(e.getMessage());
		}catch (HttpHostConnectException e) {
			e.printStackTrace();
			// TODO: handle exception
        	//Log.i(tag, "...socket time out.....");
        	//throw new NetworkUnavailableException(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			//throw new Exception(e.getMessage());
		}
        return null;
        //throw new IOException(response.getStatusLine().getReasonPhrase());
    }*/
    
    public String postRequest(String url,String params, String contentType)
    		throws ClientProtocolException, IOException, NetworkUnavailableException, Exception{

    	final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Constant.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, Constant.SOCKET_TIMEOUT);
    	
        HttpClient client = new DefaultHttpClient(httpParams);
        HttpPost post = new HttpPost(url);
        post.setHeader(HTTP.CONTENT_TYPE, contentType);

        if(Constant.log) Log.i(tag, "Request URL : "+url);
        if(Constant.log) Log.i(tag, "Request params : "+params);
        
        if(params!=null){
            post.setEntity(new StringEntity(params));
        }
        
        try{
	        HttpResponse hhtpResponse = client.execute(post);
	        if (hhtpResponse.getStatusLine().getStatusCode() == 200) {
	        	String response = EntityUtils.toString(hhtpResponse.getEntity());
	        	if(Constant.log) Log.i(tag, "Response : "+response);
	            return response;
	        }else{
	        	if(Constant.log) Log.i(tag, "Response : "+hhtpResponse.getStatusLine().getStatusCode()+" = "+hhtpResponse.getStatusLine().getReasonPhrase());
	        }
	        //Log.i(tag,String.valueOf(hhtpResponse.getStatusLine().getStatusCode()));
	        //Log.i(tag, hhtpResponse.getStatusLine().getReasonPhrase());
        }catch (SocketException e) {
        	throw new NetworkUnavailableException(e.getMessage());
        }catch (SocketTimeoutException e) {
        	throw new NetworkUnavailableException(e.getMessage());
        }catch (ConnectTimeoutException e) {
        	throw new NetworkUnavailableException(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
        
        return null;
        //throw new IOException(response.getStatusLine().getReasonPhrase());
     }
     public String encodeUrl(HashMap<String,Object> parameters) {
        if (parameters == null) {
        	if(Constant.log) Log.i(tag, "Encoded string : ");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            if (first) first = false; else sb.append("&");
            sb.append(URLEncoder.encode(key) + "=" +
                      URLEncoder.encode(parameters.get(key).toString()));
        }
        if(Constant.log) Log.i(tag, "Encoded string : "+sb.toString());
        return sb.toString();
    }

    private HashMap<String,Object> decodeUrl(String s) {
        HashMap<String,Object> params = new HashMap();
        if (s != null) {
            String array[] = s.split("&");
            for (String parameter : array) {
                String v[] = parameter.split("=");
                params.put(URLDecoder.decode(v[0]),
                                 URLDecoder.decode(v[1]));
            }
        }
        return params;
    }

    /**
     * Parse a URL query and fragment parameters into a key-value bundle.
     *
     * @param url the URL to parse
     * @return a dictionary bundle of keys and values
     */
    private  HashMap<String,Object> parseUrl(String url) {
        // hack to prevent MalformedURLException
        url = url.replace("fbconnect", "http");
        try {
            URL u = new URL(url);
            HashMap<String,Object> b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new HashMap<String,Object>();
        }
    }

    
    public String login(String username, String password)
			throws NetworkUnavailableException, Exception{
    	
    	final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Constant.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, Constant.SOCKET_TIMEOUT);
    	
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		try {
			HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {

				@Override
				public void process(HttpRequest request, HttpContext context)
						throws HttpException, IOException {
					AuthState authState = (AuthState) context
							.getAttribute(ClientContext.TARGET_AUTH_STATE);
					CredentialsProvider credsProvider = (CredentialsProvider) context
							.getAttribute(ClientContext.CREDS_PROVIDER);
					HttpHost targetHost = (HttpHost) context
							.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

					if (authState.getAuthScheme() == null) {
						AuthScope authScope = new AuthScope(
								targetHost.getHostName(), targetHost.getPort());
						org.apache.http.auth.Credentials creds = credsProvider
								.getCredentials(authScope);
						if (creds != null) {
							authState.setAuthScheme(new BasicScheme());
							authState
									.setCredentials((org.apache.http.auth.Credentials) creds);
						}
					}
				}
			};

			HttpGet httpGet = new HttpGet(Constant.BASE_URI+"/"+Constant.URL_LOGIN);
			
			Log.i(tag, "userName : "+username+ "#" + password);
			httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(Constant.BASE_IP, Constant.PORT),
					new UsernamePasswordCredentials(username, password));
			
			httpClient.addRequestInterceptor(preemptiveAuth, 0);
			httpGet.setHeader(new BasicHeader(HTTP.CONTENT_TYPE,CONTENT_TYPE_JSON));
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"Android");
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			if (response.getStatusLine().toString().contains("200")) {

				if (entity != null) {
					String responseEntity = EntityUtils.toString(entity);
//					System.out.println("Response content length: " + entity.getContentLength());
					//System.out.println("Response : " + responseEntity);
					return responseEntity;
				}
			}else if(response.getStatusLine().toString().contains("401")){
				String responseEntity = EntityUtils.toString(entity);
				System.out.println("Response : " + responseEntity);
				throw new UnauthorizedException(response.getStatusLine().getReasonPhrase());
			}
			else{
				throw new Exception(response.getStatusLine().getReasonPhrase());
			}
			/*else if (response.getStatusLine().toString().contains("401")) {
				if (entity != null) {
					String responseEntity = EntityUtils.toString(entity);
//					System.out.println("Response content length: " + entity.getContentLength());
					System.out.println("Response : " + responseEntity);
					return responseEntity;
					
				}
			}
			else if (response.getStatusLine().toString().contains("403")) {
				if (entity != null) {
					String responseEntity = EntityUtils.toString(entity);
//					System.out.println("Response content length: " + entity.getContentLength());
					System.out.println("Response : " + responseEntity);
					return responseEntity;
					
				}
			}
			else if (response.getStatusLine().toString().contains("500")) {
				if (entity != null) {
					String responseEntity = EntityUtils.toString(entity);
//					System.out.println("Response content length: " + entity.getContentLength());
					System.out.println("Response : " + responseEntity);
					return responseEntity;
					
				}
			}*/
			return null;
		} catch (SocketException e) {
			// TODO: handle exception
        	//Log.i(tag, "...socket time out.....");
        	throw new NetworkUnavailableException(e.getMessage());
		}catch (ConnectTimeoutException e) {
			// TODO: handle exception
        	//Log.i(tag, "...socket time out.....");
        	throw new NetworkUnavailableException(e.getMessage());
		}catch (UnauthorizedException e) {
			// TODO: handle exception
        	//Log.i(tag, "...socket time out.....");
        	throw new UnauthorizedException(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpClient.getConnectionManager().shutdown();
		}
	}

}