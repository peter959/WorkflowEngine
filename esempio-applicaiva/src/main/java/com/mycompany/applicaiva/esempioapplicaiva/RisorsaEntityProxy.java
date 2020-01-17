package com.mycompany.applicaiva.esempioapplicaiva;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository 
@FeignClient(name="risorsa") //
@RibbonClient(name="risorsa") // uso eureka 
public interface RisorsaEntityProxy {
    @GetMapping("/valore-iva/from/{nation}")
    public int recuperaIva(@PathVariable("nation") String nation);
}