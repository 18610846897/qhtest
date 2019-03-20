package com.qh.asynhttp;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

public class Back implements FutureCallback<HttpResponse>
{
	private long start = System.currentTimeMillis();

	Back()
	{
		System.out.println("请求前可以做点事情！");
	}

	public void completed(HttpResponse httpResponse)
	{
		try
		{
			System.out.println("cost is:" + (System.currentTimeMillis() - start) + ":"
					+ EntityUtils.toString(httpResponse.getEntity()));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void failed(Exception e)
	{
		System.out.println(" cost is:" + (System.currentTimeMillis() - start) + ":" + e);
	}

	public void cancelled()
	{
		System.out.println("关闭");
	}
}
