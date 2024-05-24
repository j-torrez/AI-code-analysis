package com.chatgpt.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data  // Lombok annotation to generate getters, setters, and other utility methods
@AllArgsConstructor  // Lombok annotation to generate a constructor with all arguments
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
public class ChatGptResponse {

    private List<Choice> choices;  // A list of choices returned by the ChatGPT API

    @Data  // Lombok annotation to generate getters, setters, and other utility methods for the nested class
    @AllArgsConstructor  // Lombok annotation to generate a constructor with all arguments for the nested class
    @NoArgsConstructor  // Lombok annotation to generate a no-argument constructor for the nested class
    public static class Choice {

        private int index;  // The index of the choice in the list of choices
        private Message message;  // The message associated with this choice

    }

}


