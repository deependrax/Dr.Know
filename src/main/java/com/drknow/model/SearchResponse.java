package com.drknow.model;

import java.util.List;

public class SearchResponse {

    private String msg;
    private List<Answer> answers;
	
    public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
