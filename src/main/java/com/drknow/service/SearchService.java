package com.drknow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.drknow.model.QNA;
import com.drknow.util.TextUtils;
import com.drknow.model.Answer;
import com.drknow.model.Keyword;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Service
public class SearchService {

	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	private static Gson GSON = new Gson();

	private static Answer NO_ANSWER = new Answer("I don't know this.", null);
	private static Map<String, List<QNA>> answersIndex;

	public List<Answer> getAnswers(String question) {
		Map<QNA, Answer> answers = new HashMap<>();
		List<String> keywords = TextUtils.getKeywords(question);
		List<String> matchedKeywords = new ArrayList<>();

		List<QNA> qnas = answersIndex.get(TextUtils.sanitize(question));
		if (qnas != null) {
			qnas.forEach(qna -> {
				answers.put(qna, new Answer(qna.getAnswer()));
			});
		}

		if (answers.size() == 0) { // Keyword search
			keywords.forEach(keyword -> {
				List<QNA> matchedQNAs = answersIndex.get(keyword);

				if (matchedQNAs != null) {
					matchedKeywords.add(keyword);

					matchedQNAs.forEach(matchedQNA -> {
						Answer ans = answers.get(matchedQNA);

						if (ans != null) {
							ans.setMatchScore(ans.getMatchScore() + 1);
						} else {
							ans = getAnswer(matchedQNA, keywords);
						}

						Keyword matchedKeyword = new Keyword(keyword, true);
						ans.getKeywords().remove(matchedKeyword);
						ans.getKeywords().add(matchedKeyword);

						answers.put(matchedQNA, ans);
					});
				}
			});
		}

		if (answers.size() == 0) // No answers found
			return new ArrayList<>(Arrays.asList(NO_ANSWER));

		// Sort answers found by score
		List<Answer> answersList = new ArrayList<Answer>(answers.values());
		Collections.sort(answersList);

		return answersList;
	}

	private Answer getAnswer(QNA qna, List<String> keywords) {
		return new Answer(qna.getAnswer(),
				keywords.stream().map(keyword -> new Keyword(keyword, false)).collect(Collectors.toSet()), 1);
	}

	/**
	 * Cleanup & Index the data
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	private void indexData() throws IOException {
		logger.info("Loading data file");
		JsonParser parser = new JsonParser();
		JsonArray data = (JsonArray) parser.parse(new FileReader("./src/main/resources/dataset.json"));

		answersIndex = new HashMap<>();
		Iterator<JsonElement> iterator = data.iterator();
		while (iterator.hasNext()) {
			JsonObject jsonObject = (JsonObject) iterator.next();

			String question = (String) jsonObject.get("question").getAsString();
			String answer = (String) jsonObject.get("answer").getAsString();
			JsonArray keywordsArr = (JsonArray) jsonObject.get("tags");

			Set<String> keywords = new HashSet<String>();
			keywords.addAll(TextUtils.getKeywords(question));
			keywords.addAll(TextUtils.getKeywords(answer));

			Iterator<JsonElement> keywordIterator = keywordsArr.iterator();
			while (keywordIterator.hasNext()) {
				String keyword = keywordIterator.next().getAsString().toLowerCase();

				if (TextUtils.isValidKeyword(keyword))
					keywords.add(keyword);
			}

			QNA qna = new QNA(question, answer, keywords);

			// Index answer for the question exact match.
			indexAnswer(TextUtils.sanitize(question), qna);

			logger.info(GSON.toJson(keywords));

			// Add index for each unique keyword for the question
			Iterator<String> i = keywords.iterator();
			while (i.hasNext()) {
				String keyword = i.next();
				indexAnswer(keyword, qna);
			}
		}
	}

	private void indexAnswer(String indexKey, QNA qna) {
		List<QNA> indexedQNA = answersIndex.get(indexKey);
		if (indexedQNA != null) {
			indexedQNA.add(qna);
		} else {
			answersIndex.put(indexKey, new ArrayList<QNA>(Arrays.asList(qna)));
		}
	}
}