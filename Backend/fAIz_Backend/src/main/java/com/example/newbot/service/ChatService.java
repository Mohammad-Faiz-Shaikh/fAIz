package com.example.newbot.service;

import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.example.newbot.model.Newbot;
import com.example.newbot.repository.NewbotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {


private final ChatClient chatClient;
private final VectorStore vectorStore;
private final NewbotRepository repo;


public String ask(String question){


var docs = vectorStore.similaritySearch(question);


String context =docs.stream().map(Document::getText).collect(Collectors.joining("\n"));



String ans= chatClient.prompt().user("""
Answer only from this information:%sQuestion:%s""".formatted(context,question)).call().content();

repo.save(new Newbot(question,ans));

return ans;

}


}	
