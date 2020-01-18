package com.mycompany.MSC.MSC;

import org.springframework.stereotype.Service;

@Service
public class MSCService {
	MSCEntity returnEntity() {
		return new MSCEntity("MSC");
	}
}