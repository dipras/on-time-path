package com.otp.game;
import com.otp.event.GameEvent;
import com.otp.graph.Node;
import java.util.ArrayList;
import java.util.List;
public class GameState {
    private Node currentPosition;         
    private final Node startPosition;     
    private final Node destination;       
    private int timeRemaining;            
    private final int initialTime;        
    private int totalTravelTime;          
    private GameStatus status;            
    private final List<String> travelLog;
    private final List<GameEvent> eventHistory;
    private final List<Node> pathTaken;
    public GameState(Node start, Node destination, int initialTime) {
        this.startPosition = start;
        this.currentPosition = start;
        this.destination = destination;
        this.initialTime = initialTime;
        this.timeRemaining = initialTime;
        this.totalTravelTime = 0;
        this.status = GameStatus.PLAYING;
        this.travelLog = new ArrayList<>();
        this.eventHistory = new ArrayList<>();
        this.pathTaken = new ArrayList<>();
        this.pathTaken.add(start);
        addLog("[START] Memulai perjalanan dari " + start.getName());
        addLog("[TARGET] Tujuan: " + destination.getName());
        addLog("[TIME] Waktu tersedia: " + initialTime + " menit");
        addLog("-----------------------------------");
    }
    public void moveTo(Node nextNode, int travelTime, GameEvent event) {
        if (event != null) {
            eventHistory.add(event);
        }
        int actualTime = travelTime + (event != null ? event.getTimeModifier() : 0);
        actualTime = Math.max(1, actualTime);  
        timeRemaining -= actualTime;
        totalTravelTime += actualTime;
        String logEntry = String.format("[>>] %s -> %s (%d menit)", 
            currentPosition.getName(), nextNode.getName(), actualTime);
        addLog(logEntry);
        if (event != null && event.getTimeModifier() != 0) {
            addLog("   " + event.toString());
        }
        currentPosition = nextNode;
        pathTaken.add(nextNode);
        updateGameStatus();
    }
    private void updateGameStatus() {
        if (currentPosition.equals(destination)) {
            if (timeRemaining >= 0) {
                status = GameStatus.WON;
                addLog("-----------------------------------");
                addLog("[WIN] SELAMAT! Kamu sampai tepat waktu!");
                addLog("[TIME] Sisa waktu: " + timeRemaining + " menit");
            } else {
                status = GameStatus.LOST;
                addLog("-----------------------------------");
                addLog("[LOSE] Kamu sampai, tapi terlambat!");
                addLog("[TIME] Terlambat: " + Math.abs(timeRemaining) + " menit");
            }
        } else if (timeRemaining <= 0) {
            status = GameStatus.LOST;
            addLog("-----------------------------------");
            addLog("[LOSE] Waktu habis! Kamu tidak sampai tepat waktu!");
        }
    }
    public void addLog(String entry) {
        travelLog.add(entry);
    }
    public Node getCurrentPosition() {
        return currentPosition;
    }
    public Node getStartPosition() {
        return startPosition;
    }
    public Node getDestination() {
        return destination;
    }
    public int getTimeRemaining() {
        return timeRemaining;
    }
    public int getInitialTime() {
        return initialTime;
    }
    public int getTotalTravelTime() {
        return totalTravelTime;
    }
    public GameStatus getStatus() {
        return status;
    }
    public List<String> getTravelLog() {
        return new ArrayList<>(travelLog);
    }
    public List<GameEvent> getEventHistory() {
        return new ArrayList<>(eventHistory);
    }
    public List<Node> getPathTaken() {
        return new ArrayList<>(pathTaken);
    }
    public String getPathTakenString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pathTaken.size(); i++) {
            sb.append(pathTaken.get(i).getName());
            if (i < pathTaken.size() - 1) {
                sb.append(" → ");
            }
        }
        return sb.toString();
    }
    public boolean isPlaying() {
        return status == GameStatus.PLAYING;
    }
    public boolean hasWon() {
        return status == GameStatus.WON;
    }
    public boolean hasLost() {
        return status == GameStatus.LOST;
    }
}
