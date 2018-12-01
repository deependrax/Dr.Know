package com.drknow.model;

import java.util.List;

public class Answer {

	private String answer;
	private List<Keyword> keywords;

	public Answer(String answer, List<Keyword> keywords) {
		super();
		this.answer = answer;
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "Answer [answer=" + answer + ", keywords=" + keywords + "]";
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
}
