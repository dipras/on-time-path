package com.otp.graph;
import java.util.ArrayList;
import java.util.List;
public class Node {
    private final String name;            
    private final List<Edge> edges;       
    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }
    public void addEdge(Node destination, int weight) {
        edges.add(new Edge(this, destination, weight));
    }
    public List<Edge> getEdges() {
        return edges;
    }
    public Edge getEdgeTo(Node destination) {
        for (Edge edge : edges) {
            if (edge.getDestination().equals(destination)) {
                return edge;
            }
        }
        return null;
    }
    public List<Node> getNeighbors() {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            neighbors.add(edge.getDestination());
        }
        return neighbors;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return name.equals(node.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
