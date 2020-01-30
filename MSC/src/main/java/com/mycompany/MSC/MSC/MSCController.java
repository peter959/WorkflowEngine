package com.mycompany.MSC.MSC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class MSCController {

	private static final Logger LOGGER = LogManager.getLogger(MSCController.class);
	
	@Autowired
	MSCService mscService;

	@RequestMapping("/MSC")
	Boolean run() {
		LOGGER.info("in esecuzione...");
		mscService.execute();
		return true;
	}
}