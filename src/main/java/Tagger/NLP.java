package tagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class NLP {
	private static ArrayList<String> importantWords;
	private static ArrayList<String> listText;
	private static ArrayList<String> repeatedWords;
	private static Map<String, Integer> wordCount;
	public static  ArrayList<String> getTags(String text){
		String innerText = text;
		importantWords = new ArrayList<String>();
		repeatedWords = new ArrayList<String>();
		fillCapitalLetterWords(text);
		innerText = innerText.toLowerCase();
		String[] punctuation = {".", ",", ";", ":", "-", "_", "(", ")", "?", "¿", "!", "¡", "'", "<", ">"};
		String[] articles = {"el", "la", "los", "las", "un", "unos", "una", "unas"};
		String[] connectors = {"además","asímismo", "mismo modo", "misma manera", "igualmente", "también", "como", "debido",
				"gracias","porque", "puesto", "visto", "condición", "así", "entonces", "y"};
		String[] personalPronouns = {"yo", "él", "ella", "ello", "vos", "tú", "usted", "nosotros", "nosotras", "ustedes",
				"vosotros", "vosotras", "ellos", "ellas", "mí","conmigo", "ti", "contigo", "consigo"};
		String[] extras = {"de", "que", "qué", "cómo", "ya", "del", "ha", "eso", "lo", "si", "sí", "no"};
		String[] preposicions = {"ex", "a", "ante", "bajo", "cabe", "con", "contra", "de", "desde", "en", "entre", "hacia",
				"hasta", "para", "por", "según", "sin", "so", "sobre", "tras"};
		String[] adverbs = {"ahí", "allí", "aquí", "acá", "delante", "detrás", "arriba", "abajo", "cerca", "lejos",
				"encima", "ya", "aún", "hoy", "tarde", "pronto","todavía", "ayer", "nunca", "siempre", "jamás", "ahora",
				"antes", "después", "posteriormente", "primero", "respectivamente", "mal", "bien", "regular", "deprisa",
				"despacio", "mejor", "peor", "igual", "similar", "fácilmente", "difícilmente", "así", "naturalmente","muy",
				"más", "poco", "bastante", "demasiado", "menos", "mucho", "algo", "casi", "sólo", "nada", "quizás",
				"acaso", "tal"};
		String[] demonstratives = {"eso", "esto", "aquello", "ese", "este", "aquel", "esta", "esa", "aquella", "estos",
				"esos", "aquellos", "estas", "esas", "aquellas" };
		innerText.trim();

		for (String word : punctuation) {
			innerText = innerText.replace(word, "");
		}

		wordCount = findDuplicateString(innerText);

		String[] arrayText = innerText.split(" ");
		listText = new ArrayList<String>();

		for (String word : arrayText) {
			listText.add(word);
		}

		deleteFromMap(articles);
		deleteFromMap(personalPronouns);
		deleteFromMap(connectors);
		deleteFromMap(extras);
		deleteFromMap(preposicions);
		deleteFromMap(adverbs);
		deleteFromMap(demonstratives);

		for (String word : listText) {
			if(wordCount.containsKey(word)){
				int times = wordCount.get(word);
				if(times >= 2){
					repeatedWords.add(word);
				}
			}
		}

		ArrayList<String> outputWords = new ArrayList<String>();
		outputWords.addAll(repeatedWords);
		outputWords.addAll(importantWords);
		return outputWords;
		//return createTags(repeatedWords, importantWords);
	}

//	private ArrayList<Tag> createTags(ArrayList<String> repeatedWords, ArrayList<String> importantWords) {
//		ArrayList<Tag> tagList = new ArrayList<Tag>();
//		for (String word : repeatedWords) {
//			tagList.add(new Tag(word));
//		}
//		for (String word : importantWords) {
//			tagList.add(new Tag(word));
//		}
//		return tagList;
//	}


	private static void deleteFromMap(String[] wordsToDelete){
		for (String word : wordsToDelete) {
			if(wordCount.containsKey(word)){
				wordCount.remove(word);
			}
		}
	}

	private static void fillCapitalLetterWords(String text){
		String[] words = text.split(" ");
		for (String word : words) {
			if(word.matches("^([A-Z][a-z]+)+$"))
				importantWords.add(word);
		}
	}

	private static Map<String, Integer> findDuplicateString(String str) {
		String[] stringArrays = str.split(" ");
		Map<String, Integer> map = new HashMap<String, Integer>();
		Set<String> words = new HashSet<String>(Arrays.asList(stringArrays));
		int count = 0;
		for (String word : words) {
			for (String temp : stringArrays) {
				if (word.equals(temp)) {
					++count;
				}
			}
			map.put(word, count);
			count = 0;
		}

		return map;

	}
}
