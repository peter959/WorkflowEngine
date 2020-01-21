package com.mycompany.engine.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.digraph.parser.GraphNode;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



@Service
public class EngineApplicationService {

	void initFirebase() {
		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream(
				"/Users/pietrovassallo/Documents/GitHub/IvaSpringboot/workflow-engine-db-firebase-adminsdk-1t320-cf74df9b92.json");
			FirebaseOptions options;
		
			options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://workflow-engine-db.firebaseio.com/")
				.build();

			FirebaseApp.initializeApp(options);

		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference("test/giovanni");
		ref.child("users");
		Map<String, MSBean> users = new HashMap<>();
		users.put("asda", new MSBean("MSA"));
		users.put("sesso", new MSBean("MSB"));

		ref.setValueAsync(users); 
	}

	@Autowired
	private MSAEntityProxy MSAproxy;

	@Autowired
	private MSBEntityProxy MSBproxy;

	@Autowired
	private MSCEntityProxy MSCproxy;

	List<GraphNode> workflow;
	GraphManager gm;
	private static final Logger LOGGER = Logger.getLogger(EngineApplication.class.getSimpleName());
	private List<CompletableFuture> tasks;
	private Map<String, CompletableFuture<MSBean>> taskMap = new HashMap<>();

	private Map<String, EntityProxy> proxyMap = new HashMap<>();

	void initializeProxyMap() {
		proxyMap.put("MSA", MSAproxy);
		proxyMap.put("MSB", MSBproxy);
		proxyMap.put("MSC", MSCproxy);
	}

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

	void function(GraphNode microservice, String risultato, CompletableFuture<MSBean> task) {
		proxyMap.get(microservice.getId()).run();
		try {
			risultato = risultato + task.get().getName();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String run() {
		initializeGraphManager();
		initializeProxyMap();

		String risultato = "";

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

				CompletableFuture[] prevTasks = new CompletableFuture[incomingNodes]; // array dei task precedenti a
																						// microservice
				for (int i = 0; i < incomingNodes; i++) {
					prevTasks[i] = taskMap.get(gm.getIncomingNodesFromNode(microservice).get(i).getId());
				}
				CompletableFuture.allOf(prevTasks).join();
				CompletableFuture<MSBean> task = CompletableFuture.supplyAsync(() -> proxyMap.get(microservice.getId()).run());
				taskMap.put(microservice.getId(), task);

				try {
					risultato = risultato + task.get().getName();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (incomingNodes == 1) {
				// CASO IN CUI C'E' UN SOLO MICROSERVIZIO PRECEDERNTE
				GraphNode prev = gm.getIncomingNodesFromNode(microservice).get(0);

				if (prev.getId().equals("start")) {
					// SE E' START NON ATTENDERE NESSUNO
					printLog(microservice.getId() + " " + "NON ATTENDE NESSUNO");

					CompletableFuture<MSBean> task = CompletableFuture.supplyAsync(() -> proxyMap.get(microservice.getId()).run());
					taskMap.put(microservice.getId(), task);
					try {
						risultato = risultato + task.get().getName();
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					// SE NON E' START, ATTENDI QUELLO PRECEDENTE
					printLog(microservice.getId() + " " + "ATTENDE " + prev.getId());
					CompletableFuture<MSBean> prevTask = taskMap.get(prev.getId());
					CompletableFuture<MSBean> task = prevTask.thenApply(result -> proxyMap.get(microservice.getId()).run());
					taskMap.put(microservice.getId(), task);
					try {
						risultato = risultato + task.get().getName();
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return risultato;
	}

}
