package Getter;



import Tagger.Tagger;

/**
 * Adaptador entre el Parser del componente y el Tagger. Extiende Thread para poder independizarse de la
 * ejecución del parser.
 * @author estebandib
 *
 */
public class GetterTaggerAdapter extends Thread{
	private String[] bodies;
	private String stringBodies;
	private Tagger tagger;
	public GetterTaggerAdapter(String b){
		tagger = new Tagger();
		stringBodies = b;
	}
	public void run(){
		sendList();
	}
	/**
	 * Envía la lista de cuerpos de noticia, una por una, al Tagger.
	 */
	private void sendList(){
		bodies = stringBodies.split("*");
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
