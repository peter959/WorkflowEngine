package com.mycompany.engine.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.digraph.parser.GraphNode;


@Service
public class EngineApplicationService {

	@Autowired
	private MSAEntityProxy MSAproxy;

	@Autowired
	private MSBEntityProxy MSBproxy;

	@Autowired
	private MSCEntityProxy MSCproxy;

	List<GraphNode> workflow;
	GraphManager gm;
	private static final Logger LOGGER = LogManager.getLogger(EngineApplicationService.class);
	private Map<String, CompletableFuture<Boolean>> taskMap = new HashMap<>();
	private Map<String, EntityProxy> proxyMap = new HashMap<>();

	void initializeProxyMap() {
		proxyMap.put("MSA", MSAproxy);
		proxyMap.put("MSB", MSBproxy);
		proxyMap.put("MSC", MSCproxy);
	}

	void initializeGraphManager() {
		gm = new GraphManager();
		workflow = gm.BFS();
	}

	private List<String> printBFS() {
		List<String> workflowString = new ArrayList<String>();
		for (GraphNode node : workflow) {
			workflowString.add(node.getId());
		}
		return workflowString;
	}

	public void run() {
		initializeGraphManager();
		initializeProxyMap();
		LOGGER.info("Workflow da eseguire: " + printBFS());
		
		for (GraphNode microservice : workflow) {

			int incomingNodes = gm.getIncomingNodesFromNode(microservice).size();

			if (incomingNodes > 1) {
				// CASO IN CUI CI SONO PIU MICROSERVIZI DA ATTENDERE
				LOGGER.info(microservice.getId() + " " + "ATTENDE" + " " + gm.getIncomingNodesFromNode(microservice) ); // incomingNodes può essere ottenuto in getWaitingList senza passarla

				CompletableFuture[] prevTasks = new CompletableFuture[incomingNodes]; // array dei task precedenti a microservice

				for (int i = 0; i < incomingNodes; i++) {
					prevTasks[i] = taskMap.get(gm.getIncomingNodesFromNode(microservice).get(i).getId());
				}
				CompletableFuture.allOf(prevTasks).join();
				CompletableFuture<Boolean> task = CompletableFuture.supplyAsync(() -> proxyMap.get(microservice.getId()).run());
				LOGGER.info(microservice.getId() + " LANCIATO");
				taskMap.put(microservice.getId(), task);

			} else if (incomingNodes == 1) {
				// CASO IN CUI C'E' UN SOLO MICROSERVIZIO PRECEDERNTE
				GraphNode prev = gm.getIncomingNodesFromNode(microservice).get(0);

				if (prev.getId().equals("start")) {
					// SE E' START NON ATTENDERE NESSUNO
					LOGGER.info(microservice.getId() + " " + "NON ATTENDE NESSUNO");

					CompletableFuture<Boolean> task = CompletableFuture.supplyAsync(() -> proxyMap.get(microservice.getId()).run());
					LOGGER.info(microservice.getId() + " LANCIATO");
					taskMap.put(microservice.getId(), task);
				}
				else {
					// SE NON E' START, ATTENDI QUELLO PRECEDENTE
					LOGGER.info(microservice.getId() + " " + "ATTENDE " + prev.getId());
					CompletableFuture<Boolean> prevTask = taskMap.get(prev.getId());
					CompletableFuture<Boolean> task = prevTask.thenApply(result -> proxyMap.get(microservice.getId()).run());
					LOGGER.info(microservice.getId() + " LANCIATO");
					taskMap.put(microservice.getId(), task);
				}
			}
		}
	}

}
