package com.example.openaichatbot.service;

import com.example.openaichatbot.dto.ChatCompletion;
import com.example.openaichatbot.dto.RequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:openapi.properties")
public class MainService {
    @Value("${api.key}")
    String api;
    public ChatCompletion getResponse(String text) {
        WebClient webClient = WebClient.builder().build();
        String url = "https://api.openai.com/v1/chat/completions";


        RequestDto.Message[] requestDtos = new RequestDto.Message[2];
        requestDtos[0] = new RequestDto.Message("system", "\"You are a friendly and empathetic companion who speaks casually and informally, like a close friend. Your job is to provide a listening ear, offer comforting conversation, and help users feel less alone.\"");
        requestDtos[1] = new RequestDto.Message("user", text);
        RequestDto dto = new RequestDto("gpt-3.5-turbo", requestDtos);
        // POST 요청
        ResponseEntity<ChatCompletion> responseBody = webClient.post()
                .uri(url)	// url 정의
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + api)
                .bodyValue(dto)	// requestBody 정의
                .retrieve()	// 응답 정의 시작
                .toEntity(ChatCompletion.class)	// 응답 데이터 정의
                .block();	// 동기식 처리

        return responseBody.getBody();
    }
}
