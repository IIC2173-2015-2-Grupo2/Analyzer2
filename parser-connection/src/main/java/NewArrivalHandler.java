import java.awt.List;
import java.util.LinkedList;
import java.util.Queue;


public class NewArrivalHandler {
	private NewsParser newsParser;
	public NewArrivalHandler(){
		newsParser = new NewsParser();
	}
	protected void newArrival(String s){
		newsParser.newArrival(s);	
	}
}