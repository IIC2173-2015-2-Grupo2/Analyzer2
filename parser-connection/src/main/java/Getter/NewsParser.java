package Getter;
import java.util.*;

import Saver.NewsSaver;

public class NewsParser {
	private String newsToParse;
	private NewsTagger newsTagger;
	private NewsSaver newsSaver;
	private String[] forSaver;
	private String forTagger;

	public NewsParser(){
		newsToParse = "";
		newsSaver = new NewsSaver();
		newsTagger = new NewsTagger();
	}

	public void newArrival(String s){
		newsToParse = s;
		parseNews();
	}

	private void parseNews(){
		JSONObject jsonObject;
		jsonObject = new JSONObject();
		//TODO: Parsear el JSON, entregar el cuerpo al tagger y el resto al DBManager

		//			if(newsToParse.size() > 1){
		//				decomposeJSON();
		//			}
		//			else{
		//				synchronized (parserLock) {
		//					decomposeJSON();
		//				}
		//			}
		//		}
	}

	//	private void decomposeJSON(){
	//		JSONObject jsonObject;
	//		jsonObject = new JSONObject(newsToParse.poll());
	//		//TODO: Parsear el JSON
	//	}
}