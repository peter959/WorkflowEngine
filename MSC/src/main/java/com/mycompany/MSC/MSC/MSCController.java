package com.mycompany.MSC.MSC;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSCController {
	@RequestMapping("/MSC")
	String run() {
		return "MSC";
	}
}