package com.mycompany.MSA.MSA;
 
//import javax.persistence.Entity;

//@Entity
public class MSAEntity { 
    private String name;

    public MSAEntity() {}

    public MSAEntity(String name) {
        this.name = name;
    }

    public void setName(String name) {
		this.name  = name;
    }
    
	public String getName() {
		return this.name;
	}
    

}