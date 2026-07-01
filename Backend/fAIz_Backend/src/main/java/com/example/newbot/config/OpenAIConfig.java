package com.example.newbot.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAIConfig {


    @Bean
    public ChatClient chatClient(
            OpenAiChatModel chatModel
    ){

        return ChatClient
                .builder(chatModel)
                .build();

    }

}
