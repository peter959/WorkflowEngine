package com.mycompany.engine.engine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MSBean {
	private String name;
	
	public MSBean() {};
	
	public MSBean(String name) {
		this.name  = name;
	};

	@Bean
	public String getName() {
		return this.name;
	}
}
