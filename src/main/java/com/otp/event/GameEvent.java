package com.otp.event;
import com.otp.graph.Edge;
public class GameEvent {
    private final EventType type;
    private final Edge affectedEdge;
    private final int timeModifier;   
    public GameEvent(EventType type, Edge affectedEdge, int timeModifier) {
        this.type = type;
        this.affectedEdge = affectedEdge;
        this.timeModifier = timeModifier;
    }
    public void apply() {
        if (affectedEdge != null) {
            affectedEdge.addWeight(timeModifier);
        }
    }
    public EventType getType() {
        return type;
    }
    public Edge getAffectedEdge() {
        return affectedEdge;
    }
    public int getTimeModifier() {
        return timeModifier;
    }
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getTitle()).append("\n");
        sb.append(type.getDescription()).append("\n");
        if (timeModifier > 0) {
            sb.append("[TIME] Waktu bertambah ").append(timeModifier).append(" menit");
        } else if (timeModifier < 0) {
            sb.append("[TIME] Waktu berkurang ").append(Math.abs(timeModifier)).append(" menit");
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return type.getTitle() + " (waktu " + (timeModifier >= 0 ? "+" : "") + timeModifier + " menit)";
    }
}
