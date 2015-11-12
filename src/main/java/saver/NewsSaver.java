package saver;

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
	private String titleColumn = "title";
	private String dateColumn = "date";
	private String headerColumn = "summary";
	private String urlColumn = "url";
	private String newsItemNodeLabel = "NewsItem";
	private String tagNodeLabel = "Tag";
	private String nameColumn = "name";
	private String title, tags, header, date, url;
	private final String port, host, password, user;
	public static final int TITLE_INDEX = 0;
	public static final int DATE_INDEX = 1;
	public static final int HEADER_INDEX = 2;
	public static final int URL_INDEX = 3;
	public static final int TAG_INDEX = 4;

	public NewsSaver(){
		port = System.getenv("NEO4J_PORT");
		host = System.getenv("NEO4J_HOST");
		password = System.getenv("NEO4J_PASS");
		user = System.getenv("NEO4J_USER");
	}

	/**
	 * Encargado de guardar la distinta información en la base de datos
	 * @param s Arreglo de información con estructura [título, fecha, bajada, url, tags]
	 */
	public void saveInDataBase(String[] data){
		title = s[TITLE_INDEX];
		date = s[DATE_INDEX];
		header = s[HEADER_INDEX];
		url = s[URL_INDEX];
		tags = s[TAG_INDEX];

		final String txUri = "http://" + host + "/db/data/transaction/commit";
		WebResource resource2 = Client.create().resource(txUri);
		byte[] encodedBytes = Base64.encodeBase64((user + ":" + password).getBytes());
		//Creamos el nodo de la noticia
		String newsItemCreator = "{\"statements\" : [ {\"statement\" : \"" +
				"CREATE (n:"
				+ newsItemNodeLabel + " { "
				+ titleColumn + " : '"+ title +"', "
				+ dateColumn + " : '"+ date +"', "
				+ headerColumn + "  : '"+ header +"', "
				+ urlColumn + "  : '"+ url +"' }) RETURN ID(n)" +
				"\"} ]}";
        ClientResponse response2 = getClientResponse(resource2, newsItemCreator, encodedBytes);
        System.out.println(response2.toString());
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
	}

	private int getIdFromJsonResult(String result){
        System.out.println("Getting id from result: " + result);
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
