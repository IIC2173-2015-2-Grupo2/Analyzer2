package getter;


import java.util.ArrayList;

import model.NewsItemData;
import saver.NewsSaver;

/**
 * Adaptador entre el Parser del componente y el Saver. Extiende Thread para poder independizarse de las
 * acciones del parser.
 * @author estebandib
 *
 */
public class GetterSaverAdapter extends Thread{
	private ArrayList<NewsItemData> toProcess;
	private NewsSaver newsSaver;
	public GetterSaverAdapter(ArrayList<NewsItemData> allNews){
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
		for (NewsItemData newItem : toProcess){
			newsSaver.saveInDataBase(newItem);
		}
	}
}
