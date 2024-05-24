package com.chatgpt.app.controller;

import com.chatgpt.app.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController  // Marks this class as a RESTful web service controller
@RequestMapping("/api")  // Base URL for all API endpoints in this controller
public class CustomBotController {

    @Value("${openai.model}")  // Injects the OpenAI model from application properties
    private String model;

    @Value(("${openai.api.url}"))  // Injects the OpenAI API URL from application properties
    private String apiURL;

    @Autowired  // Injects an instance of RestTemplate
    private RestTemplate template;

    @GetMapping("/generate-response")  // Maps GET requests to /api/generate-response
    public Message chat(@RequestParam("codeSnippet") String codeSnippet, @RequestParam("language") String language) {
        // Generates a prompt from the code snippet and language
        String prompt = generatePromptFromCodeSnippet(codeSnippet, language);
        // Creates a request object for ChatGPT
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        // Sends the request to the OpenAI API and receives a response
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        // Returns the first message from the response
        return chatGptResponse.getChoices().get(0).getMessage();
    }

    // Generates a prompt from the code snippet and specified language
    private String generatePromptFromCodeSnippet(String codeSnippet, String language) {
        return "Convert the given code snippet to " + language + ":\n\n" + codeSnippet;
    }

    @GetMapping("/debug-code")  // Maps GET requests to /api/debug-code
    public Message debugCode(@RequestParam("codeSnippet") String codeSnippet) throws UnsupportedEncodingException {
        // Encodes the code snippet for safe transmission
        String encodedCodeSnippet;
        encodedCodeSnippet = URLEncoder.encode(codeSnippet, StandardCharsets.UTF_8.toString());

        // Generates a prompt for debugging the code
        String prompt = generatePromptForCodeDebugging(codeSnippet);
        // Creates a request object for ChatGPT
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        // Sends the request to the OpenAI API and receives a response
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        // Returns the first message from the response
        return chatGptResponse.getChoices().get(0).getMessage();
    }

    // Generates a prompt for debugging the given code snippet
    private String generatePromptForCodeDebugging(String encodedCodeSnippet) throws UnsupportedEncodingException {
        // Decodes the encoded code snippet
        String decodedCodeSnippet;
        decodedCodeSnippet = URLDecoder.decode(encodedCodeSnippet, StandardCharsets.UTF_8.toString());

        // Builds a detailed prompt for code debugging
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Code debugging prompt:\n\n");
        promptBuilder.append("Please review the following code snippet for debugging:\n\n");
        promptBuilder.append(decodedCodeSnippet);
        promptBuilder.append("\n\n");
        promptBuilder.append("Identify and address the following potential issues in the code:\n\n");
        promptBuilder.append("1. Check for any syntax errors or missing semicolons.\n\n");
        promptBuilder.append("2. Examine the logic and algorithm for correctness and accuracy.\n\n");
        promptBuilder.append("3. Look for any runtime errors or exceptions that may occur during execution.\n\n");
        promptBuilder.append("4. Verify the correctness of loop conditions and array bounds.\n\n");
        promptBuilder.append("5. Review variable scopes and data types to ensure proper usage.\n\n");
        promptBuilder.append("6. Check for any unintended side effects or unexpected behavior.\n\n");
        promptBuilder.append("7. Ensure that all necessary libraries and dependencies are imported and used correctly.\n\n");
        promptBuilder.append("8. Look for potential race conditions or synchronization issues in multi-threaded code (if applicable).\n\n");
        promptBuilder.append("\n");
        promptBuilder.append("After identifying the issues, please provide what is wrong and provide suggestions and fixes for each problem found.\n");

        return promptBuilder.toString();
    }

    @GetMapping("/code-quality-check")  // Maps GET requests to /api/code-quality-check
    public Message codeQualityCheck(@RequestParam("codeSnippet") String codeSnippet) {
        // Generates a prompt for code quality checking
        String prompt = generatePromptForCodeQualityChecking(codeSnippet);
        // Creates a request object for ChatGPT
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        // Sends the request to the OpenAI API and receives a response
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        // Returns the first message from the response
        return chatGptResponse.getChoices().get(0).getMessage();
    }

    // Generates a prompt for checking the quality of the given code snippet
    private String generatePromptForCodeQualityChecking(String codeSnippet) {
        // Builds a detailed prompt for code quality assessment
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Code quality check prompt:\n\n");
        promptBuilder.append("Please review the following code snippet for quality assessment:\n\n");
        promptBuilder.append(codeSnippet);
        promptBuilder.append("\n\n");
        promptBuilder.append("Please provide feedback on the following aspects of code quality:\n\n");
        promptBuilder.append("1. Code Consistency: Check if the code follows consistent naming conventions and coding style.\n\n");
        promptBuilder.append("2. Code Performance: Evaluate the efficiency and performance of the code. Suggest any performance optimizations if necessary.\n\n");
        promptBuilder.append("3. Code Documentation: Assess the code's readability and completeness of comments and documentation.\n\n");
        promptBuilder.append("4. Error Handling: Review the error handling mechanisms to ensure proper handling of exceptions and errors.\n\n");
        promptBuilder.append("5. Code Testability: Examine the code for testability. Are there clear separation of concerns and units for testing?\n\n");
        promptBuilder.append("6. Code Modularity: Evaluate the code's modularity and identify opportunities for better code organization.\n\n");
        promptBuilder.append("7. Code Complexity: Assess the code's complexity and suggest simplifications or refactorings as needed.\n\n");
        promptBuilder.append("8. Code Duplication: Identify and suggest removal of any duplicated code to improve maintainability.\n\n");
        promptBuilder.append("9. Code Readability: Evaluate the overall readability and clarity of the code.\n\n");
        promptBuilder.append("\n");
        promptBuilder.append("Please provide your feedback and assign a rating to each aspect mentioned above. You can use a scale from 1 to 5, where 1 is poor and 5 is excellent.\n");

        return promptBuilder.toString();
    }
}
