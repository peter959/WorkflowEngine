package com.mycompany.risorsa.esempiorisorsa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RisorsaRepository extends JpaRepository<RisorsaEntity, Long> {
    RisorsaEntity findByNation(String nation); 
}