package com.mycompany.MSA.MSA;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSAController {
	@RequestMapping("/MSA")
	String run() {
		return "MSA";
	}
}