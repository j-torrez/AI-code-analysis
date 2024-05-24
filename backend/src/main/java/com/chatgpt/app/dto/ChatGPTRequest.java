package com.chatgpt.app.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data  // Lombok annotation to generate getters, setters, and other utility methods
public class ChatGPTRequest {

    private String model;  // The model name to be used for the OpenAI API request
    private List<Message> messages;  // A list of messages to be sent in the request

    // Constructor to initialize the ChatGPTRequest object with a model and prompt
    public ChatGPTRequest(String model, String prompt) {
        this.model = model;  // Sets the model field with the provided model name
        this.messages = new ArrayList<>();  // Initializes the messages list
        this.messages.add(new Message("user", prompt));  // Adds a new user message with the provided prompt
    }
}


