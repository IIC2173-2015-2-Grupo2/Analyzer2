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
/*
		Tagger t = new Tagger();
		try {
			t.tagNews("Alexis metio 2 golazos en la premier Michelle Bachelet");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	/*	try {
			Tagger.seed();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		DatabaseManager.createDatabaseManager();

		/*
		try {
			Tagger.miniSeed();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		Spark.port(8080);
		post("/", (request, response) -> {
			NewArrivalHandler nah = new NewArrivalHandler();
			nah.newArrival(request.body());
			return response;
		});

		get("/", (request, response) -> "status: on");
		
		for(int i = 0; i < 100 ; i++){
			NewsSaver saver = new NewsSaver();
			NewsItemData item = new NewsItemData("Some title" + i, "Some date", "Some summary", "Body", "url", "image", "SantiApi", "es", "tag1, tag2, tag3");
			saver.saveInDataBase(item);
		}
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
