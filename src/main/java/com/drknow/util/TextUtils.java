package com.drknow.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextUtils {
	private static final Logger logger = LoggerFactory.getLogger(TextUtils.class);
	private static Set<String> stopWords;
	
	static {
		logger.info("Loading stop words");
		try {
			stopWords = new HashSet<String>(Arrays.asList(
					FileUtils.readFileToString(new File("./src/main/resources/english-stop-words.dat")).split(",")));
		} catch (IOException e) {
			logger.error("Some error loading stop words", e);
			stopWords = new HashSet<>();
		}
	}

	public static String sanitize(String text) {
		return text.toLowerCase().replaceAll("\\\\t|\\\\n", " ").replaceAll("[^a-zA-Z0-9.- ]", " ").trim();
	}

	public static List<String> getKeywords(String text) {
		if (text == null || text.length() == 0)
			return new ArrayList<String>();

		return filterStopwords(Arrays.asList(text.split(" ")));
	}

	public static List<String> filterStopwords(Collection<String> keywords) {
		return keywords.stream().map(keyword -> sanitize(keyword)).filter(keyword -> {
			return isValidKeyword(keyword);
		}).collect(Collectors.toList());
	}

	public static boolean isValidKeyword(String keyword) {
		if (keyword == null || keyword.equals(""))
			return false;
		return !stopWords.contains(keyword.toLowerCase());
	}
}
