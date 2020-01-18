package com.mycompany.MSC.MSC;
 
//import javax.persistence.Entity;

//@Entity
public class MSCEntity { 
  private String name;
	static public String risultato = "";
	
	public MSCEntity() {};
	
	
	public MSCEntity(String name) {
		this.name  = name;
	};

	
	/*@Bean
	public void setName(String name) {
		this.name  = name;
	}*/
	
	public String getName() {
		return this.name;
	}

	static public String getRisultato() {
		return risultato;
	}

	static public void setRisultato(String newRisultato) {
		risultato = risultato + newRisultato;
	}

}