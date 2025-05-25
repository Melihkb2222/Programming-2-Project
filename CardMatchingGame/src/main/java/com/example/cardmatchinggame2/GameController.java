package com.example.cardmatchinggame2;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GameController {
    private static Card firstCard = null;
    private static Card secondCard = null;
    private static boolean canClick = true;
    private static int matches = 0;
    private final String username;

    private final Label timerLabel = new Label("Time: 0");
    private int timeSeconds = 0;
    private Timer timer;

    public GameController(String username) {
        this.username = username;
        startTimer();
    }

    public Pane createContent() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setStyle("-fx-padding: 10;");

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        List<String> values = Arrays.asList("A", "A", "B", "B", "C", "C", "D", "D");
        Collections.shuffle(values);

        int index = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                Card card = new Card(values.get(index++), this);
                grid.add(card, col, row);
            }
        }

        layout.getChildren().addAll(new Label("Player: " + username), timerLabel, grid);
        return layout;
    }

    public void cardClicked(Card card) {
        if (!canClick || card.isMatched()) return;

        if (firstCard == null) {
            firstCard = card;
            return;
        }

        if (secondCard == null && card != firstCard) {
            secondCard = card;
            checkMatch();
        }
    }

    private void checkMatch() {
        if (firstCard.getValue().equals(secondCard.getValue())) {
            firstCard.setMatched(true);
            secondCard.setMatched(true);
            matches++;

            reset();

            if (matches == 4) {
                stopTimer();
                saveScore(username, timeSeconds);

                Platform.runLater(() -> {
                    try {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText(null);
                        alert.setContentText("🎉 Congratulations, you won! Your time: " + timeSeconds + " seconds");
                        alert.showAndWait();
                    } catch (Exception e) {
                        System.err.println("Error showing game over alert: " + e.getMessage());
                    }
                });
            }

        } else {
            canClick = false;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        firstCard.flip();
                        secondCard.flip();
                        reset();
                    });
                }
            }, 1000);
        }
    }

    private void saveScore(String username, int timeSeconds) {
        try (FileWriter writer = new FileWriter("scores.txt", true)) {
            writer.write(username + "," + timeSeconds + "\n");
        } catch (IOException e) {
            System.err.println("Failed to save score: " + e.getMessage());
        }
    }

    private void reset() {
        firstCard = null;
        secondCard = null;
        canClick = true;
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    timeSeconds++;
                    timerLabel.setText("Time: " + timeSeconds + " s");
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
