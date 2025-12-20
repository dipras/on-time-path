package com.otp;
import com.otp.game.GameStatus;
import com.otp.game.GameEngine;
import com.otp.game.GameState;
import com.otp.event.GameEvent;
import com.otp.event.EventManager;
import com.otp.graph.Edge;
import com.otp.graph.Dijkstra;
import com.otp.graph.Graph;
import com.otp.graph.Node;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
public class AppTest {
    @Test
    public void testGraphCreation() {
        Graph graph = Graph.createSurabayaMap();
        assertNotNull(graph.getNode("Rumah (Wonokromo)"));
        assertNotNull(graph.getNode("Kampus ITS"));
        assertNotNull(graph.getNode("Darmo"));
        assertNotNull(graph.getNode("Gubeng"));
        Node rumah = graph.getNode("Rumah (Wonokromo)");
        assertTrue(rumah.getNeighbors().size() > 0);
    }
    @Test
    public void testDijkstra() {
        Graph graph = Graph.createSurabayaMap();
        Node start = graph.getNode("Rumah (Wonokromo)");
        Node end = graph.getNode("Kampus ITS");
        List<Node> path = Dijkstra.findShortestPath(graph, start, end);
        assertFalse(path.isEmpty());
        assertEquals(start, path.get(0));
        assertEquals(end, path.get(path.size() - 1));
        int distance = Dijkstra.getShortestDistance(graph, start, end);
        assertTrue(distance > 0);
        assertTrue(distance < 100);  
    }
    @Test
    public void testGameEngine() {
        GameEngine engine = new GameEngine();
        engine.startNewGame();
        GameState state = engine.getGameState();
        assertEquals("Rumah (Wonokromo)", state.getCurrentPosition().getName());
        assertEquals("Kampus ITS", state.getDestination().getName());
        assertEquals(15, state.getTimeRemaining());
        assertEquals(GameStatus.PLAYING, state.getStatus());
        assertTrue(engine.getAvailableMoves().size() > 0);
    }
    @Test
    public void testEventManager() {
        EventManager eventManager = new EventManager(12345);  
        Graph graph = Graph.createSurabayaMap();
        Node rumah = graph.getNode("Rumah (Wonokromo)");
        Edge edge = rumah.getEdges().get(0);
        for (int i = 0; i < 10; i++) {
            GameEvent event = eventManager.generateEvent(edge);
            assertNotNull(event);
            assertNotNull(event.getType());
        }
    }
    @Test
    public void testNodeAndEdge() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        nodeA.addEdge(nodeB, 5);
        assertEquals(1, nodeA.getEdges().size());
        assertEquals(5, nodeA.getEdges().get(0).getWeight());
        assertEquals(nodeB, nodeA.getEdges().get(0).getDestination());
        Edge edge = nodeA.getEdges().get(0);
        edge.addWeight(3);
        assertEquals(8, edge.getWeight());
        edge.reduceWeight(2);
        assertEquals(6, edge.getWeight());
    }
    @Test
    public void testGameStateTransitions() {
        GameEngine engine = new GameEngine();
        engine.startNewGame(50);  
        List<Node> moves = engine.getAvailableMoves();
        if (!moves.isEmpty()) {
            Node nextNode = moves.get(0);
            engine.movePlayer(nextNode);
            assertEquals(nextNode, engine.getGameState().getCurrentPosition());
            assertTrue(engine.getGameState().getTimeRemaining() < 50);
        }
    }
}
