package com.drknow.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchQuery {
	
    @NotBlank(message = "please ask something!")
    private String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}