package com.drknow.services;

import org.springframework.stereotype.Service;

import com.drknow.model.Answer;
import com.drknow.model.Keyword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchService {
	private static Answer noAnswer = new Answer("I don\'t know this.", null);
	
	public List<Answer> getAnswers(String question) {
		List<Answer> answers = new ArrayList<Answer>();
		answers.add(noAnswer);
		return answers;
	}
}
