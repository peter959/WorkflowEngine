package com.mycompany.engine.engine;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EngineApplication {
	// @Autowired
	// static EngineApplicationService engineApplicationService;

	public static void main(String[] args) {
		SpringApplication.run(EngineApplication.class, args);

		ApplicationContext applicationContext = (ApplicationContext) SpringApplication.run(EngineApplication.class,args);

		EngineApplicationService eap = ((BeanFactory) applicationContext).getBean(EngineApplicationService.class);

		eap.run();
		
	}

}