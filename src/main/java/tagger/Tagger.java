package tagger;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import tagger.Tag.DataSetType;

/* Básicamente el cuello de botella está en, al ver
 * cada palabra, ver si esta es tag o no, la revisión de si una
 * palabra o expresión
 *
 *
 * */



public class Tagger {


	public static HashMap<String, Tag> existingTags;

	private HashSet<String> people;
	private HashSet<String> places;
	private HashSet<String> categories;
	private HashSet<String> companies;

	public Tagger(){

		people = new HashSet<String>();
		String[] _people = {"Steelback", "Febiven", "YellOwStar", "Reignover", "Huni",
				"Jack", "Hai", "Charlie", "LemonNation", "Bubbadub", "Aconr", "TBQ",
				"GODV", "imp", "Pyl", "Gandalf", "Putin", "Obama", "Merkel",
				"Artyom Lesnitsky Oktyabrskaya", "Trump", "Tim Cook", "Emma Watson",
				"Tsipras", "Xi Jinping", "Hillary Clinton", "Netanyahu", "Jack Black",
				"Chewbacca", "PewDiePie"};
		for(int i = 0 ; i < _people.length ; i++){
			people.add(_people[i]);
		}


		places = new HashSet<String>();
		String[] _places = {"Paris", "Berlin", "Los Angeles", "Shanghai", "London",
				"Santiago", "Talca", "Buenos Aires", "New York", "Madrid", "Moscow",
				"Zagreb", "King's Landing", "Winterfell", "Mordor", "the world", "Chile",
				"USA", "the UK", "Germany", "Uganda", "Ireland", "France", "Mexico",
				"El Salvador", "Australia", "Vatican City", "Mordor", "King's Landing"};
		for(int i = 0 ; i < _places.length ; i++){
			places.add(_places[i]);
		}


		categories = new HashSet<String>();
		String[] _categories= {"Technology", "ES", "Games", "Entertainment",
				"Criminal", "Illegal", "World", "Police", "Technology",
				"World Domination"};
		for(int i = 0 ; i < _categories.length ; i++){
			categories.add(_categories[i]);
		}


		companies = new HashSet<String>();
		String[] _companies = {"Google", "Amazon", "Electronic Arts", "Ubisoft", "Zinga",
				"Clash of Clans' creator", "Candy Crush's creator", "Valve", "OMGPop",
				"Dropbox", "Sony", "LG", "Los Pollos Hermanos", "Facebook", "Twitter",
				"Manga Corta", "Netflix", "ArquiNews", "Smartboard", "Microsoft", "Apple"};
		for(int i = 0 ; i < _companies.length ; i++){
			companies.add(_companies[i]);
		}
	}



	public Tag[] tagNews(String body) throws Exception{

		if(body == null || body.isEmpty()){
			return null;
		}

		ArrayList<String> arrlst = NLP.getTags(body.split("ç")[0], body.split("ç")[1]);
		Tag[] output = new Tag[arrlst.size()];

		for (int i = 0 ; i < arrlst.size() ; i++) {
			String tag = arrlst.get(i);
			DataSetType dataSet = null;

			if(people.contains(tag))
				dataSet = DataSetType.PEOPLE;
			else if(places.contains(tag))
				dataSet = DataSetType.PLACES;
			else if(categories.contains(tag))
				dataSet = DataSetType.CATEGORIES;
			else if(companies.contains(tag))
				dataSet = DataSetType.COMPANIES;
			else
				dataSet = DataSetType.OTHER;

			output[i] = new Tag(tag, dataSet);
		}
		

		return output;
	}


	public String mayBeTag(String token){
		return existingTags.get(token).getContent();
	}


	/*
	public static void seed() throws IOException{
		HashSet<String> hs = new HashSet<String>();

		for(int year = 2010 ; year <= 2015 ; year++){
			for(int month = 1; month <= 12 ; month++){
				for(int day = 1; day <= 28 ; day+=3){
					ArrayList<String> kws = Feed.parseTrendsJSON(Feed.getGoogleTrends(year, month, day, 38));
					hs.addAll(kws);
				}
			}
		}

		for(int year = 2011 ; year <= 2014 ; year++){
			for(int month = 1; month <= 12 ; month++){
				hs.addAll(   Feed.parseChartsJSON(     Feed.getGoogleTopCharts(year, month)   )   );

			}
		}


		int[] years = {2008,2009,2011,2012,2013,2014};
		for (int i = 0; i < years.length; i++) {
			hs.addAll(   Feed.parseChartsJSON(     Feed.getGoogleTopChartsLocation(years[i], "CL")   )   );
		}

		for (String tagContent : hs) {
			System.out.println(tagContent);
			addTag(tagContent);
		}




	}

	public static void miniSeed() throws IOException{
		existingTags = new HashMap<String, Tag>();
		HashSet<String> hs = new HashSet<String>();

		for(int year = 2015 ; year <= 2015 ; year++){
			for(int month = 12; month <= 12 ; month++){
				for(int day = 1; day <= 28 ; day+=3){
					ArrayList<String> kws = Feed.parseTrendsJSON(Feed.getGoogleTrends(year, month, day, 38));
					hs.addAll(kws);
				}
			}
		}

		for(int year = 2014 ; year <= 2014 ; year++){
			for(int month = 12; month <= 12 ; month++){
				hs.addAll(   Feed.parseChartsJSON(     Feed.getGoogleTopCharts(year, month)   )   );

			}
		}


		for (String tagContent : hs) {
			addTag(tagContent);
		}


	}
	*/

}
