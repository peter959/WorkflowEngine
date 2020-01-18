package com.mycompany.MSA.MSA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSAController {
	@Autowired
	MSAService msaService;

	@RequestMapping("/MSA")
	MSAEntity run() {
		MSAEntity msaEntity = msaService.returnEntity();
		return msaEntity;
	}
}

	// @RequestMapping("/MSA")
	// MSAEntity run(@Nullable List<String> results) {
	// 	MSAEntity msaEntity = msaService.returnEntity();
	// 	String tmp = "";
	// 	for (String s: results) {
	// 		tmp.concat(s);
	// 	}
	// 	msaEntity.setRisultato(tmp + "MSA");
	// 	return msaEntity;
	// }
