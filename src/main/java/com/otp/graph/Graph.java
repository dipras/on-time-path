package com.otp.graph;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
public class Graph {
    private final Map<String, Node> nodes;   
    public Graph() {
        this.nodes = new HashMap<>();
    }
    public Node addNode(String name) {
        Node node = new Node(name);
        nodes.put(name, node);
        return node;
    }
    public Node getNode(String name) {
        return nodes.get(name);
    }
    public void addEdge(String sourceName, String destName, int weight) {
        Node source = nodes.get(sourceName);
        Node dest = nodes.get(destName);
        if (source != null && dest != null) {
            source.addEdge(dest, weight);
            dest.addEdge(source, weight);  
        }
    }
    public void addDirectedEdge(String sourceName, String destName, int weight) {
        Node source = nodes.get(sourceName);
        Node dest = nodes.get(destName);
        if (source != null && dest != null) {
            source.addEdge(dest, weight);
        }
    }
    public Collection<Node> getAllNodes() {
        return nodes.values();
    }
    public boolean containsNode(String name) {
        return nodes.containsKey(name);
    }
    public int size() {
        return nodes.size();
    }
    public static Graph createSurabayaMap() {
        Graph graph = new Graph();
        graph.addNode("Rumah (Wonokromo)");       
        graph.addNode("Darmo");
        graph.addNode("Tegalsari");
        graph.addNode("Gubeng");
        graph.addNode("Manyar");
        graph.addNode("Ngagel");
        graph.addNode("Kertajaya");
        graph.addNode("Mulyorejo");
        graph.addNode("Sukolilo");
        graph.addNode("Kampus ITS");               
        graph.addEdge("Rumah (Wonokromo)", "Darmo", 4);
        graph.addEdge("Rumah (Wonokromo)", "Ngagel", 3);
        graph.addEdge("Rumah (Wonokromo)", "Gubeng", 5);
        graph.addEdge("Darmo", "Tegalsari", 3);
        graph.addEdge("Darmo", "Ngagel", 2);
        graph.addEdge("Tegalsari", "Gubeng", 4);
        graph.addEdge("Tegalsari", "Manyar", 5);
        graph.addEdge("Gubeng", "Ngagel", 2);
        graph.addEdge("Gubeng", "Kertajaya", 3);
        graph.addEdge("Gubeng", "Manyar", 4);
        graph.addEdge("Ngagel", "Kertajaya", 3);
        graph.addEdge("Manyar", "Kertajaya", 2);
        graph.addEdge("Manyar", "Mulyorejo", 4);
        graph.addEdge("Kertajaya", "Mulyorejo", 3);
        graph.addEdge("Kertajaya", "Sukolilo", 4);
        graph.addEdge("Mulyorejo", "Sukolilo", 2);
        graph.addEdge("Mulyorejo", "Kampus ITS", 3);
        graph.addEdge("Sukolilo", "Kampus ITS", 2);
        return graph;
    }
}
