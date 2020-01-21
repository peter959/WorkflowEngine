package com.mycompany.engine.engine;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EngineApplication {

	public static void main(String[] args) {

		SpringApplication.run(EngineApplication.class, args);
		
	}

}