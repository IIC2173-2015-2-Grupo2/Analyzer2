package Tagger;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/* Básicamente el cuello de botella está en, al ver
 * cada palabra, ver si esta es tag o no, la revisión de si una 
 * palabra o expresión  
 * 
 * 
 * */


public class Tagger {
	
	public static HashMap<String, Tag> existingTags;
	
	public String[] tagNews(String body) throws Exception{
		
		if(body == null || body.isEmpty()){
			throw new Exception("empty body");
		}
		
		ArrayList<String> tags = new ArrayList<String>();
		Scanner sc = new Scanner(body);
		
		String token = sc.next();
		

		
		while(sc.hasNext()){
			while(token.length() < Tag.MIN_LEN_TAG && sc.hasNext()){
				token += " " + sc.next();
			}
			
			String tagCandidate = mayBeTag(token);
			while(token.length() < tagCandidate.length() - 1){
				token += " " + sc.next();
			}
			
			
			if(token.equalsIgnoreCase(tagCandidate)){
				tags.add(tagCandidate);
				
			}			
		}
		
		return (String[]) tags.toArray();
	}
	
	public static void addTag(String tagName){
		Tag t = new Tag(tagName);
		existingTags.put(t.getStartsWith(), t);
	}
	
	public static  String mayBeTag(String token){
		return existingTags.get(token).getContent();
	}
	
	
	
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
			addTag(tagContent);
		}
		
		
	}
	
	
}
