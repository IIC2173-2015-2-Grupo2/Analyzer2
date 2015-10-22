package Tagger;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Feed {
	//query
	//curl --data "ajax=1&htd=20141111&pn=p1&htv=l" http://www.google.com/trends/hottrends/hotItems
	
	/*
	 * Makes the post request to google
	 * 
	 * */
	public static String getGoogleTrends(int year,int month, int day) throws IOException{
		
		String urlParameters  = "ajax=1&htd=" + year + month + day + "&pn=p38&htv=l";
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
		conn.setUseCaches( false );
		try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
		   wr.write( postData );
		}
		
		StringBuilder sb = new StringBuilder();
		
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        for ( int c = in.read(); c != -1; c = in.read() )
        	sb.append(c);
        
        return sb.toString();
	}
	
	/*
	 * 
	 * Parses the response from Google and adds the Tags Lists to the
	 * HashMap
	 * 
	 */
	public static void parseJSON(String response){
		
	}
	
	
}
