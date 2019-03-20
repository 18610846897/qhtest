package com.qh.asynhttp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import com.alibaba.fastjson.JSONObject;

public class AsynHttpClient
{
	public static void main(String[] args)
	{
		// 构造请求
		String url = "http://127.0.0.1:8082/httpPost";
		String url_ = "http://127.0.0.1:8082/httpdo";
		JSONObject httpPrm = new JSONObject();
		httpPrm.put("name", "张全蛋");// 姓名
		httpPrm.put("ID", "1234567891017522x");// 身份证号码
		httpPrm.put("tel", "18735918661");// 手机号
		
		JSONObject httpPrm_ = new JSONObject();
		httpPrm_.put("name", "张二蛋");// 姓名
		httpPrm_.put("ID", "612727198900203518");// 身份证号码
		httpPrm_.put("tel", "15099273724");// 手机号
		
		List<Prms> prms = new ArrayList<>();
		prms.add(new Prms(url,httpPrm));
		prms.add(new Prms(url_,httpPrm_));
		
		
		AsynHttpClient.doAsynHttpPost(prms);
	}
	/**
	 * 异步http请求的封装，new Back()是响应回调对象，接收http响应信息
	 * @param prms 多个http请求url和json参数的对象封装
	 * @param jsonPram http请求需要的参数
	 */
	public static void doAsynHttpPost(List<Prms> prms)
	{
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(50000)//连接超时,连接建立时间,三次握手完成时间
				.setSocketTimeout(50000)//请求超时,数据传输过程中数据包之间间隔的最大时间
				.setConnectionRequestTimeout(1000)//使用连接池来管理连接,从连接池获取连接的超时时间
				.build();
		// 配置io线程
		IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
				.setIoThreadCount(Runtime.getRuntime().availableProcessors())
				.setSoKeepAlive(false)//长连接
				.build();
		// 设置连接池大小
		ConnectingIOReactor ioReactor = null;
		try
		{
			ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
		} catch (IOReactorException e)
		{
			e.printStackTrace();
		}
		PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(100);

		final CloseableHttpAsyncClient client = HttpAsyncClients
				.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig)
				.build();
		//遍历
		HttpPost httpPost = null;
		for (Prms p : prms)
		{
			httpPost = new HttpPost(p.getURL());
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setEntity(new StringEntity(p.getHttpPrm().toJSONString(),"UTF-8"));
			client.start();
			// 异步请求
			client.execute(httpPost, new Back());
		}
	}
}
