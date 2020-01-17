package com.mycompany.MSA.MSA;

import org.springframework.stereotype.Service;

@Service
public class MSAService {
	MSAEntity returnEntity() {
		return new MSAEntity("MSA");
	}
}