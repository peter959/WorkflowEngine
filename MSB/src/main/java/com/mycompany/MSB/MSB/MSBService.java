package com.mycompany.MSB.MSB;

import org.springframework.stereotype.Service;

@Service
public class MSBService {
	MSBEntity returnEntity() {
		return new MSBEntity("MSB");
	}
}