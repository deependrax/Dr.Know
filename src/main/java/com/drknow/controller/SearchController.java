package com.drknow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.drknow.model.SearchResponse;
import com.drknow.model.SearchQuery;
import com.drknow.model.Answer;
import com.drknow.services.SearchService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;

	@PostMapping("/api/ask-dr-know")
	public ResponseEntity<?> search(@Valid @RequestBody SearchQuery searchQuery, Errors errors) {
		SearchResponse result = new SearchResponse();
		
		// Handle invalid request errors and return error response
		if (errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(result);
		}

		List<Answer> answers = searchService.getAnswers(searchQuery.getQuestion());
		
		if (answers.isEmpty()) {
			result.setMsg("no answer found!");
		} else {
			result.setMsg("success");
		}
		result.setAnswers(answers);

		return ResponseEntity.ok(result);
	}
}
