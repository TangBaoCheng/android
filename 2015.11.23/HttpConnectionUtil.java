package com.scxh.android1503.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.scxh.android1503.util.HttpConnectionUtil.HttpCallBack;
import com.scxh.android1503.util.HttpConnectionUtil.Method;

import android.os.AsyncTask;

public class HttpConnectionUtil {
	public enum Method {
		GET,POST
	}

	/** 第一步定义接口 */
	public interface HttpCallBack {
		public void returnMessage(String message);
	}

	/** 注册接口 */
	public HttpCallBack mHttpCallBack;

	/**
	 * 无参处理
	 * 
	 * @param url
	 * @param method
	 * @param httpCallBack
	 */
	public void asyncTaskHttp(String url, final Method method,
			HttpCallBack httpCallBack) {
		asyncTaskHttps(url, method, null, httpCallBack);
	}

	/**
	 * 有参处理
	 * 
	 * 根据传URI 参数，请求方式， 从服务端获取数据
	 * 
	 * @param url
	 * @param method
	 * @param parameter
	 * @return
	 */
	public void asyncTaskHttps(final String url, final Method method,
			final HashMap<String, Object> parameter, HttpCallBack httpCallBack) {
		mHttpCallBack = httpCallBack;
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				return getDataByHttpClient(url, method, parameter);
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				mHttpCallBack.returnMessage(result);
			}
		}.execute(url);

	}

	/**
	 * 网络获取数据
	 * 
	 * @param url
	 * @param method
	 * @param parameter
	 * @return
	 */
	public String getDataByHttpClient(String url, final Method method,
			final HashMap<String, Object> parameter) {
		String str = null;
		try {
			HttpClient httpClient = new DefaultHttpClient(); //实现化HttpClient
			HttpUriRequest request = getHttpRequest(url, method, parameter); //实始化Http请求
			HttpResponse response = httpClient.execute(request);  //执行Http请求得到响应实例

			if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
				 str = EntityUtils.toString(response.getEntity());  
			}else{
				str = "网络出错";
			}
	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str;

	}

	/**
	 * Http请求处理
	 * @param url
	 * @param method
	 * @param parameter
	 * @return
	 */
	public HttpUriRequest getHttpRequest(String url, final Method method,
			final HashMap<String, Object> parameter) {
		if (method.equals(Method.GET)) {
			if (parameter != null) {
				/**
				 * 组装Get请求参数 http://192.168.1.156/app/login?user=张三&psw=123456"
				 * */
				url = url + "?";
				StringBuilder sb = new StringBuilder(url);
				for (String key : parameter.keySet()) {
					Object values = parameter.get(key);
					sb.append(key);
					sb.append("=");
					sb.append(values);
					sb.append("&");
				}
				url = sb.substring(0, sb.length() - 1);
			}

			return new HttpGet(url);
		} else {
			HttpPost request = new HttpPost(url);
			if (parameter != null) {
				/** post传参 */
				ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
				for (String key : parameter.keySet()) {
					Object values = parameter.get(key);
					BasicNameValuePair userNameValue = new BasicNameValuePair(
							key, (String) values);
					pairs.add(userNameValue);
				}
				try {
					HttpEntity entity = new UrlEncodedFormEntity(pairs,
							HTTP.UTF_8);
					request.setEntity(entity);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}

			return request;
		}
	}

}
