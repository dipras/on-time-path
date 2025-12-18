package com.otp.game;
import com.otp.graph.Node;
import com.otp.graph.Edge;
import com.otp.graph.Dijkstra;
import com.otp.graph.Graph;
import com.otp.event.EventManager;
import com.otp.event.GameEvent;
import java.util.List;
public class GameEngine {
    private final Graph graph;
    private final EventManager eventManager;
    private GameState gameState;
    private static final int DEFAULT_TIME = 15;   
    private static final String START_LOCATION = "Rumah (Wonokromo)";
    private static final String DESTINATION = "Kampus ITS";
    public GameEngine() {
        this.graph = Graph.createSurabayaMap();
        this.eventManager = new EventManager();
    }
    public void startNewGame() {
        Node start = graph.getNode(START_LOCATION);
        Node destination = graph.getNode(DESTINATION);
        this.gameState = new GameState(start, destination, DEFAULT_TIME);
    }
    public void startNewGame(int customTime) {
        Node start = graph.getNode(START_LOCATION);
        Node destination = graph.getNode(DESTINATION);
        this.gameState = new GameState(start, destination, customTime);
    }
    public GameEvent movePlayer(Node nextNode) {
        Node current = gameState.getCurrentPosition();
        Edge edge = current.getEdgeTo(nextNode);
        if (edge == null) {
            return null;  
        }
        GameEvent event = eventManager.generateEvent(edge);
        gameState.moveTo(nextNode, edge.getWeight(), event);
        return event;
    }
    public List<Node> getAvailableMoves() {
        return gameState.getCurrentPosition().getNeighbors();
    }
    public Edge getEdgeTo(Node destination) {
        return gameState.getCurrentPosition().getEdgeTo(destination);
    }
    public Dijkstra.DijkstraResult calculateOptimalPath() {
        return Dijkstra.findShortestPaths(graph, gameState.getCurrentPosition());
    }
    public List<Node> getOptimalPathToDestination() {
        return Dijkstra.findShortestPath(graph, gameState.getCurrentPosition(), gameState.getDestination());
    }
    public int getMinimumTimeToDestination() {
        return Dijkstra.getShortestDistance(graph, gameState.getCurrentPosition(), gameState.getDestination());
    }
    public Node getRecommendedNextMove() {
        List<Node> optimalPath = getOptimalPathToDestination();
        if (optimalPath.size() > 1) {
            return optimalPath.get(1);  
        }
        return null;
    }
    public String compareWithOptimalPath() {
        StringBuilder sb = new StringBuilder();
        Dijkstra.DijkstraResult fromStart = Dijkstra.findShortestPaths(
            graph, gameState.getStartPosition()
        );
        int optimalTime = fromStart.getDistance(gameState.getDestination());
        int playerTime = gameState.getTotalTravelTime();
        sb.append("\n[STATS] PERBANDINGAN JALUR\n");
        sb.append("======================================\n");
        sb.append("Jalur Optimal (Dijkstra):\n");
        sb.append("  " + fromStart.getPathString(gameState.getDestination()) + "\n");
        sb.append("  Waktu: " + optimalTime + " menit\n\n");
        sb.append("Jalur Kamu:\n");
        sb.append("  " + gameState.getPathTakenString() + "\n");
        sb.append("  Waktu: " + playerTime + " menit\n\n");
        int difference = playerTime - optimalTime;
        if (difference > 0) {
            sb.append("[+] Kamu membutuhkan " + difference + " menit lebih lama dari jalur optimal\n");
        } else if (difference < 0) {
            sb.append("[*] Luar biasa! Kamu " + Math.abs(difference) + " menit lebih cepat dari jalur optimal!\n");
            sb.append("    (Mungkin berkat event jalan lancar/jalan tikus)\n");
        } else {
            sb.append("[OK] Kamu mengambil jalur optimal!\n");
        }
        return sb.toString();
    }
    public Graph getGraph() {
        return graph;
    }
    public GameState getGameState() {
        return gameState;
    }
    public boolean isGameOver() {
        return !gameState.isPlaying();
    }
    public boolean hasPlayerWon() {
        return gameState.hasWon();
    }
}
