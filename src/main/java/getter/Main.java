package getter;

import static spark.Spark.get;
import static spark.Spark.post;

import model.NewsItemData;
import saver.DatabaseManager;
import saver.NewsSaver;
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
