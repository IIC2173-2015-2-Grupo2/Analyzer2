package getter;


import java.util.ArrayList;

import model.NewsItemData;
import saver.NewsSaver;
//import saver.DebuggerNewsSaver;

/**
 * Adaptador entre el Parser del componente y el Saver. Extiende Thread para poder independizarse de las
 * acciones del parser.
 * @author estebandib
 *
 */
public class GetterSaverAdapter extends Thread{
	private ArrayList<NewsItemData> toProcess;
	private NewsSaver newsSaver;
//	private DebuggerNewsSaver debuggerNewsSaver;
	private ArrayList<String> forTagger;
	
	public GetterSaverAdapter(ArrayList<NewsItemData> allNews, ArrayList<String> forTagger){
		newsSaver = new NewsSaver();
		//usado para debuggear
		//debuggerNewsSaver = new DebuggerNewsSaver();
		toProcess = allNews;
		this.forTagger = forTagger;
	}
	public void run(){
		saveNews();
	}

	/**
	 * Guarda, de a una, las noticias en la base de datos.
	 */
	private void saveNews(){
		for (NewsItemData newItem : toProcess){
			int id = newsSaver.saveInDataBase(newItem);
			
			GetterTaggerAdapter getterTaggerAdapter = new GetterTaggerAdapter(forTagger, id);
			getterTaggerAdapter.start();
			
			//usado para debuggear
			//debuggerNewsSaver.saveInDatabase(newItem);
		}
	}
}
