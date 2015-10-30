package tagger;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.*;

public class Feed {
	//query
	//curl --data "ajax=1&htd=20141111&pn=p1&htv=l" http://www.google.com/trends/hottrends/hotItems
	
	/*
	 * Makes the post request to google
	 * 
	 * */
	

	
	
	public static String getGoogleTrends(int iyear,int imonth, int iday,int location) throws IOException{
		
		String year = iyear+"";
		String month = (imonth+"").length() < 2 ? ("0"+ imonth) : ("" + imonth);
		String day = (iday+"").length() < 2 ? ("0"+ iday) : ("" + iday);
		
		String urlParameters  = "ajax=1&htd="+year+month+day+"&pn=p"+location+"&htv=l";
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		String request        = "http://www.google.com/trends/hottrends/hotItems";
		URL    url            = new URL( request );
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
		conn.setDoOutput( true );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "POST" );
		conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
		conn.setRequestProperty( "charset", "utf-8");
		conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		conn.setRequestProperty("Accept", "application/json");
		conn.setUseCaches( false );
		try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
		   wr.write( postData );
		}
		
		StringBuilder sb = new StringBuilder();
		
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
        String line;
        while((line = in.readLine()) != null){
        	sb.append(line);
        }
        /*
        for ( int c = in.read(); c != -1; c = in.read() )
        	sb.append(c);
        	*/
        
        
        
        return sb.toString();
	}

	
public static String getGoogleTopChartsLocation(int iyear, String location) throws IOException{
		
		String year = iyear+"";
		
		String urlParameters  = "ajax=1&geo="+ location + "&date=" + year;
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		String request        = "http://www.google.com/trends/topcharts/category";
		URL    url            = new URL( request );
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
		conn.setDoOutput( true );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "POST" );
		conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
		conn.setRequestProperty( "charset", "utf-8");
		conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		conn.setRequestProperty("Accept", "application/json");
		conn.setUseCaches( false );
		try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
		   wr.write( postData );
		}
		
		StringBuilder sb = new StringBuilder();
		
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
        String line;
        while((line = in.readLine()) != null){
        	sb.append(line);
        }
        /*
        for ( int c = in.read(); c != -1; c = in.read() )
        	sb.append(c);
        	*/
        
        
        
        return sb.toString();
	}
	
	
public static String getGoogleTopCharts(int iyear,int imonth) throws IOException{
		
		String year = iyear+"";
		String month = (imonth+"").length() < 2 ? ("0"+ imonth) : ("" + imonth);
		
		String urlParameters  = "ajax=1&geo=US&date=" + year + month;
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		String request        = "http://www.google.com/trends/topcharts/category";
		URL    url            = new URL( request );
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
		conn.setDoOutput( true );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "POST" );
		conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
		conn.setRequestProperty( "charset", "utf-8");
		conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		conn.setRequestProperty("Accept", "application/json");
		conn.setUseCaches( false );
		try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
		   wr.write( postData );
		}
		
		StringBuilder sb = new StringBuilder();
		
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
        String line;
        while((line = in.readLine()) != null){
        	sb.append(line);
        }
        /*
        for ( int c = in.read(); c != -1; c = in.read() )
        	sb.append(c);
        	*/
        
        
        
        return sb.toString();
	}
	
	/*
	 * 
	 * Parses the response from Google and adds the Tags Lists to the
	 * HashMap
	 * 
	 */
	public static ArrayList<String> parseTrendsJSON(String response){
		
		ArrayList<String> keywords = new ArrayList<String>();
		
		JSONObject json = new JSONObject(response);
		
		JSONArray jarray = json.getJSONArray("trendsByDateList");
		
		for(int i = 0 ; i < jarray.length() ; i++){
			JSONArray trendsByDate = ((JSONObject)jarray.get(i)).getJSONArray("trendsList");
			
			for(int j = 0 ; j < trendsByDate.length() ; j++){
				keywords.add(((JSONObject) trendsByDate.get(j)).getString("title")  );
			}
			
		}
		
		return keywords;
		
		
	}
	
	public static ArrayList<String> parseChartsJSON(String response){
		
		ArrayList<String> keywords = new ArrayList<String>();
		
		JSONObject json = new JSONObject(response);
		
		JSONArray chartList = json.getJSONObject("data").getJSONArray("chartList");
		
			
			for(int j = 0 ; j < chartList.length() ; j++){
				JSONObject trendingChart;
				
				if(chartList.getJSONObject(j).has("trendingChart"))
					trendingChart = chartList.getJSONObject(j).getJSONObject("trendingChart");
				else
					trendingChart = chartList.getJSONObject(j).getJSONObject("topChart");
				
				
//				String category = trendingChart.getString("visibleName");
				
				JSONArray entityList = trendingChart.getJSONArray("entityList");
				
				for(int k = 0 ; k < entityList.length() ; k++){
//					String keyword = entityList.getJSONObject(k).getString("title");
					//keywords.add( keyword + " :: " + category );
					//keywords.add(keyword);
				}
				
			}
			
		
		
		return keywords;
	}
	

	
}
