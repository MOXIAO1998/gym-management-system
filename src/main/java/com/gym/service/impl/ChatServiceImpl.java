package com.gym.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class ChatServiceImpl implements ChatService {

    @Value("${deepseek.api.key}")
    private String apiKeyFromConfig;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.api.model}")
    private String defaultModel;

    @Override
    public String queryChat(String content, String model) {
        String userContent = content == null ? "" : content.trim();

        if (userContent.isEmpty()) {
            return "I have not received any valid content; Please re-enter a valid content";
        }

        String resolvedModel = (model == null || model.trim().isEmpty()) ? defaultModel : model.trim();

        String apiKey = apiKeyFromConfig;

        if(apiKey == null || apiKey.trim().isEmpty()){
            apiKey = System.getenv("DEEPSEEK_API_KEY");
        }

        if(apiKey == null || apiKey.trim().isEmpty()){
            throw new IllegalStateException("Deepseek API key is not set");
        }

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            HashMap<Object, Object> payload = new HashMap<>();
            payload.put("model", resolvedModel);
            payload.put("stream", false);

            ArrayList<Map<String, String>> messages = new ArrayList<>();
            messages.add(new HashMap<>(){{
                put("role", "system");
                put("content", "You are a workout and nutrition assistant. " +
                        "Your answer should be specific, executable, and doable. " +
                        "Also, you need to give matters needing attention for the user." +
                        "If the user message includes classes listed in 'Supplementary Information of the System', " +
                        "please briefly summary what classes the user have," +
                        "then answer the subsequent questions based on the summary.");

            }});

            messages.add(new HashMap<>(){{
                put("role","user");
                put("content", userContent);
            }});

            payload.put("messages", messages);

            String requestBody = objectMapper.writeValueAsString(payload);

            HttpURLConnection connection = (HttpURLConnection)new URL(apiUrl).openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            OutputStream os = connection.getOutputStream();
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            int statusCode = connection.getResponseCode();
            InputStream is = statusCode >= 200 && statusCode < 300 ? connection.getInputStream() : connection.getErrorStream();

            String responseBody  = readAll(is);

            if (statusCode < 200 || statusCode >= 300) {
                throw new RuntimeException("DeepSeek API request failed with status code " + statusCode +
                        ", body: " + responseBody);
            }

            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode choices = root.path("choices");
            if(choices.isArray() && !choices.isEmpty()){
                JsonNode message = choices.get(0).path("message");
                String reply = message.path("content").asText(null);
                if(reply != null && !reply.trim().isEmpty()){
                    return reply;
                }
            }

            throw new RuntimeException("DeepSeek return cannot be parsed: choices[0].message.content is empty");


        }catch(IOException e){
            throw new RuntimeException("DeepSeek API invoke failed", e);
        }

    }

    private String readAll(InputStream is) throws IOException {
        if (is == null) return "";

        StringBuilder sb = new StringBuilder();
        BufferedReader bf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        while( (line = bf.readLine()) != null){
            sb.append(line);
        }
        bf.close();
        return sb.toString();

    }
}
