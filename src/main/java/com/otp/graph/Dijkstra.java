package com.otp.graph;
import java.util.*;
public class Dijkstra {
    public static class DijkstraResult {
        private final Map<Node, Integer> distances;       
        private final Map<Node, Node> previousNodes;      
        private final Node source;
        public DijkstraResult(Map<Node, Integer> distances, Map<Node, Node> previousNodes, Node source) {
            this.distances = distances;
            this.previousNodes = previousNodes;
            this.source = source;
        }
        public int getDistance(Node destination) {
            return distances.getOrDefault(destination, Integer.MAX_VALUE);
        }
        public List<Node> getPath(Node destination) {
            List<Node> path = new ArrayList<>();
            Node current = destination;
            while (current != null) {
                path.add(0, current);
                current = previousNodes.get(current);
            }
            if (path.isEmpty() || !path.get(0).equals(source)) {
                return new ArrayList<>();
            }
            return path;
        }
        public String getPathString(Node destination) {
            List<Node> path = getPath(destination);
            if (path.isEmpty()) {
                return "Tidak ada jalur";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                sb.append(path.get(i).getName());
                if (i < path.size() - 1) {
                    sb.append(" → ");
                }
            }
            return sb.toString();
        }
    }
    public static DijkstraResult findShortestPaths(Graph graph, Node source) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(
            Comparator.comparingInt(nd -> nd.distance)
        );
        for (Node node : graph.getAllNodes()) {
            distances.put(node, Integer.MAX_VALUE);
            previousNodes.put(node, null);
        }
        distances.put(source, 0);
        pq.offer(new NodeDistance(source, 0));
        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.node;
            if (visited.contains(currentNode)) {
                continue;
            }
            visited.add(currentNode);
            for (Edge edge : currentNode.getEdges()) {
                Node neighbor = edge.getDestination();
                if (visited.contains(neighbor)) {
                    continue;
                }
                int newDistance = distances.get(currentNode) + edge.getWeight();
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, currentNode);
                    pq.offer(new NodeDistance(neighbor, newDistance));
                }
            }
        }
        return new DijkstraResult(distances, previousNodes, source);
    }
    private static class NodeDistance {
        Node node;
        int distance;
        NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
    public static List<Node> findShortestPath(Graph graph, Node source, Node destination) {
        DijkstraResult result = findShortestPaths(graph, source);
        return result.getPath(destination);
    }
    public static int getShortestDistance(Graph graph, Node source, Node destination) {
        DijkstraResult result = findShortestPaths(graph, source);
        return result.getDistance(destination);
    }
}
