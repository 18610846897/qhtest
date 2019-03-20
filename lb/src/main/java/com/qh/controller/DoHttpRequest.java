package com.qh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qh.asynhttp.AsynHttpClient;
import com.qh.asynhttp.Prms;

@RestController
public class DoHttpRequest
{
	/*
	 * @Value("${hwq.msg}") private String msg;
	 */

	@RequestMapping(value = "httpPost")
	public void asynHttpDo(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("-----------------request url------------" + request.getRequestURL());
		try(BufferedReader stream = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8")))
		{
			String value = "";
			String message = "";
			while (null != (value = stream.readLine()))
			{
				message += value;
			}
			System.out.println("-----------------httpPost body ------------"+JSONObject.toJSONString(message));
			
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			
			// 构造请求
			String url = "http://127.0.0.1:8082/qh";
			String url_ = "http://127.0.0.1:8082/xxw";
			JSONObject httpPrm = (JSONObject)JSON.parse(message);
			
			JSONObject httpPrm_ = new JSONObject();
			httpPrm_.put("name", "张二蛋");// 姓名
			httpPrm_.put("ID", "612727198900203518");// 身份证号码
			httpPrm_.put("tel", "15099273724");// 手机号
			
			List<Prms> prms = new ArrayList<>();
			prms.add(new Prms(url,httpPrm));
			prms.add(new Prms(url_,httpPrm_));
			
			//方法中的回调Backl类处理响应
			AsynHttpClient.doAsynHttpPost(prms);
			
			//response.getWriter().print(obj.toJSONString());//上面方法封装了该实现
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
