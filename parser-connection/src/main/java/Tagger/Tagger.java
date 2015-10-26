package Tagger;


import java.util.ArrayList;
import java.util.HashMap;
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

			
			
			
		}
		
		return (String[]) tags.toArray();
	}
	
	public void addTag(String tagName){
		Tag t = new Tag(tagName);
		existingTags.put(t.getStartsWith(), t);
	}
	
	public String mayBeTag(String token){
		return existingTags.get(token).getContent();
	}
	
	
	
	
}
