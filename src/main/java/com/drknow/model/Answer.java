package com.drknow.model;

import java.util.Set;

public class Answer implements Comparable<Answer> {

	private String answer;
	private Set<Keyword> keywords;
	private int matchScore;

	public Answer(String answer) {
		super();
		this.answer = answer;
	}

	public Answer(String answer, Set<Keyword> keywords) {
		super();
		this.answer = answer;
		this.keywords = keywords;
	}

	public Answer(String answer, Set<Keyword> keywords, int matchScore) {
		super();
		this.answer = answer;
		this.keywords = keywords;
		this.matchScore = matchScore;
	}

	@Override
	public int compareTo(Answer o) {
		if (this.matchScore < o.matchScore)
			return 1;
		else if (this.matchScore > o.matchScore)
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "AnswerDTO [answer=" + answer + ", keywords=" + keywords + ", matchScore=" + matchScore + "]";
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public int getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(int matchScore) {
		this.matchScore = matchScore;
	}
}
