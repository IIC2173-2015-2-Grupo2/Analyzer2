package getter;

import static spark.Spark.get;
import static spark.Spark.post;

import saver.DatabaseManager;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Main{
	/**
	 * Se encarga de recibir el post desde el Parser
	 * @param args
	 */

	public static void main(String[] args) {

		DatabaseManager.createDatabaseManager();
		
		Spark.port(8080);
		post("/", (request, response) -> {
			NewArrivalHandler nah = new NewArrivalHandler();
			nah.newArrival(request.body());
			return response;
		});

		get("/", (request, response) -> "status: on");
		/*
		for(int i = 20; i < 100 ; i++){
			NewsSaver saver = new NewsSaver();
			NewsItemData item = new NewsItemData("Some title" + i, "Some date", "Some summary", "Body", "url", "image", "SantiApi", "es", "tag1, tag2, tag3");
			saver.saveInDataBase(item);
		}*/
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
