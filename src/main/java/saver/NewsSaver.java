package saver;

import model.NewsItemData;
import tagger.Tag;
import tagger.Tag.DataSetType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.codec.binary.Base64;

import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;

/**
 * Clase encargada de guardar la información en la base de datos
 * @author estebandib
 */
public class NewsSaver {
	private String newsItemNodeLabel = "NewsItem";
	private String nameColumn = "name";
	private WebResource resource2;
	private byte[] encodedBytes;

	public NewsSaver() {
		// port = System.getenv("NEO4J_PORT");
		String host = "arqui7.ing.puc.cl";
		String password = "7c38caaee73a5564a3183c0970118725189ef64e9a565c982edb10e4388f43df";
		String user = "neo4j";

		String txUri = "http://" + host + "/db/data/transaction/commit";
		resource2 = Client.create().resource(txUri);
		encodedBytes = Base64.encodeBase64((user + ":" + password).getBytes());
	}

	/**
	 * Encargado de guardar la distinta información en la base de datos
	 * @param s NewsItemData de información con estructura [título, fecha, bajada, url, tags, entre otros]
	 */
	public int saveInDataBase(NewsItemData data){
		//Creamos el nodo de la noticia
		String newsItemCreatorPrefix = "CREATE (n:" + newsItemNodeLabel + " { ";
		String newsItemCreatorSuffix = " }) RETURN ID(n)";
		String newsItemCreatorBody = "";

		for(Entry<String, String> entry : data.getSetFields().entrySet()){
			newsItemCreatorBody += entry.getKey() + ":\'" + entry.getValue() + "\',";
		}
		newsItemCreatorBody = newsItemCreatorBody.substring(0, newsItemCreatorBody.length() - 1);
		String newsItemCreatorString = newsItemCreatorPrefix + newsItemCreatorBody + newsItemCreatorSuffix;

		JsonObject inner = new JsonObject();
		inner.addProperty("statement", newsItemCreatorString);
		JsonArray arr = new JsonArray();
		arr.add(inner);
		JsonObject outer = new JsonObject();
		outer.add("statements", arr);

		ClientResponse response2 = getClientResponse(resource2, outer, encodedBytes);
		String dataNewsItem = response2.getEntity(String.class);
		int newsItemId = getIdFromJsonResult(dataNewsItem);
		response2.close();
		if(data.getTags() != null && !"null".equals(data.getTags())){
			String[] sepTags = data.getTags().split(",");
			Tag[] tags = new Tag[sepTags.length];
			for(int i = 0; i < tags.length; i++)
				tags[i] = new Tag(sepTags[i], DataSetType.Tag);
			saveNewsItemTags(tags, newsItemId);
		}
		System.out.println("Saved news id: " + newsItemId);
		saveProvider(data, newsItemId);
		return newsItemId;
	}

	public void saveProvider(NewsItemData news, int newsId){
		String providerCreator = "{\"statements\" : [ {\"statement\" : \"" +
				"MERGE (n:NewsProvider { "
				+ nameColumn + "  : '" + news.getSource().trim() + "' }) RETURN ID(n)" +
				"\"} ]}";

		ClientResponse responseTag = getClientResponse(resource2, providerCreator, encodedBytes);
		String tagItem = responseTag.getEntity(String.class);
		int auxSourceId = getIdFromJsonResult(tagItem);
		responseTag.close();

		String relationCreator = String.format("{\"statements\" : [ {\"statement\" : \""
				+ "MATCH (a:NewsItem),(b:NewsProvider) "
				+ "WHERE ID(a) = %d AND ID(b) = %d "
				+ "CREATE UNIQUE (b)-[r:`posted`]->(a)\"} ]}", newsId, auxSourceId);

		ClientResponse relationTag = getClientResponse(resource2, relationCreator, encodedBytes);
		//System.out.println("Saved provider id: " + auxSourceId);
		relationTag.close();
	}

	public void saveNewsItemTags(Tag[] data, int id){
		if(data != null){
			for (int i = 0; i < data.length; i++) {
				String tagsCreator = "{\"statements\" : [ {\"statement\" : \"" +
						"MERGE (n:"
						+ data[i].getDataSet().toString() + " { "
						+ nameColumn + "  : '" + data[i].getContent().trim() + "' }) RETURN ID(n)" +
						"\"} ]}";

				ClientResponse responseTag = getClientResponse(resource2, tagsCreator, encodedBytes);
				String tagItem = responseTag.getEntity(String.class);
				int auxTagId = getIdFromJsonResult(tagItem);
				responseTag.close();
				//System.out.println("Saved tag id: " + auxTagId);
				String relationCreator = String.format("{\"statements\" : [ {\"statement\" : \""
						+ "MATCH (a:NewsItem),(b:%s) "
						+ "WHERE ID(a) = %d AND ID(b) = %d "
						+ "CREATE UNIQUE (b)-[r:`has`]->(a)\"} ]}", data[i].getDataSet().toString(), id, auxTagId);

				ClientResponse relationTag = getClientResponse(resource2, relationCreator, encodedBytes);
				relationTag.close();
			}
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

	private ClientResponse getClientResponse(WebResource resource, JsonObject entity, byte[] bytes){
		return resource
		        .accept( MediaType.APPLICATION_JSON)
		        .type( MediaType.APPLICATION_JSON)
		        .entity(entity.toString())
		        .header("Authorization", "Basic " + new String(bytes))
		        .post(ClientResponse.class);
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
