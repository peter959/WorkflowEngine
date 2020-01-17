package com.mycompany.engine.engine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MSABean {
	private String name;
	
	public MSABean() {};
	
	
	public MSABean(String name) {
		this.name  = name;
	};
	
	/*@Bean
	public void setName(String name) {
		this.name  = name;
	}*/
	
	@Bean
	public String getName() {
		return this.name;
	}

}
