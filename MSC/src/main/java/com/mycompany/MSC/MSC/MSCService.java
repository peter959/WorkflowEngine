package com.mycompany.MSC.MSC;

import org.springframework.stereotype.Service;

@Service
public class MSCService {
	void execute() {
		FirebaseManager fm = new FirebaseManager();
		fm.getAndUnion("microservices/MSA/result", "microservices/MSB/result", "microservices/MSC");
	}

	MSCEntity returnEntity() {
		MSCEntity msc = new MSCEntity("MSC");
		return msc;
	}
}