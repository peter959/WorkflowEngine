package com.mycompany.MSB.MSB;
 
//import javax.persistence.Entity;

//@Entity
public class MSBEntity { 
  private String name;
  final static private String risultato = "Pluto";
	
	public MSBEntity() {};
	
	
	public MSBEntity(String name) {
		this.name  = name;
	};

	
	/*
	public void setName(String name) {
		this.name  = name;
	}*/
	
	public String getName() {
		return this.name;
	}

	static public String getRisultato() {
		return risultato;
	}


}