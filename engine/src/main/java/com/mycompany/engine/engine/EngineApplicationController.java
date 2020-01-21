package com.mycompany.engine.engine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EngineApplicationController {
	@Autowired
	EngineApplicationService EngineApplicationService;
	
	@Autowired
	private MSAEntityProxy proxy;
	
	@RequestMapping("/test")
	void test() {
		/*MSBean response = proxy.run();
		return response.getName();*/
		//EngineApplicationService.initFirebase();
	}

	
	@RequestMapping("/BFS-print")
	List<String> print() {
		return EngineApplicationService.print();
	}

	@RequestMapping("/run")
	void run() {
		EngineApplicationService.run();
	}
	

}