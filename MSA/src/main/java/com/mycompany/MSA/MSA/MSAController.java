package com.mycompany.MSA.MSA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class MSAController {
	@Autowired
	MSAService msaService;

	private static final Logger LOGGER = LogManager.getLogger(MSAController.class);

	@RequestMapping("/MSA")
	MSAEntity run() {
		LOGGER.info("in esecuzione...");
		msaService.execute();
		return msaService.returnEntity();
	}
}
