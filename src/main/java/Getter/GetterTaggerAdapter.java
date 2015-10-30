package Getter;



import java.util.ArrayList;

import Tagger.Tagger;

/**
 * Adaptador entre el Parser del componente y el Tagger. Extiende Thread para poder independizarse de la
 * ejecución del parser.
 * @author estebandib
 *
 */
public class GetterTaggerAdapter extends Thread{
	private ArrayList<String> bodies;
	private Tagger tagger;
	public GetterTaggerAdapter(ArrayList<String> b){
		tagger = new Tagger();
		bodies = b;
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
				tagger.tagNews(string);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
