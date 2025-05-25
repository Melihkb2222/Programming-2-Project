package com.example.cardmatchinggame2;

import java.io.*;

public class AuthManager {
    private static final String FILE = "users.txt";

    public static boolean login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Login failed: users file not found.");
        } catch (IOException e) {
            System.err.println("Login failed due to read error: " + e.getMessage());
        }
        return false;
    }

    public static boolean register(String username, String password) {
        if (userExists(username)) return false;
        try (FileWriter writer = new FileWriter(FILE, true)) {
            writer.write(username + ":" + password + "\n");
            return true;
        } catch (IOException e) {
            System.err.println("Registration failed: " + e.getMessage());
        }
        return false;
    }

    private static boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ":")) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("User check failed: users file not found.");
        } catch (IOException e) {
            System.err.println("User check failed due to read error: " + e.getMessage());
        }
        return false;
    }
}
