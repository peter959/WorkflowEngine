package com.mycompany.engine.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import info.bluefoot.scripts.util.circulariterator.CircularIterator;
import info.bluefoot.scripts.util.circulariterator.CircularList;
//import java.util.logging.*;

public class GraphManager {
    static GraphNode start;
    static GraphParser parser;
    static Map<String, GraphNode> nodes;
    static Map<String, GraphEdge> edges;
    //private String path = "/workflow.dot";
    //private static final Logger LOGGER = Logger.getLogger(GraphManager.class.getSimpleName());

    GraphManager () {
        // Properties prop = System.getProperties();
        // prop.setProperty("java.util.logging.config.file", "src/main/resources/LOG/logging.properties");
        
        // try {
        //     LogManager.getLogManager().readConfiguration();
        // } catch (SecurityException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        initialize("/workflow.dot"); 
        start = nodes.get("start");
    };


    // Preleva il grafo dal percorso passato ed effettua il parsing
    public static void getParsed(final String path) {
        ////LOGGER.info("Avvio procedura di parsing");
        try {
            parser = new GraphParser(GraphManager.class.getResourceAsStream(path)) ;
        } 
        catch (Exception e) {
            if(e.getCause().toString().contains("java.lang.NullPointerException")){
                //LOGGER.severe("--- Error: file at " + path + " not found");
                System.out.println("--- Error: file at " + path + " not found");
            }
            else if(e.getCause().toString().contains("missing {GRAPH, DIGRAPH}")){
                //LOGGER.severe("--- Error: missing {GRAPH, DIGRAPH} keyword on the // file");
                System.out.println("--- Error: missing {GRAPH, DIGRAPH} keyword on the // file");
            }
            else {
                //LOGGER.severe(e.getCause().toString());
                System.out.println(e.getCause());
            }
            System.exit(1);
            System.out.println(e.getCause());
        }
        finally {
            ////LOGGER.info("Parsing completato");
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
        ////LOGGER.info("Inizio procedura BFS");

        List<GraphNode> result = new ArrayList<GraphNode>();

        boolean visited[] = new boolean[nodes.size()];
    
        // associa ad ogni nodo un indice, servirà per controllare se i nodi sono stati
        // visitati
        ////LOGGER.info("Creazione mappa [GraphNode, Indice]");
        HashMap<GraphNode, Integer> map = new HashMap<GraphNode, Integer>();
        int j = 0;
        for (final GraphNode node : nodes.values()) {
            map.put(node, j);
            j++;
        }
        ////LOGGER.info("Creazione mappa completata");

        // Create a queue for BFS
        LinkedList<GraphNode> queue = new LinkedList<GraphNode>();

        // Mark the current node as visited and enqueue it
        ////LOGGER.info("Elemento di partenza visitato ed inserito nella coda");
        visited[map.get(s)] = true;
        queue.add(s);
        int nNodes = nodes.size();

        while (queue.size() != 0) {
            if (nNodes == 0)
                break;
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            ////LOGGER.info("Elemento uscito dalla coda e stampato: " + s.getId());
            ////LOGGER.info("Numero di nodi contati: " + nNodes);
            System.out.println(s.getId() + " ");
            result.add(s);


            // prendo tutti i nodi adiacenti e li metto in una lista circolare iterabile
            CircularIterator<GraphNode> i = new CircularList<GraphNode>(getOutcomingNodesFromNode(s)).iterator();
            int counter = getOutcomingNodesFromNode(s).size(); // numero di figli puntati dal nodo s
            int nfr = 0; // numero figli eliminati
            while (counter > 0) {
                GraphNode n = i.next();
                ////LOGGER.info("nodo next " + n.getId());
                ////LOGGER.info("Var counter = " + counter);
                ////LOGGER.info("var nfr = " + nfr);
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
                    ////LOGGER.info("Ho visitato l'elemento " + n.getId());
                    ////LOGGER.info("Ho inserito nella coda " + n.getId());
                    if (!queue.contains(n))
                        queue.add(n); // evita doppioni
                } else
                    counter--;
            }
            nNodes--;
        }
        

        return result;
    }

    /*public static void main(final String[] args) {
        Properties prop = System.getProperties();
        prop.setProperty("java.util.logging.config.file", "src/main/resources/LOG/logging.properties");

        try {
            LogManager.getLogManager().readConfiguration();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ////LOGGER.info("an info msg");
        initialize("/workflow.dot");
        printNodes();
        printEdges();
        

        start = nodes.get("start");

        System.out.println("--- START");

        //BFS(start);        

    }*/

}