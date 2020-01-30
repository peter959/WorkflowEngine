package com.mycompany.MSA.MSA;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class MSAService {
	
	private static final Logger LOGGER = LogManager.getLogger(MSAService.class);

	void execute() {
		FirebaseManager fm = new FirebaseManager();
		fm.insertResult("microservices/MSA", MSAEntity.getRisultato());
		LOGGER.info("MSA ESEGUITO");
	}
}