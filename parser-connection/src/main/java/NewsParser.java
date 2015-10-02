import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONObject;

public class NewsParser implements NewArrivalListener{
	Queue<String> newsToParse;
//	Object parserLock;
	public NewsParser(){
		newsToParse = new LinkedList<>();
//		parserLock = new Object();
		NewArrivalHandler.createListener(this);
	}
	public void newArrival(String s){
//		synchronized (parserLock) {
			newsToParse.add(s);
//		}
		if (newsToParse.size() == 1)
			parseNews();
	}
	private void parseNews(){
		JSONObject jsonObject;
		jsonObject = new JSONObject(newsToParse.poll());
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