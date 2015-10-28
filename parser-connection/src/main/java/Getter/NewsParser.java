package Getter;
import java.awt.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.xml.crypto.Data;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Clase encargada de sacar la información contenida en el JSON y enviarla a los distintos adaptadores.
 * @author estebandib
 *
 */
public class NewsParser {
	private String newsToParse;
	private ArrayList<String> forTagger;
	private Lock parserLock;
	private GetterTaggerAdapter getterTaggerAdapter;
	private GetterSaverAdapter getterSaverAdapter;
	private ArrayList<New> listAllNews;
	private int debuggerCounter;

	public NewsParser(Lock l){
		newsToParse = "";
		parserLock = l;
		listAllNews = new ArrayList<New>();
		forTagger = new ArrayList<String>();

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
		listAllNews.clear();
		forTagger.clear();
		debuggerCounter = 0;
		JSONArray allNews = new JSONArray(newsToParse); //ojo acá, puede que no lo tome como array
		for (Object jsonObject : allNews) {
			debuggerCounter++;
			New recentNew = new New((String) ((JSONObject)jsonObject).get("title"),
					(String) ((JSONObject)jsonObject).get("time"),
					(String) ((JSONObject)jsonObject).get("header"),
					(String) ((JSONObject)jsonObject).get("body"),
					(String) ((JSONObject)jsonObject).get("url"),
					(String) ((JSONObject)jsonObject).getJSONArray("tags").toString());
			listAllNews.add(recentNew);
			forTagger.add(recentNew.getBody());
		}
		
		
		getterSaverAdapter = new GetterSaverAdapter(listAllNews);
		getterSaverAdapter.start();
		
		getterTaggerAdapter = new GetterTaggerAdapter(forTagger);
		getterTaggerAdapter.start();
		
		parserLock.unlock();
		
	}
	
}