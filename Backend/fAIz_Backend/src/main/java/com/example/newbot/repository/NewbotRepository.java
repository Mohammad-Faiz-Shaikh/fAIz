package com.example.newbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.newbot.model.Newbot;

public interface NewbotRepository extends JpaRepository<Newbot, Long> {

}
