package Saver;

import javax.xml.crypto.Data;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.codec.binary.Base64;

import javax.ws.rs.core.MediaType;

import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;

private static String titleColumn = "title";
private static String dateColumn = "date";
private static String headerColumn = "header";
private static String urlColumn = "url";

private static String newsItemNodeLabel = "NewsItem";
private static String tagNodeLabel = "Tag";
private static String nameColumn = "name";
/**
 * Clase encargada de guardar la información en la base de datos
 * @author estebandib
 *
 */
public class NewsSaver {
	String title, tags, header, date, url, imageUrl;
	final String port, host, password, user;
	public NewsSaver(){
		port = DatabaseManager.getPort();
		host = DatabaseManager.getHost();
		password = DatabaseManager.getPassword();
		user = DatabaseManager.getUser();
	}
	/**
	 * Encargado de guardar la distinta información en la base de datos
	 * @param s Arreglo de información con estructura [título, fecha, bajada, url, tags]
	 */
	public void saveInDataBase(String[] s){
		title = s[0];
		date = s[1];
		header = s[2];
		url = s[3];
		tags = s[4];

		final String txUri = host + "transaction/commit";
		WebResource resource2 = Client.create().resource(txUri);	
		byte[] encodedBytes = Base64.encodeBase64((user + ":" + password).getBytes());
		
		//Creamos el nodo de la noticia
		String newsItemCreator = "{\"statements\" : [ {\"statement\" : \"" +
				"CREATE (n:" 
				+ newsItemNodeLabel + " { " 
				+ titleColumn + " : 'Test', " 
				+ dateColumn + " : 'Tests', " 
				+ headerColumn + "  : 'Test', " 
				+ urlColumn + "  : 'Test' }) RETURN ID(n)" + 
				"\"} ]}";
		ClientResponse response2 = getClientResponse(resource2, newsItemCreator, encodedBytes);

		String dataNewsItem = response2.getEntity(String.class);
		int newsItemId = getIdFromJsonResult(dataNewsItem);
		response2.close();
		
		//Creamos los nodos de los tags y sus relaciones
		String[] sepTags = tags.split(",");
		for (int i = 0; i < sepTags.length; i++) {
			String tagsCreator = "{\"statements\" : [ {\"statement\" : \"" +
					"MERGE (n:" 
					+ tagNodeLabel + " { " 
					+ nameColumn + "  : '" 
					+ sepTags[i] + "' }) RETURN ID(n)" + 
					"\"} ]}";
			
			ClientResponse responseTag = getClientResponse(resource2, tagsCreator, encodedBytes);

			String tagItem = responseTag.getEntity(String.class);
			int auxTagId = getIdFromJsonResult(tagItem);
			responseTag.close();
			
			String relationCreator = String.format("{\"statements\" : [ {\"statement\" : \"" 
					+ "MATCH (a:NewsItem),(b:Tag) "
					+ "WHERE ID(a) = %d AND ID(b) = %d "
					+ "CREATE (b)-[r:`is in `]->(a)\"} ]}", newsItemId, auxTagId);
			
			ClientResponse relationTag = getClientResponse(resource2, relationCreator, encodedBytes);
			relationTag.close();
	}

	private int getIdFromJsonResult(String result){
		JsonParser parser = new JsonParser();
		return parser.parse(result)
				.getAsJsonObject()
				.getAsJsonArray("results")
				.get(0).getAsJsonObject()
				.getAsJsonArray("data")
				.get(0).getAsJsonObject()
				.get("row").getAsInt();
	}

	private ClientResponse getClientResponse(WebResource resource, String entity, byte[] bytes){
		return resource
		        .accept( MediaType.APPLICATION_JSON)
		        .type( MediaType.APPLICATION_JSON)
		        .entity(entity)
		        .header("Authorization", "Basic " + new String(bytes))
		        .post(ClientResponse.class);
	}
}