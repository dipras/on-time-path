package com.otp.ui;
import com.otp.event.GameEvent;
import com.otp.game.GameEngine;
import com.otp.game.GameState;
import com.otp.graph.Dijkstra;
import com.otp.graph.Edge;
import com.otp.graph.Node;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
public class TUIManager {
    private Terminal terminal;
    private Screen screen;
    private MultiWindowTextGUI gui;
    private GameEngine gameEngine;
    private BasicWindow mainWindow;
    private Panel mainPanel;
    private Panel statusPanel;
    private Panel mapPanel;
    private Panel logPanel;
    private Panel actionPanel;
    private Label positionLabel;
    private Label timeLabel;
    private Label destinationLabel;
    private Label statusLabel;
    private Label optimalPathLabel;
    private TextBox logTextBox;
    public TUIManager() {
        this.gameEngine = new GameEngine();
    }
    public void initialize() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminal = terminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
    }
    public void showMainMenu() {
        BasicWindow menuWindow = new BasicWindow("ON-TIME PATH - Sampai Kampus Tepat Waktu!");
        menuWindow.setHints(Arrays.asList(Window.Hint.CENTERED));
        Panel menuPanel = new Panel();
        menuPanel.setLayoutManager(new GridLayout(1));
        menuPanel.addComponent(new Label(
            "╔═══════════════════════════════════════════╗\n" +
            "║        ON-TIME PATH GAME                  ║\n" +
            "║   Sampai Kampus Tepat Waktu di Surabaya   ║\n" +
            "╚═══════════════════════════════════════════╝"
        ));
        menuPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        menuPanel.addComponent(new Label(
            "[CERITA]\n" +
            "Kamu baru bangun dan hanya punya 15 menit untuk\n" +
            "sampai ke kampus ITS dari rumahmu di Wonokromo!\n\n" +
            "[TUJUAN]\n" +
            "Pilih jalur terbaik melewati Surabaya dan sampai\n" +
            "tepat waktu. Hati-hati dengan kemacetan!\n\n" +
            "[EVENT ACAK]\n" +
            "* Jalan Macet - waktu bertambah\n" +
            "* Jalan Lancar - waktu berkurang\n" +
            "* Kecelakaan, Hujan, dll."
        ));
        menuPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        Button startButton = new Button("[PLAY] Mulai Permainan", () -> {
            menuWindow.close();
            startGame();
        });
        Button helpButton = new Button("[HELP] Cara Bermain", () -> {
            showHelpDialog();
        });
        Button exitButton = new Button("[EXIT] Keluar", () -> {
            menuWindow.close();
        });
        menuPanel.addComponent(startButton);
        menuPanel.addComponent(helpButton);
        menuPanel.addComponent(exitButton);
        menuWindow.setComponent(menuPanel);
        gui.addWindowAndWait(menuWindow);
    }
    private void showHelpDialog() {
        MessageDialog.showMessageDialog(gui, "[HELP] Cara Bermain",
            "KONTROL:\n" +
            "* Gunakan Arrow Keys untuk memilih\n" +
            "* Tekan Enter untuk konfirmasi\n" +
            "* Tab untuk berpindah antar tombol\n\n" +
            "GAMEPLAY:\n" +
            "1. Kamu memulai dari Rumah (Wonokromo)\n" +
            "2. Pilih jalur menuju Kampus ITS\n" +
            "3. Setiap jalur memiliki waktu tempuh\n" +
            "4. Event acak dapat mengubah waktu\n" +
            "5. Sampai sebelum waktu habis = MENANG!\n\n" +
            "TIPS:\n" +
            "* Perhatikan jalur optimal yang disarankan\n" +
            "* Event bisa menguntungkan atau merugikan",
            MessageDialogButton.OK
        );
    }
    private void startGame() {
        gameEngine.startNewGame();
        showGameScreen();
    }
    private void showGameScreen() {
        mainWindow = new BasicWindow("ON-TIME PATH - Perjalanan ke Kampus");
        mainWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        mainPanel = new Panel();
        mainPanel.setLayoutManager(new GridLayout(2));
        Panel leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        leftPanel.setPreferredSize(new TerminalSize(50, 30));
        createStatusPanel(leftPanel);
        createMapPanel(leftPanel);
        Panel rightPanel = new Panel();
        rightPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        rightPanel.setPreferredSize(new TerminalSize(45, 30));
        createActionPanel(rightPanel);
        createLogPanel(rightPanel);
        mainPanel.addComponent(leftPanel);
        mainPanel.addComponent(rightPanel);
        mainWindow.setComponent(mainPanel);
        updateDisplay();
        gui.addWindowAndWait(mainWindow);
    }
    private void createStatusPanel(Panel parent) {
        statusPanel = new Panel();
        statusPanel.setLayoutManager(new GridLayout(2));
        statusPanel.addComponent(new Label("===== STATUS =====").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        statusPanel.addComponent(new Label("[>>] Posisi:"));
        positionLabel = new Label("-");
        statusPanel.addComponent(positionLabel);
        statusPanel.addComponent(new Label("[*] Tujuan:"));
        destinationLabel = new Label("-");
        statusPanel.addComponent(destinationLabel);
        statusPanel.addComponent(new Label("[T] Waktu:"));
        timeLabel = new Label("-");
        statusPanel.addComponent(timeLabel);
        statusPanel.addComponent(new Label("[S] Status:"));
        statusLabel = new Label("-");
        statusPanel.addComponent(statusLabel);
        parent.addComponent(statusPanel.withBorder(Borders.singleLine("[STATUS] Game")));
    }
    private void createMapPanel(Panel parent) {
        mapPanel = new Panel();
        mapPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        mapPanel.addComponent(new Label("[MAP] Jalur Optimal (Dijkstra):"));
        optimalPathLabel = new Label("-");
        mapPanel.addComponent(optimalPathLabel);
        parent.addComponent(mapPanel.withBorder(Borders.singleLine("[NAVIGASI]")));
    }
    private void createActionPanel(Panel parent) {
        actionPanel = new Panel();
        actionPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        actionPanel.addComponent(new Label("Pilih jalur tujuan:"));
        actionPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        parent.addComponent(actionPanel.withBorder(Borders.singleLine("[ACTION] Pilih Jalur")));
    }
    private void createLogPanel(Panel parent) {
        logPanel = new Panel();
        logPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        logTextBox = new TextBox(new TerminalSize(40, 10), TextBox.Style.MULTI_LINE);
        logTextBox.setReadOnly(true);
        logPanel.addComponent(logTextBox);
        parent.addComponent(logPanel.withBorder(Borders.singleLine("[LOG] Log Perjalanan")));
    }
    private void updateDisplay() {
        GameState state = gameEngine.getGameState();
        positionLabel.setText(state.getCurrentPosition().getName());
        destinationLabel.setText(state.getDestination().getName());
        timeLabel.setText(state.getTimeRemaining() + " menit tersisa");
        statusLabel.setText(state.getStatus().getDescription());
        if (state.getTimeRemaining() <= 3) {
            timeLabel.setForegroundColor(TextColor.ANSI.RED);
        } else if (state.getTimeRemaining() <= 7) {
            timeLabel.setForegroundColor(TextColor.ANSI.YELLOW);
        } else {
            timeLabel.setForegroundColor(TextColor.ANSI.GREEN);
        }
        updateOptimalPath();
        updateActionButtons();
        updateLog();
    }
    private void updateOptimalPath() {
        GameState state = gameEngine.getGameState();
        if (!state.isPlaying()) {
            return;
        }
        List<Node> optimalPath = gameEngine.getOptimalPathToDestination();
        int minTime = gameEngine.getMinimumTimeToDestination();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < optimalPath.size(); i++) {
            sb.append(optimalPath.get(i).getName());
            if (i < optimalPath.size() - 1) {
                sb.append(" → ");
            }
        }
        sb.append("\nWaktu minimum: ").append(minTime).append(" menit");
        Node recommended = gameEngine.getRecommendedNextMove();
        if (recommended != null) {
            sb.append("\n\n[!] Rekomendasi: ").append(recommended.getName());
        }
        optimalPathLabel.setText(sb.toString());
    }
    private void updateActionButtons() {
        GameState state = gameEngine.getGameState();
        actionPanel.removeAllComponents();
        actionPanel.addComponent(new Label("Pilih jalur tujuan:"));
        actionPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        if (!state.isPlaying()) {
            showGameOverOptions();
            return;
        }
        List<Node> moves = gameEngine.getAvailableMoves();
        Node recommended = gameEngine.getRecommendedNextMove();
        for (Node nextNode : moves) {
            Edge edge = gameEngine.getEdgeTo(nextNode);
            String buttonText = String.format("%s (%d menit)", 
                nextNode.getName(), edge.getWeight());
            if (nextNode.equals(recommended)) {
                buttonText = "[*] " + buttonText;
            }
            final Node destination = nextNode;
            Button moveButton = new Button(buttonText, () -> {
                makeMove(destination);
            });
            actionPanel.addComponent(moveButton);
        }
        actionPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        Button quitButton = new Button("[X] Menyerah", () -> {
            mainWindow.close();
            showMainMenu();
        });
        actionPanel.addComponent(quitButton);
    }
    private void showGameOverOptions() {
        GameState state = gameEngine.getGameState();
        if (state.hasWon()) {
            actionPanel.addComponent(new Label("[WIN] SELAMAT!"));
            actionPanel.addComponent(new Label("Kamu sampai tepat waktu!"));
            actionPanel.addComponent(new Label("Sisa waktu: " + state.getTimeRemaining() + " menit"));
        } else {
            actionPanel.addComponent(new Label("[LOSE] GAME OVER"));
            actionPanel.addComponent(new Label("Waktu habis!"));
        }
        actionPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        Button compareButton = new Button("[STATS] Bandingkan Jalur", () -> {
            String comparison = gameEngine.compareWithOptimalPath();
            MessageDialog.showMessageDialog(gui, "[STATS] Perbandingan Jalur", comparison, MessageDialogButton.OK);
        });
        actionPanel.addComponent(compareButton);
        Button replayButton = new Button("[REPLAY] Main Lagi", () -> {
            mainWindow.close();
            startGame();
        });
        actionPanel.addComponent(replayButton);
        Button menuButton = new Button("[HOME] Menu Utama", () -> {
            mainWindow.close();
            showMainMenu();
        });
        actionPanel.addComponent(menuButton);
    }
    private void makeMove(Node destination) {
        GameEvent event = gameEngine.movePlayer(destination);
        if (event != null && event.getTimeModifier() != 0) {
            String eventMessage = event.getFullDescription();
            MessageDialog.showMessageDialog(gui, event.getType().getTitle(), eventMessage, MessageDialogButton.OK);
        }
        updateDisplay();
        if (gameEngine.isGameOver()) {
            showGameOverDialog();
        }
    }
    private void showGameOverDialog() {
        GameState state = gameEngine.getGameState();
        String title;
        String message;
        if (state.hasWon()) {
            title = "[WIN] MENANG!";
            message = "Selamat! Kamu sampai ke kampus tepat waktu!\n" +
                      "Total waktu: " + state.getTotalTravelTime() + " menit\n" +
                      "Sisa waktu: " + state.getTimeRemaining() + " menit\n\n" +
                      "Jalur yang kamu ambil:\n" + state.getPathTakenString();
        } else {
            title = "[LOSE] KALAH!";
            message = "Waktu habis! Kamu tidak sampai tepat waktu.\n" +
                      "Total waktu: " + state.getTotalTravelTime() + " menit\n\n" +
                      "Jalur yang kamu ambil:\n" + state.getPathTakenString();
        }
        MessageDialog.showMessageDialog(gui, title, message, MessageDialogButton.OK);
    }
    private void updateLog() {
        GameState state = gameEngine.getGameState();
        StringBuilder sb = new StringBuilder();
        for (String log : state.getTravelLog()) {
            sb.append(log).append("\n");
        }
        logTextBox.setText(sb.toString());
    }
    public void shutdown() {
        try {
            if (screen != null) {
                screen.stopScreen();
            }
            if (terminal != null) {
                terminal.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            initialize();
            showMainMenu();
        } catch (IOException e) {
            System.err.println("Error menjalankan TUI: " + e.getMessage());
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }
}
