package com.drknow.services;

import org.springframework.stereotype.Service;

import com.drknow.model.Answer;
import com.drknow.model.Keyword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchService {

	public List<Answer> getAnswers(String question) {
		List<Answer> answers = new ArrayList<Answer>();

		Answer answer = new Answer("You are a good question!", new ArrayList<Keyword>(
				Arrays.asList(new Keyword("good question", true), new Keyword("hurt me", false))));
		Answer answer2 = new Answer("Your question hurt me.", new ArrayList<Keyword>(
				Arrays.asList(new Keyword("good question", false), new Keyword("hurt me", true))));

		answers.add(answer);
		answers.add(answer2);

		return answers;
	}
}
