package com.mycompany.engine.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.paypal.digraph.parser.GraphNode;

@Service
public class EngineApplicationService {
	
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

}
