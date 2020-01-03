package com.mycompany.applicaiva.esempioapplicaiva;

import org.springframework.context.annotation.Bean;

public class ApplicaIvaBean {
    private Long id;
    private String nation;
     private int iva;
    private int port;
     private float prezzo;
    private float prezzotot;
    public ApplicaIvaBean() {

    }

    public ApplicaIvaBean(Long id, String nation, int iva, int port, float prezzo, float tot) {
        this.id = id;
        this.nation = nation;
        this.iva = iva;
        this.port = port;
        this.prezzo = prezzo;
        this.prezzotot = tot;
	}
    
	Long getId() {
        return this.id;
    }
    
    int getIva() {
        return this.iva;
    }
    
    int getPort() {
        return this.port;
    }

    float print() {
        return this.prezzotot;
    }
 }