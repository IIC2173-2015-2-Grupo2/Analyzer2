package getter;
import java.util.ArrayList;

import org.json.*;

import model.NewsItemData;

/**
 * Clase encargada de sacar la información contenida en el JSON y enviarla a los distintos adaptadores.
 * @author estebandib
 */
public class NewsParser {
	private String newsToParse;
	private ArrayList<String> forTagger;
	private ArrayList<NewsItemData> listAllNews;

	public NewsParser(){
		newsToParse = "";
		listAllNews = new ArrayList<NewsItemData>();
		forTagger = new ArrayList<String>();

	}

	/**
	 * Procesa la nueva llegada de JSON
	 * @param s el JSON
	 */
	public void newArrival(String s){
		newsToParse = s;
		try {
			parseNews();
		} catch (Exception e) {

		}

	}


	//Estructura del json language: sp o en
	//{ [ {title=>"Titulo", time=>"yyyy-mm-dd hh:mm:ss", header=>"Descripcion", url=>"url a la pagina", imageUrl => "url de la imagen", source=> "fuente", body=>"todo el body de la noticia", tags: [ ], language: "sp o en"},{noticia2},... ] }
	/**
	 * Se encarga de obtener la información desde el JSON
	 */
	private void parseNews(){
		listAllNews.clear();
		forTagger.clear();
		JSONArray allNews = new JSONArray(newsToParse); //ojo acá, puede que no lo tome como array
		for (Object jsonObject : allNews) {
			NewsItemData recentNew = new NewsItemData((String) ((JSONObject)jsonObject).get("title"),
					(String) ((JSONObject)jsonObject).get("time"),
					(String) ((JSONObject)jsonObject).get("header"),
					(String) ((JSONObject)jsonObject).get("body"),
					(String) ((JSONObject)jsonObject).get("url"),
					(String) ((JSONObject)jsonObject).get("imageUrl"),
					(String) ((JSONObject)jsonObject).get("source"),
					(String) ((JSONObject)jsonObject).get("language"),
					(String) ((JSONObject)jsonObject).getJSONArray("tags").toString());
			listAllNews.add(recentNew);
			forTagger.add(recentNew.getBody() + "ç" + recentNew.getLanguage());
		}
		for (NewsItemData newsItem : listAllNews) {
			GetterSaverAdapter getterSaverAdapter = new GetterSaverAdapter(newsItem);
			getterSaverAdapter.start();
		}


	}

}
