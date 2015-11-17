package getter;


import java.util.ArrayList;

import model.NewsItemData;
import saver.NewsSaver;
import saver.DebuggerNewsSaver;

/**
 * Adaptador entre el Parser del componente y el Saver. Extiende Thread para poder independizarse de las
 * acciones del parser.
 * @author estebandib
 *
 */
public class GetterSaverAdapter extends Thread{
	private ArrayList<NewsItemData> toProcess;
	private NewsSaver newsSaver;
	private DebuggerNewsSaver debuggerNewsSaver;
	public GetterSaverAdapter(ArrayList<NewsItemData> allNews){
		//newsSaver = new NewsSaver();
		//usado para debuggear
		debuggerNewsSaver = new DebuggerNewsSaver();
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
			//newsSaver.saveInDataBase(newItem);
			//usado para debuggear
			debuggerNewsSaver.saveInDatabase(newItem);
		}
	}
}
