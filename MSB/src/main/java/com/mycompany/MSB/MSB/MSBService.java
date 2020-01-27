package com.mycompany.MSB.MSB;

import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class MSBService {
	private static final Logger LOGGER = LogManager.getLogger(MSBService.class);

	void execute() {
		FirebaseManager fm = new FirebaseManager();
		fm.insertResult("microservices/MSB", MSBEntity.getRisultato());
		LOGGER.info("MSB ESEGUITO");
	}

	MSBEntity returnEntity() {
		MSBEntity msb = new MSBEntity("MSB");
		return msb;
	}
}