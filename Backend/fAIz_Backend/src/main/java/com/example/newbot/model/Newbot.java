package com.example.newbot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_newbot_master")
@Data

public class Newbot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String question;
	
	@Column(length = 5000)
	private String answer;
	public Newbot(String question, String answer) {
		super();
		this.question = question;
		
		this.answer = answer;
	}
	
	
	
	
}
