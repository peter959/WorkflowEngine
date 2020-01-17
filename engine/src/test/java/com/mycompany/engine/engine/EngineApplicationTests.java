package com.mycompany.engine.engine;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EngineApplicationTests {

	@Autowired
	EngineApplication engine;
	
	@Test
	void contextLoads() {
//		System.out.println("Before");
//		String result = engine.run();
//		System.out.println("result = " + result);
//		Assertions.assertThat(result).isEqualTo("FINISH");
	}

}
