package com.mycompany.engine.engine;

import com.google.api.client.util.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EngineApplication /*implements ApplicationRunner*/{
	
	public static void main(String[] args) {
		SpringApplication.run(EngineApplication.class, args);
	}
	
/*
	@Override
    public void run(ApplicationArguments args) throws Exception {

		//myService.print();
		System.out.println("non hai inserito parametri corretti");

		//System.out.println(args.getOptionValues("arguments").get(0).equals("run"));
		
		if (args.getOptionValues("arguments").get(0).equals("run")) {
			myService.run();
		}
		else if (args.getOptionValues("arguments").get(0).equals("print")){
			myService.print();
		}
		else {
			System.out.println("non hai inserito parametri corretti");
		}

        for (String name : args.getOptionNames()){
            System.out.println("arg-" + name + "=" + args.getOptionValues(name).get(0));
		}
		
        //boolean containsOption = args.containsOption("person.name");
        //logger.info("Contains person.name: " + containsOption);
	}
*/

}