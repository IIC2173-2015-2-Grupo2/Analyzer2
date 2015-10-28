package Getter;


import Saver.DatabaseManager;
import Tagger.Feed;
import Tagger.Tagger;

import static spark.Spark.*;

import java.io.IOException;

import spark.*;

public class Main{
	/**
	 * Se encarga de recibir el post desde el Parser
	 * @param args
	 */
	
	public static void main(String[] args) {
		//port(8839);
		DatabaseManager.createDatabaseManager();
		
		try {
			Tagger.miniSeed();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		post("/", (request, response) -> /*processPost(request, response)*/{
			NewArrivalHandler nah = new NewArrivalHandler();
			nah.newArrival(request.body());
			return response;
		});
	}
	/**
	 * Procesa el post, es decir, comienza con la primera etapa del Pipe & Filter.
	 */
	public static Response processPost(Request request, Response response){
		NewArrivalHandler nah = new NewArrivalHandler();
		nah.newArrival(request.body());
		return response;
		
	}


}
