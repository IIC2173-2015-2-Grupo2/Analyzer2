package saver;

import model.NewsItemData;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.codec.binary.Base64;
import javax.ws.rs.core.MediaType;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;

/**
 * Clase encargada de guardar la información en la base de datos
 * @author estebandib
 */
public class NewsSaver {
	private String newsItemNodeLabel = "NewsItem";
	private String tagNodeLabel = "Tag";
	private String nameColumn = "name";
	private String title, header, date, url, image, fuente;
	private final String host, password, user;

	public NewsSaver(){
		host = System.getenv("NEO4J_HOST");
		password = System.getenv("NEO4J_PASS");
		user = System.getenv("NEO4J_USER");
	}

	/**
	 * Encargado de guardar la distinta información en la base de datos
	 * @param s NewsItemData de información con estructura [título, fecha, bajada, url, tags, entre otros]
	 */
	public void saveInDataBase(NewsItemData data){
		final String txUri = "http://" + host + "/db/data/transaction/commit";
		WebResource resource2 = Client.create().resource(txUri);
		byte[] encodedBytes = Base64.encodeBase64((user + ":" + password).getBytes());
		//Creamos el nodo de la noticia
		String newsItemCreatorPrefix = "{\"statements\" : [ {\"statement\" : \"CREATE (n:" + newsItemNodeLabel + " { ";
		String newsItemCreatorSuffix = "' }) RETURN ID(n)\"} ]}";
		String newsItemCreatorBody = "";

		for(Map.Entry<String, String> entry : data.getSetFields().entrySet()){
			newsItemCreatorBody += entry.getKey() + ":" + entry.getValue();
    }

		String newsItemCreatorString = newsItemCreatorPrefix + newsItemCreatorBody + newsItemCreatorSuffix;

		ClientResponse response2 = getClientResponse(resource2, newsItemCreatorString, encodedBytes);
		String dataNewsItem = response2.getEntity(String.class);
		int newsItemId = getIdFromJsonResult(dataNewsItem);
		response2.close();

		//Creamos los nodos de los tags y sus relaciones
		String[] sepTags = data.getTags().split(",");
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
