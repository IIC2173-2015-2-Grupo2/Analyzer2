package Getter;

import Saver.DatabaseManager;
import static spark.Spark.*;
import spark.*;
public class Main {
	/**
	 * Se encarga de recibir el post desde el Parser
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseManager.createDatabaseManager();
		post("/", (request, response) -> processPost(request, response));
	}
	/**
	 * Procesa el post, es decir, comienza con la primera etapa del Pipe & Filter.
	 */
	private static Response processPost(Request request, Response response){
		response.status(200);
		NewArrivalHandler nah = new NewArrivalHandler();
		nah.newArrival(request.body());
		return response;
	}


}
