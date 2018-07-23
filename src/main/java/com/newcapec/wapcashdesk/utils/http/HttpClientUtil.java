package com.newcapec.wapcashdesk.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newcapec.wapcashdesk.constant.SysConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: HTTP工具类
 * @ClassName:HttpClientUtil.java
 * @Description:
 * 字符编码<UTF-8>
 * public static final String CHARSET_UTF8 = "UTF-8";
 * @Copyright 2016-2017 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:2016年12月12日 下午3:55:15
 * @version V1.0
 */
public class HttpClientUtil {

	private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	private static HttpClientContext context = HttpClientContext.create();

	/**
	 *
	 * @Title: get请求(带参)
	 * @param url
	 * @param param
	 * @return String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年12月12日 下午3:55:42
	 */
	public static String doGet(String url, Map<String, String> param) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			try {
				response = httpclient.execute(httpGet);
				// 使用HttpClient认证机制
				// response = httpClient.execute(httpGet, context);
			} catch (Exception e) {
				log.warn("【GET请求失败】,请求地址：{}", url);
				e.printStackTrace();
			}
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), SysConstant.CHARSET_UTF8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 *
	 * @Title: get请求(无参)
	 * @param url
	 * @return String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年12月12日 下午3:56:26
	 */
	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 *
	 * @Title: post请求(带参)
	 * @param url
	 * @param param
	 * @return String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年12月12日 下午3:56:01
	 */
	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, SysConstant.CHARSET_UTF8);
				httpPost.setEntity(entity);
			}

			log.info("【POST请求信息】,请求地址:{},请求参数的MAP:{}", url, param);

			// 执行http请求
			try {
				response = httpClient.execute(httpPost);
				// 使用HttpClient认证机制
				// response = httpClient.execute(httpPost, context);
			} catch (Exception e) {
				log.warn("【POST请求失败】,请求地址:{},请求参数的MAP:{}", url, param);
				e.printStackTrace();
			}
			resultString = EntityUtils.toString(response.getEntity(), SysConstant.CHARSET_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}


	/**
	 * @Title: post请求(带请求头)
	 * @methodName:  doPostWithHeader
	 * @param url
	 * @param headers 请求头
	 * @param param 请求参数【JOSN格式】
	 * @return java.lang.String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:  2017-06-10 14:16
	 */
	public static String doPostWithHeader(String url, Map<String, String> headers, String param) {

		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);

			// 创建请求消息头
			if (headers != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : headers.keySet()) {
					httpPost.setHeader("content-type", "application/json");
					httpPost.setHeader(key, headers.get(key));
				}
			}

			// 添加请求参数【JOSN格式】
			if (StringUtils.isNotEmpty(param)) {
				StringEntity entity = new StringEntity(param, SysConstant.CHARSET_UTF8);
				httpPost.setEntity(entity);
			}


			log.info("【POST请求信息】,请求地址:{},请求头:{},请求参数<JOSN格式>:{}", url,headers, param);

			// 执行http请求
			try {
				response = httpClient.execute(httpPost);
				// 使用HttpClient认证机制
				// response = httpClient.execute(httpPost, context);
			} catch (Exception e) {
				log.warn("【POST请求异常】,请求地址:{},请求头:{},请求参数的<JOSN格式>:{},异常信息是：{}", url, headers,param,e);
				e.printStackTrace();
			}
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200){
				resultString = EntityUtils.toString(response.getEntity(), SysConstant.CHARSET_UTF8);
			}

			log.info("【POST请求信息】,请求地址:{},请求头:{},请求参数<JOSN格式>:{},返回结果：{}", url,headers, param,resultString);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}
	/**
	 *
	 * @Title: post请求(无参)
	 * @param url
	 * @return String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年12月12日 下午3:56:17
	 */
	public static String doPost(String url) {
		return doPost(url, null);
	}

	/**
	 *
	 * @Title:使用POST方法发送JSON数据
	 * @param url
	 * @param json
	 * @return String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年12月12日 下午3:56:10
	 */
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容(指定请求形式是Json形式的字符串)
			// 在SpringMVC中接收请求的Json,需要使用@RequestBody;
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			try {
				response = httpClient.execute(httpPost);
				// 使用HttpClient认证机制
				// response = httpClient.execute(httpPost, context);
			} catch (Exception e) {
				log.warn("【POST请求(参数为Json)失败】,请求地址：{},参数：{}", url, json);
				e.printStackTrace();
			}
			resultString = EntityUtils.toString(response.getEntity(), SysConstant.CHARSET_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}

	/**
	 *
	 * @Title: 使用POST方法发送XML数据
	 * @param url
	 * @param xmlParam
	 * @param xmlData
	 * @return
	 * @throws Exception
	 *             String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年11月20日 下午9:54:54
	 */
	public String sendXMLDataByPost(String url, String xmlParam, String xmlData) throws Exception {

		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";

		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> parameters = new ArrayList<>();
		// 设置参数名的---注意传入的参数名,在获取的时候和他保持一致
		parameters.add(new BasicNameValuePair(xmlParam, xmlData));
		post.setEntity(new UrlEncodedFormEntity(parameters, SysConstant.CHARSET_UTF8));

		try {
			response = httpClient.execute(post);
		} catch (Exception e) {
			log.warn("【POST请求(参数为XML数据)失败】,请求地址：{},参数：{}", url, xmlData);
			e.printStackTrace();
		}

		System.out.println(response.toString());

		resultString = EntityUtils.toString(response.getEntity(), SysConstant.CHARSET_UTF8);
		return resultString;
	}


	/**
	 *
	 * @Title: 使用HttpClient认证机制
	 * @param username
	 * @param password void
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年12月12日 下午5:02:09
	 */
	public void addUserOAuth(String username, String password) {

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		org.apache.http.auth.Credentials credentials = new org.apache.http.auth.UsernamePasswordCredentials(username,
				password);
		credsProvider.setCredentials(org.apache.http.auth.AuthScope.ANY, credentials);
		context.setCredentialsProvider(credsProvider);
	}


	/**
	 * @Title:  读取Request中的数据流
	 * @methodName:  readData
	 * @param request
	 * @return java.lang.String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:  2017-12-14 16:44
	 */
	public static String readRequestData(HttpServletRequest request) {

		BufferedReader br = null;
		try {
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line; (line=br.readLine())!=null;) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}

            String res = result.toString();
            log.info("【读取Request中的数据流】,内容是：{}", res);
			return res;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
                    e.printStackTrace();
                    log.warn("【读取Request中的数据流异常】,异常信息：{}", e);
				}
			}
		}
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
            log.warn("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {

		JSONObject param = new JSONObject();

		param.put("schoolId", 216);// 学校ID，注意在宿主端目前学校id是216(和你们缴费轻应用学校id不一致)，
		// 后续可能需要修改解析token接口，要将该字段回显，目前先固定为216
		param.put("title", "缴费");
		param.put("content", "缴费内容");
		param.put("scale", "app");// 发送到APP端
		param.put("userlist", "21624");// 从宿主端获取的用户id，不是学号
		param.put("userType", "userid");// 类型为用户ID
		param.put("messageType", "text");
		String jsonString = param.toJSONString();
		HashMap<String, String> headers = new HashMap<>();
		headers.put("token", "1493587504_hN2cdX2snJevkJO2ZCIFzc7JLC2blXZWjJavkbabIcoOLC2akp6WkCIFIiITImZNkJ0ixcATImaRZm92ZCIFzCwilXy8kJ5mlX2SIcowLC2TkJdWkauWkpUixc70xsn3y8A3zMQHxsQTIm5WnJSxnpKaIcoiIiwijGevkmUixiIiLC2wlpQixiIHx8gHx8gHx8g5xsb5xsbiLC2NZp6Tsm6SZtIFIRpRSRmqvBlKiBivahi0WRlISNITIY2vkGUixcIMLC2MnJevkJOxnpKaIcoi5km_5khh5FNT5LiA5kmD5nt_5ZRSIiwijJevjYuxnpKaIcoi5FNT5LiA5kmD5nt_5ZRSIiwid4ZqjJqNtpQixcATIYqRlXQixi2RdpOTIiwidXyajbabIcoNzsnNyCwidXyajb5ekpUixiINz873z8UNyN29");


		String res = HttpClientUtil.doPostWithHeader("http://www.app8848.com/interface/rest/http/message/sendMessage.htm",
				headers, jsonString);

		System.out.println("返回结果："+res);

		JSONObject resultJsonObject = JSON.parseObject(res);
		String msgState = resultJsonObject.getString("msgState");

		System.out.println("推送状态："+msgState);

		if(StringUtils.isNotEmpty(msgState)&&"1".equals(msgState)){
			System.out.println("推送成功");
		}

		String result = resultJsonObject.getString("result");
		System.out.println("result"+result);



	}

}
