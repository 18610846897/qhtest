package com.qh.asynhttp;

import com.alibaba.fastjson.JSONObject;

public class Prms
{
	private String URL;
	private JSONObject httpPrm;
	
	
	public Prms(String uRL, JSONObject httpPrm)
	{
		super();
		URL = uRL;
		this.httpPrm = httpPrm;
	}
	public String getURL()
	{
		return URL;
	}
	public void setURL(String uRL)
	{
		URL = uRL;
	}
	public JSONObject getHttpPrm()
	{
		return httpPrm;
	}
	public void setHttpPrm(JSONObject httpPrm)
	{
		this.httpPrm = httpPrm;
	}
	
}
