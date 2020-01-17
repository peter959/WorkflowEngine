package com.mycompany.MSA.MSA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSAController {
	@Autowired
	MSAService msaService;

	@RequestMapping("/MSA")
	MSAEntity run() {
		return msaService.returnEntity();
	}
}