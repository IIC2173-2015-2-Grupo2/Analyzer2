package Getter;
import static spark.Spark.*;

import Saver.DatabaseManager;
import spark.Spark;

public class Main {

	public static void main(String[] args) {
		DatabaseManager.createDatabaseManager();
		Spark.post("/analyzer", (req, res) -> processPost());
	}
	
	private static void processPost(){
		
	}


}
