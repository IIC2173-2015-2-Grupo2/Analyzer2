package Getter;
import static spark.Spark.*;

import Saver.DatabaseManager;
import spark.Spark;

public class Main {
	/**
	 * Se encarga de recibir el post desde el Parser
	 * @param args
	 */
	public static void main(String[] args) {
		
		DatabaseManager.createDatabaseManager();
		Spark.post("/analyzer", (req, res) -> processPost());
		
		
		
		
		
	}
	/**
	 * Procesa el post, es decir, comienza con la primera etapa del Pipe & Filter.
	 */
	private static void processPost(){
		
	}


}
