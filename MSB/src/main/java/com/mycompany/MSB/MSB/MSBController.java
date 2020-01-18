package com.mycompany.MSB.MSB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSBController {
	@Autowired
	MSBService msbService;

	@RequestMapping("/MSB")
	MSBEntity run() {
		MSBEntity msbEntity = msbService.returnEntity();
		return msbEntity;
	}
}