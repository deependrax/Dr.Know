package com.drknow.model;

import java.util.Set;

public class QNA {

	private String question;
	private String answer;
	private Set<String> keywords;

	public QNA(String question, String answer, Set<String> keywords) {
		super();
		this.question = question;
		this.answer = answer;
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "QNA [question=" + question + ", answer=" + answer + ", keywords=" + keywords + "]";
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
}
