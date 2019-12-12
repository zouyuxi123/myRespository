package com.hpe.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//标注是一个主程序 
public class Application {

	public static void main(String[] args) {
		//启动SpringBoot应用
		SpringApplication.run(Application.class, args);
	}
	
}
