package com.mycompany.engine.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import info.bluefoot.scripts.util.circulariterator.CircularIterator;
import info.bluefoot.scripts.util.circulariterator.CircularList;

public class GraphManager {
    static GraphNode start;
    static GraphParser parser;
    static Map<String, GraphNode> nodes;
    static Map<String, GraphEdge> edges;
    private static final Logger LOGGER = LogManager.getLogger(GraphManager.class);


    GraphManager () {
        initialize("/workflow.dot"); 
        start = nodes.get("start");
    };


    // Preleva il grafo dal percorso passato ed effettua il parsing
    public static void getParsed(final String path) {
        try {
            parser = new GraphParser(GraphManager.class.getResourceAsStream(path)) ;
        } 
        catch (Exception e) {
            if(e.getCause().toString().contains("java.lang.NullPointerException")){
                LOGGER.error("--- Error: file at " + path + " not found");
            }
            else if(e.getCause().toString().contains("missing {GRAPH, DIGRAPH}")){
                LOGGER.error("--- Error: missing {GRAPH, DIGRAPH} keyword on the // file");
            }
            else {
                LOGGER.error(e.getCause().toString());
            }
            System.exit(1);
        }
    }

    // Inizializzazione dei Nodi e Edge in modo da renderli disponibili altrove
    public static void initialize(final String path) {
        getParsed(path);
        nodes = parser.getNodes();
        edges = parser.getEdges();
    }

    // Ritorna una lista di NODI USCENTI da un NODO in input
    public  List<GraphNode> getOutcomingNodesFromNode(final GraphNode node) {
        final Map<String, GraphEdge> edges = parser.getEdges();
        final List<GraphNode> outcoming = new ArrayList<GraphNode>();
        for (final GraphEdge edge : edges.values()) {
            if (edge.getNode1().getId().equals(node.getId()))
                outcoming.add(edge.getNode2());
        }
        return outcoming;
    }

    // Stampa NODI USCENTI da un NODO in input
    public  void printOutcomingNodes(final GraphNode node) {
        System.out.print("--- OUTcoming '" + node.getId() + "':{");
        getOutcomingNodesFromNode(node).forEach(n -> System.out.print(n.getId() + ","));
        System.out.println("}");
    }

    // Ritorna una lista di NODI USCENTI da un NODO in input
    public  List<GraphNode> getIncomingNodesFromNode(final GraphNode node) {
        final Map<String, GraphEdge> edges = parser.getEdges();
        final List<GraphNode> incoming = new ArrayList<GraphNode>();
        for (final GraphEdge edge : edges.values()) {
            if (edge.getNode2().getId().equals(node.getId()))
                incoming.add(edge.getNode1());
        }
        return incoming;
    }

    // Stampa NODI ENTRANTI da un NODO in input
    public void printIncomingNodes(final GraphNode node) {
        System.out.print("--- INcoming '" + node.getId() + "':{");
        getIncomingNodesFromNode(node).forEach(n -> System.out.print(n.getId() + ","));
        System.out.println("}");
    }

    // Stampa tutti i nodi del grafo
    public static void printNodes() {
        System.out.println("--- Nodes:");
        for (final GraphNode node : nodes.values()) {
            System.out.println(node.getId() + " " + node.getAttributes());
        }
    }

    // Stampa tutti i collegamenti del grafo
    public static void printEdges() {
        System.out.println("--- Edges:");
        for (final GraphEdge edge : edges.values()) {
            System.out.println(edge.getNode1().getId() + "->" + edge.getNode2().getId() + " " + edge.getAttributes());
        }
    }

    public List<GraphNode> BFS(/*GraphNode s*/) {
        GraphNode s = start;
        LOGGER.info("Inizio procedura BFS");

        List<GraphNode> result = new ArrayList<GraphNode>();

        boolean visited[] = new boolean[nodes.size()];
    
        // associa ad ogni nodo un indice, servirà per controllare se i nodi sono stati
        // visitati
        LOGGER.debug("Creazione mappa [GraphNode, Indice]");
        HashMap<GraphNode, Integer> map = new HashMap<GraphNode, Integer>();
        int j = 0;
        for (final GraphNode node : nodes.values()) {
            map.put(node, j);
            j++;
        }
        LOGGER.debug("Creazione mappa completata");

        // Create a queue for BFS
        LinkedList<GraphNode> queue = new LinkedList<GraphNode>();

        // Mark the current node as visited and enqueue it
        LOGGER.debug("Elemento di partenza visitato ed inserito nella coda");
        visited[map.get(s)] = true;
        queue.add(s);
        int nNodes = nodes.size();

        while (queue.size() != 0) {
            if (nNodes == 0)
                break;
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            LOGGER.debug("Elemento uscito dalla coda e stampato: " + s.getId());
            LOGGER.debug("Numero di nodi contati: " + nNodes);
            //System.out.println(s.getId() + " ");
            result.add(s);


            // prendo tutti i nodi adiacenti e li metto in una lista circolare iterabile
            CircularIterator<GraphNode> i = new CircularList<GraphNode>(getOutcomingNodesFromNode(s)).iterator();
            int counter = getOutcomingNodesFromNode(s).size(); // numero di figli puntati dal nodo s
            int nfr = 0; // numero figli eliminati
            while (counter > 0) {
                GraphNode n = i.next();
                LOGGER.debug("nodo next " + n.getId());
                LOGGER.debug("Var counter = " + counter);
                LOGGER.debug("var nfr = " + nfr);
                List<GraphNode> inNodes = getIncomingNodesFromNode(n);
                boolean isTheRightNode = true; // indica se il nodo ha tutti nodi entranti visitati
                for (GraphNode x : inNodes) {
                    if (visited[map.get(x)] == false) { // se un nodo entrante non è stato visitato allora non è quello
                                                        // giusto esci dal for
                        isTheRightNode = false; // Oppure c'è solo un nodo disponibile nella lista dei nodi entranti && counter>1
                        // System.out.println("sono " + n.getId() + " e mi ha fatto uscire " +
                        // x.getId());
                        break;
                    }
                }
                if (isTheRightNode == true && visited[map.get(n)] == false) { // se il nodo è quello giusto e gia non è
                                                                              // stato visitato (la seconda evita i
                                                                              // cicli)
                    nfr++; // un figlio viene rimosso (e viene inserito nella cosa per essere stampato)
                    counter = getOutcomingNodesFromNode(s).size() - nfr; // aggiorna il contatore dei figli con il
                                                                         // numero corretto
                    visited[map.get(n)] = true;
                    LOGGER.debug("Ho visitato l'elemento " + n.getId());
                    LOGGER.debug("Ho inserito nella coda " + n.getId());
                    if (!queue.contains(n))
                        queue.add(n); // evita doppioni
                } else
                    counter--;
            }
            nNodes--;
        }
        return result;
    }
}