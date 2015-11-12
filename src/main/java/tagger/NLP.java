package tagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public abstract class NLP {
	private static ArrayList<String> capitalLetterWords;
	private static ArrayList<String> listText;
	private static ArrayList<String> repeatedWords;
	private static Map<String, Integer> wordCount;
	private static String language;
	public static  ArrayList<String> getTags(String text){
		String innerText = text;
		language = "sp"; //Cambiar para soportar inglés
		capitalLetterWords = new ArrayList<String>();
		repeatedWords = new ArrayList<String>();
		fillCapitalLetterWords(text);
		innerText = innerText.toLowerCase();
		String[] punctuation = {".", ",", ";", ":", "-", "_", "(", ")", "?", "¿", "!", "¡", "'", "<", ">"};	
		for (String word : punctuation) {
			innerText = innerText.replaceAll(word, "");
		}
		innerText = innerText.trim();

		wordCount = findDuplicateString(innerText);

		String[] arrayText = innerText.split(" ");
		listText = new ArrayList<String>();

		for (String word : arrayText) {
			listText.add(word);
		}
		deleteWords();

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
		outputWords.addAll(capitalLetterWords);
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
	
	private static void deleteWords(){
		if(language == "sp"){
			deleteSpanishWords();
		}
		else if (language == "en") {
			deleteEnglishWords();
		}
	}
	private static void deleteEnglishWords(){
		String[] articles = {"the", "a", "an", "some"};
		String[] connectors = {"however", "in", "contrast", "nevertheless", "nonetheless", "yet", "on", "other", "by", "comparison",
				"contrary", "instead", "any", "case", "all", "same", "likewise", "similarly", "correspondingly", "way", "also", "as",
				"therefore", "thus", "accordingly", "first", "firstly", "for", "thing", "begin", "with", "without",
				"second", "secondly", "third", "thridly", "fourth", "foruthly", "fifth", "also", "besides", "addition", "furthermore",
				"moreover", "finally", "last", "lastly", "most", "importantly", "primarily", "most", "significantly",
				"essentially", "basically", "particular", "particularly", "specifically", "example", "instance", "illustrat",
				"say", "namely", "differently", "put", "matter", "fact", "actually", "indeed", "as", "regard", "to", "regards",
				"concerned", "conclusion", "brief", "summary", "up", "down", "rather", "precise", "afterwards", "later",
				"meantime", "meanwhile", "anyway", "anyhow", "any", "rate", "therefore", "thus"};
		String[] personalPronouns = {"I", "me", "mine", "myself", "my", "you", "your", "yours", "yourself", "he", "him", "his", "himself",
				"she", "her", "hers", "herself", "it", "its", "itself", "we", "us", "our", "ours", "ourselves", "you", "your", "yours", 
				"yourself", "yourselves", "they", "them", "their", "theirs", "themselves", "thou", "thee", "thy", "thine", "thyself",
				"who", "whom", "whomself", "whoself", "whose"};
		String[] extras = {"there", "when", "how", "so", "up", "out", "no", "only", "well", "then", "where",
				"why", "now", "around", "once", "down", "here", "tonight", "away", "today", "far", "quite", "later", "above", "yet", "maybe", "otherwise",
				"near", "forward", "somewhere", "anywhere", "please", "forever", "somehow", "absolutely", "abroad", "yeah", "nowhere", "tomorrow", "yesterday",
				"more", "about", "such", "through", "new", "just", "any", "each", "much", "before", "between", "free", "right", "best", "since",
				"both", "sure", "without", "back", "better", "enough", "lot", "small", "though", "less", "little", "under", "next", "hard", "real",
				"left", "least", "short", "last", "within", "along", "lower", "true", "bad", "across", "clear", "easy", "full", "close", "late",
				"proper", "fast", "wide", "item", "wrong", "ago", "behind", "quick", "straight", "direct", "extra", "morning", "pretty", "overall",
				"alone", "bright", "flat", "whatever", "slow", "clean", "fresh", "whenever", "cheap", "thin", "cool", "fair", "fine", "smooth", 
				"false", "thick", "collect", "nearby", "wild", "apart", "none", "aside", "loud", "super", "tight", "honest", "ok", "pray"};
		String[] preposicions = {"on", "at", "in", "since", "for", "ago", "before", "to", "past", "to", "till", "until", "by", "next",
				"to", "beside", "under", "below", "over", "above", "across", "through", "into", "towards", "onto", "from", "of", "off",
				"out", "about"};
		String[] adverbs = {"not", "also", "often", "too", "usually", "really", "early", "never", "always", "sometimes", "together", "likely",
				"simply", "generally", "instead", "actually", "again", "rather", "almost", "especially", "ever", "quickly", "probably",
				"already", "below", "directly", "else", "if", "easily", "eventually", "exactly", "certainly", "normally", "currently",
				"extremely", "finally", "constantly", "properly", "soon", "specifically", "ahead", "daily", "highly", "inmidiately",
				"relatively", "slowly", "fairly", "primarily", "completely", "ultimately", "widely", "recently", "seriously", "frequently",
				"fully", "mostly", "naturally", "nearly", "occasionally", "carefully", "clearly", "essentially", "possible", "slightly", "somewhat",
				"equally", "greatly", "necessatily", "personally", "rarely", "regularly", "similarly", "basically", "closely", "effectively",
				"initially", "literally", "mainly", "merely", "gently", "hopefully", "originally", "roughly", "significantly", "totally", "twice",
				"elsewhere", "everywhere", "obviously", "perfectly", "physically", "succesfully", "suddenly", "truly", "virtually", "altogether",
				"anyway", "automatically", "deeply", "definitely", "deliberately", "hardly", "readily", "terribly", "unfortunately", "forth", "briefly",
				"moreover","strongly","honestly", "previously"};
		String[] demonstratives = {"that", "this", "these", "those"};
		deleteFromMap(articles);
		deleteFromMap(personalPronouns);
		deleteFromMap(connectors);
		deleteFromMap(extras);
		deleteFromMap(preposicions);
		deleteFromMap(adverbs);
		deleteFromMap(demonstratives);
	}
	private static void deleteSpanishWords(){
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
		deleteFromMap(articles);
		deleteFromMap(personalPronouns);
		deleteFromMap(connectors);
		deleteFromMap(extras);
		deleteFromMap(preposicions);
		deleteFromMap(adverbs);
		deleteFromMap(demonstratives);
	}

	private static void deleteFromMap(String[] wordsToDelete){
		for (String word : wordsToDelete) {
			if(wordCount.containsKey(word)){
				wordCount.remove(word);
			}
		}
	}

	private static void fillCapitalLetterWords(String text){
		String[] words = text.split(" ");
		boolean lastWasCapital = false;
		String temp = "";
		for (String word : words) {
			if(word.matches("^([A-Z][a-z]+)+$") && !lastWasCapital){
				lastWasCapital = true;
				temp = word;
			}
			else if (word.matches("^([A-Z][a-z]+)+$") && lastWasCapital) {
				temp = temp + " " + word;
			}
			else {
				lastWasCapital = false;
			}
			if (!temp.equals("") && !temp.equals("I")){
				capitalLetterWords.add(temp);
			}
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
