package Getter;
import java.util.concurrent.locks.Lock;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Clase encargada de sacar la información contenida en el JSON y enviarla a los distintos adaptadores.
 * @author estebandib
 *
 */
public class NewsParser {
	private String newsToParse;
	private String forTagger;
	private Lock parserLock;
	private GetterTaggerAdapter getterTaggerAdapter;
	private GetterSaverAdapter getterSaverAdapter;

	public NewsParser(Lock l){
		newsToParse = "";
		parserLock = l;
	}
	/**
	 * Procesa la nueva llegada de JSON
	 * @param s el JSON
	 */
	public void newArrival(String s){
		newsToParse = s;
		parseNews();
	}
	//Estructura del json 
	//{ [ {title=>"Titulo", time=>"yyyy-mm-dd hh:mm:ss", header=>"Descripcion", url=>"url a la pagina", body=>"todo el body de la noticia" tags: [ ]},{noticia2},... ] }
	/**
	 * Se encarga de obtener la información desde el JSON
	 */
	private void parseNews(){
		String title, date, header, url, body, tags;
		title = date = header = url = body = tags = "";
		JSONArray allNews = new JSONArray(newsToParse); //ojo acá, puede que no lo tome como array
		for (Object jsonObject : allNews) {
			title += (String) ((JSONObject)jsonObject).get("title") + "*";
			date += (String) ((JSONObject)jsonObject).get("time") + "*";
			header += (String) ((JSONObject)jsonObject).get("header") + "*";
			url += (String) ((JSONObject)jsonObject).get("url") + "*";
			body += (String) ((JSONObject)jsonObject).get("body") + "*";
			tags += (String) ((JSONObject)jsonObject).getJSONArray("title").toString() + "*";
		}
		
		int end = title.length()-1;
		
		String[] forSaver = {title.substring(0,end), date.substring(0,end), header.substring(0,end), url.substring(0, end), tags.substring(0, end)};
		getterSaverAdapter = new GetterSaverAdapter(forSaver);
		getterSaverAdapter.start();
		
		forTagger = body.substring(0, body.length()-1);
		getterTaggerAdapter = new GetterTaggerAdapter(forTagger);
		getterTaggerAdapter.start();
		
		parserLock.unlock();
		
	}
}