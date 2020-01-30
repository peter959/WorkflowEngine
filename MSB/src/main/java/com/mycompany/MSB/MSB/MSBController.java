package com.mycompany.MSB.MSB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class MSBController {
	@Autowired
	MSBService msbService;

	private static final Logger LOGGER = LogManager.getLogger(MSBController.class);

	@RequestMapping("/MSB")
	Boolean run() {
		LOGGER.info("in esecuzione...");
		msbService.execute();
		return true;
	}
}