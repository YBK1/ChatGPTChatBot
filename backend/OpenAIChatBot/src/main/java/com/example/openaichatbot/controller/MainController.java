package com.example.openaichatbot.controller;

import com.example.openaichatbot.dto.ChatCompletion;
import com.example.openaichatbot.service.MainService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @PostMapping("/api")
    public ResponseEntity<?> getResponse(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String receivedText = (String) body.get("text");

        ChatCompletion cc  = mainService.getResponse(receivedText);
        if (cc == null) {
            return new ResponseEntity<>("Received text: " + receivedText, HttpStatus.BAD_REQUEST);
        } else {
            String ret = cc.getChoices().get(0).getMessage().getContent();
            return new ResponseEntity<>("요청 결과: " + ret, HttpStatus.OK);
        }
    }


}
