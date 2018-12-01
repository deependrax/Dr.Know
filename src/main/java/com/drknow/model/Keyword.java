package com.drknow.model;

public class Keyword {

	private String keyword;
	private boolean match;

	public Keyword(String keyword, boolean match) {
		super();
		this.keyword = keyword;
		this.match = match;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isMatch() {
		return match;
	}

	public void setMatch(boolean match) {
		this.match = match;
	}
}
