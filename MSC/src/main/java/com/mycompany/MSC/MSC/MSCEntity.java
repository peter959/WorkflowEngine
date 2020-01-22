package com.mycompany.MSC.MSC;
 
//import javax.persistence.Entity;

//@Entity
public class MSCEntity { 
	private String name;

	public MSCEntity() {};
	
	
	public MSCEntity(String name) {
		this.name  = name;
	};


	public String getName() {
		return this.name;
	}

}