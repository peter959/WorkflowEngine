package com.mycompany.engine.engine;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringBootApplication
public class EngineApplication{
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(EngineApplication.class, args);
	}
}