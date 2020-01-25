package com.mycompany.engine.engine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EngineApplicationController {
	@Autowired
	EngineApplicationService engineApplicationService;

	
	@RequestMapping("/run")
	void run() {
		engineApplicationService.run();
	}
}