package getter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Abstracción para procesar los post de a uno.
 * @author estebandib
 */
public class NewArrivalHandler {
	private NewsParser newsParser;
	private Lock parserLock;
	public NewArrivalHandler(){
		parserLock = new ReentrantLock();
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
