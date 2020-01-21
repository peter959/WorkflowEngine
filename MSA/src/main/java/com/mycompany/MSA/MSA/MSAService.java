package com.mycompany.MSA.MSA;

import org.springframework.stereotype.Service;

@Service
public class MSAService {
	
	void execute() {
		FirebaseManager fm = new FirebaseManager();
		fm.insertResult("microservices/MSA", MSAEntity.getRisultato());
	}

	MSAEntity returnEntity() {
		MSAEntity msa = new MSAEntity("MSA");
		return msa;
	}

}