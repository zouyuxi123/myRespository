package com.hpe.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {
	
	@Autowired
	private Environment environment;
	@Value("${cn.hpe.name}")
	private String hpeName;
	@Value("${cn.hpe.url}")
	private String hpeUrl;
	@Value("${com.hpe.name}")
	private String Name;
	
	@RequestMapping("/test")
	public String sayHello() {
		System.out.println("a-name=" + hpeName);
		System.out.println("b-url=" + hpeUrl);
		System.out.println("hpeu-name=" + Name);
		return "Hello Wolrd!";
	}
}
