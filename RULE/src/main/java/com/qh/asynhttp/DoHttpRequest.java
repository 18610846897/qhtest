package com.qh.asynhttp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class DoHttpRequest
{
	/*
	 * @Value("${hwq.msg}") private String msg;
	 */

	@RequestMapping(value = "qh")
	public void asynHttpDo(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("-----------------httpPost url------------" + request.getRequestURL());
		InputStream inputStream = null;
		try
		{
			inputStream = request.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader stream = new BufferedReader(reader);
			String value = "";
			String message = "";
			while (null != (value = stream.readLine()))
			{
				message += value;
			}
			System.out.println("-----------------httpPost body ------------"+JSONObject.toJSONString(message));
			
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			JSONObject obj = new JSONObject();
			obj.put("ev", "A");
			obj.put("sc", "30");
			obj.put("as", "test1");
			response.getWriter().print(obj.toJSONString());
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(inputStream != null)
				{
					inputStream.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "xxw")
	public void asynHttp(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("-----------------httpdo url------------" + request.getRequestURL());
		InputStream inputStream = null;
		try
		{
			inputStream = request.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader stream = new BufferedReader(reader);
			String value = "";
			String message = "";
			while (null != (value = stream.readLine()))
			{
				message += value;
			}
			System.out.println("-----------------httpdo body ------------"+JSONObject.toJSONString(message));
			
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			JSONObject obj = new JSONObject();
			obj.put("ev", "B");
			obj.put("sc", "40");
			obj.put("as", "test2");
			response.getWriter().print(obj.toJSONString());
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(inputStream != null)
				{
					inputStream.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
