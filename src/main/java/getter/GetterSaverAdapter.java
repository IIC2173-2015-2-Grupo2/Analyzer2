package getter;



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
	private NewsItemData toProcess;
	private NewsSaver newsSaver;
	//	private DebuggerNewsSaver debuggerNewsSaver;
	//	private ArrayList<String> forTagger;

	public GetterSaverAdapter(NewsItemData newsItem){
		newsSaver = new NewsSaver();
		//usado para debuggear
		//debuggerNewsSaver = new DebuggerNewsSaver();
		toProcess = newsItem;
		//		this.forTagger = forTagger;
	}
	public void run(){
		saveNews();
	}

	/**
	 * Guarda, de a una, las noticias en la base de datos.
	 */
	private void saveNews(){
		/*int id = */newsSaver.saveInDataBase(toProcess);

		//			GetterTaggerAdapter getterTaggerAdapter = new GetterTaggerAdapter(forTagger, id);
		//			getterTaggerAdapter.start();

		//usado para debuggear
		//debuggerNewsSaver.saveInDatabase(newItem);

	}
}
