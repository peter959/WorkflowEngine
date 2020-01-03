package com.mycompany.applicaiva.esempioapplicaiva;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicaIvaController { 
    @Autowired
    private RisorsaEntityProxy proxy;
    
    @GetMapping("/valore-iva/from/{nation}/{prezzo}")
    public float calcolaPrezzo(@PathVariable String nation, @PathVariable float prezzo) {
        int response = proxy.recuperaIva(nation);
        float tot = prezzo + (prezzo*((float)response/100));
        return tot;
        //return new ApplicaIvaBean(response.getId(), nation, response.getIva(), response.getPort(), prezzo, tot).print();
        
    } 
}