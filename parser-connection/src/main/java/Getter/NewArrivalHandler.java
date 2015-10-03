package Getter;
import java.awt.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;


public class NewArrivalHandler {
	private NewsParser newsParser;
	private Lock parserLock;
	public NewArrivalHandler(){
		newsParser = new NewsParser(parserLock);
	}
	protected void newArrival(String s){
		parserLock.lock();
		newsParser.newArrival(s);
	}
}