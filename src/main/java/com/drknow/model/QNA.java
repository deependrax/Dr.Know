package com.drknow.model;

import java.util.Set;

public class QNA {

	private String question;
	private String answer;
	private Set<String> tags;

	public QNA(String question, String answer, Set<String> tags) {
		super();
		this.question = question;
		this.answer = answer;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "QNA [question=" + question + ", answer=" + answer + ", tags=" + tags + "]";
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

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
}
