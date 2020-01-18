package com.mycompany.engine.engine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MSBean {
	private String name;
	static public String risultato = "";
	
	public MSBean() {};
	
	
	public MSBean(String name) {
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

	@Bean
	static public String getRisultato() {
		return risultato;
	}

	// @Bean
	// static public void setRisultato(String newRisultato) {
	// 	risultato = risultato + newRisultato;
	// }

}
