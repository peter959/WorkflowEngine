package com.mycompany.engine.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private EngineApplicationService myService;

    @Override
    public void run(String...args) throws Exception {
        System.out.println("HEEEEEY");
        myService.run();
    }
}