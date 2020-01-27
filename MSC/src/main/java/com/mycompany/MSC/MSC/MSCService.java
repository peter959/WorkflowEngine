package com.mycompany.MSC.MSC;

import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class MSCService {

	private static final Logger LOGGER = LogManager.getLogger(MSCService.class);

	void execute() {
		FirebaseManager fm = new FirebaseManager();
		fm.getAndUnion("microservices/MSA/result", "microservices/MSB/result", "microservices/MSC");
		LOGGER.info("MSC ESEGUITO");
	}

	MSCEntity returnEntity() {
		MSCEntity msc = new MSCEntity("MSC");
		return msc;
	}
}