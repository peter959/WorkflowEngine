package com.mycompany.MSB.MSB;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSBController {
	@RequestMapping("/MSB")
	String run() {
		return "MSB";
	}
}