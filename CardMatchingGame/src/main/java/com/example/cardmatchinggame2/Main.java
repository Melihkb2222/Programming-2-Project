package com.example.cardmatchinggame2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage window;
    private String currentUser;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Card Matching Game");

        showLoginScene();
        window.show();
    }

    private void showLoginScene() {
        VBox loginLayout = new VBox(10);
        loginLayout.setStyle("-fx-padding: 20");
        TextField usernameField = new TextField();
        usernameField.setPromptText("User Name");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        Label loginMessage = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (AuthManager.login(username, password)) {
                currentUser = username;
                showGameScene();
            } else {
                loginMessage.setText("Login unsuccessful. Please check your name and password or register for an account.");
            }
        });

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (AuthManager.register(username, password)) {
                loginMessage.setText("Registration successful. You can log in.");
            } else {
                loginMessage.setText("Username already exists.");
            }
        });

        loginLayout.getChildren().addAll(new Label("User Login"), usernameField, passwordField, loginButton, registerButton, loginMessage);
        Scene loginScene = new Scene(loginLayout, 300, 250);

        window.setScene(loginScene);
    }

    private void showGameScene() {
        GameController game = new GameController(currentUser);

        VBox layout = new VBox(10);
        layout.getChildren().add(game.createContent());

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            currentUser = null;
            showLoginScene();
        });

        layout.getChildren().add(logoutButton);

        Scene gameScene = new Scene(layout, 450, 350);
        window.setScene(gameScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
