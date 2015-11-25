package getter;



import java.util.ArrayList;

import saver.NewsSaver;
import tagger.Tag;
import tagger.Tagger;

/**
 * Adaptador entre el Parser del componente y el Tagger. Extiende Thread para poder independizarse de la
 * ejecución del parser.
 * @author estebandib
 *
 */
public class GetterTaggerAdapter extends Thread{
	private ArrayList<String> bodies;
	private Tagger tagger;
	private int newsId;
	
	public GetterTaggerAdapter(ArrayList<String> b, int newsId){
		tagger = new Tagger();
		bodies = b;
		this.newsId = newsId;
	}
	public void run(){
		sendList();
	}

	/**
	 * Envía la lista de cuerpos de noticia, una por una, al Tagger.
	 */
	private void sendList(){
		for (String string : bodies) {
			try {
				Tag[] tags = tagger.tagNews(string);
				NewsSaver saver = new NewsSaver();
				saver.saveNewsItemTags(tags, newsId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
