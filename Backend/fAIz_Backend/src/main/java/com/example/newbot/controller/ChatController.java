package com.example.newbot.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.newbot.dto.ChatRequest;
import com.example.newbot.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class ChatController {


private final ChatService chatService;


@PostMapping
public String chat(
@RequestBody ChatRequest request
){

return chatService.ask(
request.question()
);

}

}

