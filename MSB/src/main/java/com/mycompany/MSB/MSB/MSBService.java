package com.mycompany.MSB.MSB;

import org.springframework.stereotype.Service;

@Service
public class MSBService {
	void execute() {
		FirebaseManager fm = new FirebaseManager();
		fm.insertResult("microservices/MSB", MSBEntity.getRisultato());
	}

	MSBEntity returnEntity() {
		MSBEntity msb = new MSBEntity("MSB");
		return msb;
	}
}