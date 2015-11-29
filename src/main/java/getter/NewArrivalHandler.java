package getter;


/**
 * Abstracción para procesar los post de a uno.
 * @author estebandib
 */
public class NewArrivalHandler {
	private NewsParser newsParser;
	public NewArrivalHandler(){
		newsParser = new NewsParser();
	}

	/**
	 * Toma el JSON y se lo envía al parser del componente. Lo hace usando un lock para mantener
	 * un solo thread parseando información a la vez.
	 * @param s es el JSON
	 */
	public void newArrival(String s){
		newsParser.newArrival(s);
	}
}
