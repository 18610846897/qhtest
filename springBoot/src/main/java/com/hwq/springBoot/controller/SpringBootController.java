package com.hwq.springBoot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootController
{
	/*@Value("${hwq.msg}")
	private String msg;*/
	
	@RequestMapping("/httpPost")
	public String sayHello(String json)
	{
		System.out.println("-----------------httpPost------------"+json);
		return json;
	}
}
