package com.mycompany.engine.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

import com.paypal.digraph.parser.GraphNode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EngineApplication {
	List<GraphNode> workflow;
	GraphManager gm;
	private static final Logger LOGGER = Logger.getLogger(EngineApplication.class.getSimpleName());
	private List<CompletableFuture> tasks;
	private Map<String, CompletableFuture<String>> taskMap = new HashMap<>();

	void initializeGraphManager() {
		gm = new GraphManager();
		workflow = gm.BFS();
		tasks = new ArrayList<CompletableFuture>();

		Properties prop = System.getProperties();
		prop.setProperty("java.util.logging.config.file", "src/main/resources/LOG/loggingEngine.properties");

		try {
			LogManager.getLogManager().readConfiguration();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/BFS-print")
	List<String> print() {
		
		initializeGraphManager();
		List<String> workflowString = new ArrayList<String>();
		for (GraphNode node : workflow) {
			workflowString.add(node.getId());
		}
		return workflowString;
	}

	int printLog(GraphNode microservice) {
		LOGGER.info("eseguo " + microservice.getId());
		return 1;
	}

	String printLog(String microservice) {
		LOGGER.info(microservice);
		return microservice;
	}

	@RequestMapping("/run")
	String run() {
		
		initializeGraphManager();
		for (GraphNode microservice : workflow) {
			
			int incomingNodes = gm.getIncomingNodesFromNode(microservice).size();
			
			if (incomingNodes > 1) {
				// CASO IN CUI CI SONO PIU MICROSERVIZI DA ATTENDERE
				/***********************************************/
				String string = "";
				for (int i = 0; i < incomingNodes; i++) {
					string = string + " " + gm.getIncomingNodesFromNode(microservice).get(i).getId();
				}
				printLog(microservice.getId() + " " + "ATTENDE" + " " + string);
				/***********************************************/
				
				
				CompletableFuture[] prevTasks = new CompletableFuture[incomingNodes];  	//array dei task precedenti a microservice
				for (int i = 0; i < incomingNodes; i++) {
					prevTasks[i] = taskMap.get(gm.getIncomingNodesFromNode(microservice).get(i).getId());
				}
				CompletableFuture.allOf(prevTasks).join();
				CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> printLog(microservice.getId()));
				taskMap.put(microservice.getId(), task);
				
				
				
				
			} else if (incomingNodes == 1) {
				// CASO IN CUI C'E' UN SOLO MICROSERVIZIO PRECEDERNTE
				GraphNode prev = gm.getIncomingNodesFromNode(microservice).get(0);
				
				if (prev.getId().equals("start")) {
					// SE E' START NON ATTENDERE NESSUNO
					printLog(microservice.getId() + " " + "NON ATTENDE NESSUNO");	
					
					CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> printLog(microservice.getId()));
					taskMap.put(microservice.getId(), task);
				}
				else {
					// SE NON E' START, ATTENDI QUELLO PRECEDENTE
					printLog(microservice.getId() + " " + "ATTENDE " + prev.getId());
					
					CompletableFuture<String> prevTask = taskMap.get(prev.getId());
					CompletableFuture<String> task = prevTask.thenApply(result -> printLog(microservice.getId()));
					taskMap.put(microservice.getId(), task);
				}
			}
		}
		return "FINISH";
	}

	@RequestMapping("/run2")
	void run2() {
		CompletableFuture<String> t1 = CompletableFuture.supplyAsync(() -> printLog("a"));
		CompletableFuture<String> t2 = CompletableFuture.supplyAsync(() -> printLog("b"));
		CompletableFuture<String> t3 = CompletableFuture.supplyAsync(() -> printLog("c"));

		CompletableFuture<String> t4 = t3.thenApply(result -> printLog("f"));
	
		CompletableFuture[] t12 = new CompletableFuture[2];
		t12[0] = t1; t12[1] = t2;
		CompletableFuture.allOf(t12).join();
		CompletableFuture<String> t5 = CompletableFuture.supplyAsync(() -> printLog("d"));

		CompletableFuture[] t14 = new CompletableFuture[2];
		t14[0] = t1; t14[1] = t4;
		CompletableFuture.allOf(t14).join();
		CompletableFuture<String> t6 = CompletableFuture.supplyAsync(() -> printLog("e"));
	
	}


	public static void main(String[] args) {
		SpringApplication.run(EngineApplication.class, args);
		
	}

}