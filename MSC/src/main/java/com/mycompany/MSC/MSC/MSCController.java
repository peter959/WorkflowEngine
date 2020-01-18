package com.mycompany.MSC.MSC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSCController {
	@Autowired
	MSCService mscService;

	@RequestMapping("/MSC")
	MSCEntity run() {
		MSCEntity mscEntity = mscService.returnEntity();
		return mscEntity;
	}
}