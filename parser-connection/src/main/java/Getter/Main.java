package Getter;

import Saver.DatabaseManager;
import spark.Request;
import spark.Spark;

public class Main {
	/**
	 * Se encarga de recibir el post desde el Parser
	 * @param args
	 */
	public static void main(String[] args) {

		DatabaseManager.createDatabaseManager();
		Spark.post("/analyzer", (req, res) -> processPost(req));
	}
	/**
	 * Procesa el post, es decir, comienza con la primera etapa del Pipe & Filter.
	 */
	private static int processPost(Request req){
		NewArrivalHandler nah = new NewArrivalHandler();
		nah.newArrival(req.body());
		return 200;
	}


}
