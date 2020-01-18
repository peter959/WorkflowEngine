package com.mycompany.risorsa.esempiorisorsa;
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.Id;

@Entity
public class RisorsaEntity { 
    @Id
    private Long id; 
    @Column(name="nation")
    private String nation; 
    private int iva;
    private int port;
    public RisorsaEntity() {}
    public RisorsaEntity(Long id, String nation, int iva, int port) { 
        super();
        this.id = id;
        this.nation = nation;
        this.iva = iva; 
        this.port = port;
    }
    public void setPort(int port) { 
        this.port = port;
    }
    public int print() {
        return this.iva;
    }
}