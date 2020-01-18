package com.mycompany.applicaiva.esempioapplicaiva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
public class EsempioApplicaivaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EsempioApplicaivaApplication.class, args);
	}

}
