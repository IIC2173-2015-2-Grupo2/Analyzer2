package Getter;
import java.awt.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;

/**
 * Abstracción para procesar los post de a uno.
 * @author estebandib
 *
 */
public class NewArrivalHandler {
	private NewsParser newsParser;
	private Lock parserLock;
	public NewArrivalHandler(){
		newsParser = new NewsParser(parserLock);
	}
	
	/**
	 * Toma el JSON y se lo envía al parser del componente. Lo hace usando un lock para mantener 
	 * un solo thread parseando información a la vez.
	 * @param s es el JSON
	 */
	public void newArrival(String s){
		parserLock.lock();
		newsParser.newArrival(s);
	}
}