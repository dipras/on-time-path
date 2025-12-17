package com.otp.graph;
public class Edge {
    private final Node source;            
    private final Node destination;       
    private int weight;                   
    private final int originalWeight;     
    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.originalWeight = weight;
    }
    public Node getSource() {
        return source;
    }
    public Node getDestination() {
        return destination;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = Math.max(1, weight);  
    }
    public void addWeight(int amount) {
        this.weight = Math.max(1, this.weight + amount);
    }
    public void reduceWeight(int amount) {
        this.weight = Math.max(1, this.weight - amount);
    }
    public void resetWeight() {
        this.weight = originalWeight;
    }
    public int getOriginalWeight() {
        return originalWeight;
    }
    @Override
    public String toString() {
        return source.getName() + " -> " + destination.getName() + " (" + weight + " menit)";
    }
}
