package com.lovememoir.server.api.service.diaryanalysis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class OpenAiApiService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";
//    private static final String API_KEY = "sk-lovelog-eimjvAugdrS2eYBNN8fuT3BlbkFJ2GSk8Hu1inKp06B973Ja";
//    private static final String API_KEY = "sk-proj-GnntZyDaaoJcj0bhfJqTT3BlbkFJF1Cnc3iQcYgIsWMXZQsw";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenAiApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String callGpt4Api(String prompt) throws Exception {
        ObjectNode requestBody = objectMapper.createObjectNode();

        ArrayNode messages = requestBody.putArray("messages");
        ObjectNode systemMessage = messages.addObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "단답으로 대답해");

        ObjectNode userMessage = messages.addObject();
        userMessage.put("role", "user");
        userMessage.put("content", "지피티야 안녕?");
//        requestBody.put("content", prompt);
        requestBody.put("model", "gpt-3.5-turbo-16k");

        String requestBodyStr = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(API_URL))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + API_KEY)
            .POST(HttpRequest.BodyPublishers.ofString(requestBodyStr))
            .build();
        log.info("req 확인" + request.headers().toString());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("resp 확인" + response.toString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("GPT 실패" + response.toString());
        }

        ObjectNode responseJson = objectMapper.readValue(response.body(), ObjectNode.class);
        return responseJson.get("choices").get(0).get("text").asText(); // ??
    }
}
