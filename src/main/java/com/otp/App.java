package com.otp;
import com.otp.ui.TUIManager;
public class App {
    public static void main(String[] args) {
        System.out.println("ON-TIME PATH - Sampai Kampus Tepat Waktu!");
        System.out.println("===========================================");
        System.out.println("Memuat game...\n");
        TUIManager tui = new TUIManager();
        tui.run();
        System.out.println("\nTerima kasih sudah bermain!");
    }
}
