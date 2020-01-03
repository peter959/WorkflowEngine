package com.mycompany.risorsa.esempiorisorsa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RisorsaController { 
    @Autowired
    private Environment environment; 
    
    @Autowired
    private RisorsaRepository repository; 

    @GetMapping("/valore-iva/from/{nation}")
    public int recuperaIva(@PathVariable String nation) {
        RisorsaEntity valore = repository.findByNation(nation);
        valore.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        return valore.print();
    }
}