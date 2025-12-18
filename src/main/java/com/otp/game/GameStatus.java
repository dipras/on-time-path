package com.otp.game;
public enum GameStatus {
    PLAYING("Sedang Bermain"),
    WON("Menang! Tepat Waktu!"),
    LOST("Kalah! Waktu Habis!"),
    NOT_STARTED("Belum Dimulai");
    private final String description;
    GameStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
