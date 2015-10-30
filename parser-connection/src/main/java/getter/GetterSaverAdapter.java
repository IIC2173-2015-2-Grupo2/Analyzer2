package getter;


import java.util.ArrayList;

import saver.NewsSaver;

/**
 * Adaptador entre el Parser del componente y el Saver. Extiende Thread para poder independizarse de las
 * acciones del parser.
 * @author estebandib
 *
 */
public class GetterSaverAdapter extends Thread{
	private ArrayList<New> toProcess;
	private NewsSaver newsSaver;
	public GetterSaverAdapter(ArrayList<New> allNews){
		newsSaver = new NewsSaver();
		toProcess = allNews;
	}
	public void run(){
		saveNews();
	}
	
	/**
	 * Guarda, de a una, las noticias en la base de datos.
	 */
	private void saveNews(){
		for (New news : toProcess){
			String[] toSave = {news.getTitle(), news.getDate(), news.getHeader(), news.getUrl(), news.getTags()};
			newsSaver.saveInDataBase(toSave);
		}
	}
}
