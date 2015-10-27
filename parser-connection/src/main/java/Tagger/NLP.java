package Tagger;


import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NLP {
	//TODO: Ordenar porque cambié de lista a map
	ArrayList<String> importantWords;
	ArrayList<String> listText;
	ArrayList<String> repeatedWords;
	Map<String, Integer> wordCount;
	public ArrayList<Tag> getTags(String text){
//		text = "El ministro Juan Manuel Muñoz aceptó la solicitud de la defensa de las tres víctimas de Fernando Karadima -encabezada por el abogado Juan Pablo Hermosilla- respecto a que el papa Francisco declare por exhorto en el marco de la demanda contra el Arzobispado de Santiago por los supuestos actos de encubrimiento de obispos en materia de abuso sexual. "
//				+ "Ello, luego de conocer los dichos del pontífice que defiende al obispo de Osorno Juan Barros, contra quien pesan acusaciones de encubrir los abusos del ex párroco de El Bosque. "
//				+ "De esta manera, según detalló a Emol Hermosilla, el magistrado deberá redactar el exhorto que luego será enviado a la Corte Suprema, a cargo de remitirlo al Ministerio de Relaciones Exteriores. "
//				+ "Una vez en Cancillería, será enviado al Vaticano para los trámites de rigor. "
//				+ "La diligencia será parte de un listado de personas que fueron requeridas para deponer sobre los puntos de prueba fijados por el el juez Muñoz. "
//				+ "Lo anterior, enmarcado en la etapa probatoria de la demanda presentada por James Hamilton, Juan Carlos Cruz y José Andrés Murillo y que buscan una indemnización por perjuicios de $450 millones. "
//				+ "Ello, porque según alegan, la Iglesia y sus autoridades cometieron negligencias sistemáticas al momento de abordar las denuncias de agresiones sexuales en contra religiosos, y específicamente de Karadima, condenado por el Vaticano a una vida de penitencia y oración. ";
//		
//		text = "hola hola hola chao chao siete siete siete siete asd asd asd mm mm jg";
		importantWords = new ArrayList<String>();
		repeatedWords = new ArrayList<String>();
		fillCapitalLetterWords(text);
		text = text.toLowerCase();
//		System.out.println(text);
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
		text.trim();
		
		for (String word : punctuation) {
			text = text.replace(word, "");
		}
		
		String[] arrayText = text.split(" ");
		listText = new ArrayList<String>();
		
		for (String word : arrayText) {
			listText.add(word);
		}
		//Sacamos las palabras repetidas para poder eliminar bien
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(listText);
		listText.clear();
		listText.addAll(hs);

		//Eliminamos las respectivas palabras
		
		deleteFromMap(articles);
		deleteFromMap(personalPronouns);
		deleteFromMap(connectors);
		deleteFromMap(extras);
		deleteFromMap(preposicions);
		deleteFromMap(adverbs);
		deleteFromMap(demonstratives);
		
//		System.out.println(text);
		
//		for (String importantWord : importantWords) {
//			System.out.println(importantWord + "\n");
//		}
//		for (String word : listText) {
//			System.out.println(word + " ");
//		}
		
		//Limpiamos y creamos de nuevo, esta vez con las palabras repetidas, para poder contarlas
		listText.clear();
		for (String word : arrayText) {
			listText.add(word);
		}
		
		String textWithoutTrash = listToString(listText);
		wordCount = findDuplicateString(textWithoutTrash.toString());
		for (String word : listText) {
			int times = wordCount.get(word);
			System.out.println(word + "," + wordCount.get(word));
			if(times >= 3){
				repeatedWords.add(word);
			}
		}
		
		return createTags(repeatedWords, importantWords);
	}
	
	private ArrayList<Tag> createTags(ArrayList<String> repeatedWords, ArrayList<String> importantWords) {
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		for (String word : repeatedWords) {
			tagList.add(new Tag(word));
		}
		for (String word : importantWords) {
			tagList.add(new Tag(word));
		}
		return tagList;
	}

	private void deleteFromMap(String[] wordsToDelete){
		for (String word : wordsToDelete) {
			if(repeatedWords.contains(word)){
				repeatedWords.remove(word);
			}
		}
	}
	
	private String listToString(ArrayList<String> listText2) {
		String returnValue = "";
		for (String string : listText2) {
			returnValue += string + " ";
		}
		return returnValue;
	}
	
	private void fillCapitalLetterWords(String text){
		String[] words = text.split(" ");
		for (String word : words) {
			if(word.matches("^([A-Z][a-z]+)+$"))
				importantWords.add(word);
		}
	}
	
	private Map<String, Integer> findDuplicateString(String str) {
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
