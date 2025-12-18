package com.otp.event;
public enum EventType {
    MACET("[MACET] Jalan Macet!", "Terjadi kemacetan di jalur ini"),
    LANCAR("[LANCAR] Jalan Lancar!", "Jalanan sedang sepi, perjalanan lebih cepat"),
    NORMAL("[INFO] Normal", "Perjalanan berjalan normal"),
    KECELAKAAN("[!] Ada Kecelakaan!", "Kecelakaan menyebabkan penundaan"),
    PERBAIKAN_JALAN("[PERBAIKAN] Perbaikan Jalan", "Jalanan sedang diperbaiki"),
    HUJAN("[HUJAN] Hujan Deras", "Hujan membuat perjalanan lebih lambat"),
    JALAN_TIKUS("[SHORTCUT] Jalan Tikus!", "Menemukan jalan pintas");
    private final String title;
    private final String description;
    EventType(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
}
