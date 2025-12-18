package com.otp.event;
import com.otp.graph.Edge;
import java.util.Random;
public class EventManager {
    private final Random random;
    private static final int PROB_NORMAL = 35;       
    private static final int PROB_MACET = 25;        
    private static final int PROB_LANCAR = 20;       
    private static final int PROB_KECELAKAAN = 8;    
    private static final int PROB_PERBAIKAN = 5;     
    private static final int PROB_HUJAN = 5;         
    public EventManager() {
        this.random = new Random();
    }
    public EventManager(long seed) {
        this.random = new Random(seed);
    }
    public GameEvent generateEvent(Edge edge) {
        int roll = random.nextInt(100);
        int cumulative = 0;
        cumulative += PROB_NORMAL;
        if (roll < cumulative) {
            return new GameEvent(EventType.NORMAL, edge, 0);
        }
        cumulative += PROB_MACET;
        if (roll < cumulative) {
            int extraTime = 2 + random.nextInt(3);
            return new GameEvent(EventType.MACET, edge, extraTime);
        }
        cumulative += PROB_LANCAR;
        if (roll < cumulative) {
            int savedTime = 1 + random.nextInt(2);
            return new GameEvent(EventType.LANCAR, edge, -savedTime);
        }
        cumulative += PROB_KECELAKAAN;
        if (roll < cumulative) {
            int extraTime = 3 + random.nextInt(3);
            return new GameEvent(EventType.KECELAKAAN, edge, extraTime);
        }
        cumulative += PROB_PERBAIKAN;
        if (roll < cumulative) {
            int extraTime = 2 + random.nextInt(2);
            return new GameEvent(EventType.PERBAIKAN_JALAN, edge, extraTime);
        }
        cumulative += PROB_HUJAN;
        if (roll < cumulative) {
            int extraTime = 1 + random.nextInt(3);  
            return new GameEvent(EventType.HUJAN, edge, extraTime);
        }
        int savedTime = 2 + random.nextInt(2);
        return new GameEvent(EventType.JALAN_TIKUS, edge, -savedTime);
    }
    public static boolean isNegativeEvent(GameEvent event) {
        return event.getTimeModifier() > 0;
    }
    public static boolean isPositiveEvent(GameEvent event) {
        return event.getTimeModifier() < 0;
    }
}
