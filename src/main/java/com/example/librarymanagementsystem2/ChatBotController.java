package com.example.librarymanagementsystem2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatBotController {
    @FXML private TextArea chatArea;
    @FXML private TextField inputField;
    @FXML private Button sendButton;

    private final String API_KEY = "AIzaSyD0rZvsuu_g-SlQEl7qn-Rlja1TY9A3CD0";
    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    @FXML
    public void initialize() {
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        inputField.setOnKeyPressed(this::handleEnterKey);
        sendButton.setOnAction(event -> handleSendMessage());
    }

    @FXML
    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleSendMessage();
        }
    }

    @FXML
    private void handleSendMessage() {
        String userMessage = inputField.getText().trim();
        if (userMessage.isEmpty()) return;
        appendMessage("You: " + userMessage);
        inputField.clear();
        String requestBody = new JSONObject()
                .put("contents", new JSONObject[]{
                        new JSONObject()
                                .put("parts", new JSONObject[]{
                                new JSONObject().put("text", userMessage)
                        })
                })
                .toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    String aiResponse = parseResponse(response.body());
                    appendMessage("Bot: " + aiResponse);
                })
                .exceptionally(e -> {
                    appendMessage("Error: Failed to get response");
                    e.printStackTrace();
                    return null;
                });
    }

    private String parseResponse(String jsonResponse) {
        try {
            JSONObject response = new JSONObject(jsonResponse);
            return response.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        } catch (Exception e) {
            return "Error parsing response";
        }
    }

    private void appendMessage(String message) {
        chatArea.appendText(message + "\n\n");
        chatArea.setScrollTop(Double.MAX_VALUE);
    }
}

