package API;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;



import com.jayway.restassured.RestAssured.*;
import com.mashape.unirest.http.HttpResponse;



 
public class RedboxAPI extends API_Top {
	
	final static String JSON = "application/json";
	private JsonArray movieObject;
	
	public RedboxAPI(){
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			// load a properties file 
			prop.load(getClass().getResourceAsStream("Api_keys.properties"));			 
			this.apiKey = prop.getProperty("redboxKey");
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setMovieObject(String movieName) throws Exception {
		String url = " https://api.redbox.com/v3/products?apiKey=" + this.apiKey + "&pageNum=1&pageSize=2&q=" + this.parseMediaQuery(movieName) + "&operator=Contains";

		URL urlObj = new URL(url);
		HttpURLConnection con;
		
			con = (HttpURLConnection) urlObj.openConnection();
		
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("Accept", JSON);
		
		 try (InputStream is = con.getInputStream();
			      JsonReader rdr = Json.createReader(is)) {
			 
			      JsonObject obj = rdr.readObject();
			      JsonObject result = obj.getJsonObject("Products");
			      JsonArray results = result.getJsonArray("Movie");
			      this.movieObject = results;
			  }
	}
 
//	// HTTP GET request
//	public void setMovieObject(String movieName) throws Exception {
// 
//		String url = " https://api.redbox.com/v3/products?apiKey=" + this.apiKey + "&pageNum=1&pageSize=2&q=" + this.parseMediaQuery(movieName) + "&operator=Contains";
//
//		URL obj = new URL(url);
//		HttpURLConnection con;
//		
//			con = (HttpURLConnection) obj.openConnection();
//		
// 
//		// optional default is GET
//		con.setRequestMethod("GET");
// 
//		//add request header
//		con.setRequestProperty("Accept", JSON);
//		
//		
//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		StringBuilder builder = new StringBuilder();
//		builder.append('[');
//		for (String line = null; (line = in.readLine()) != null;) {
//		    builder.append(line);
//		}
//		in.close();
//		builder.append(']');
//		System.out.println("Builder: " + builder);
//		
//		JSONTokener tokener = new JSONTokener(builder.toString());
//		JSONArray finalResult = new JSONArray(tokener);
//		
//		JSONObject movieInfo = new JSONObject();
//		try {
//			movieInfo = finalResult.getJSONObject(0).getJSONObject("Products").getJSONArray("Movie").getJSONObject(0);
//		} catch (JSONException e) {
//			System.out.println("EXCEPTION");
//			e.printStackTrace();
//		}
//		
//		this.movieObject = movieInfo;
//		
//			
//			//System.out.println("START: " + finalResult.getJSONObject(i).getJSONObject("Products").getJSONArray("Movie").getJSONObject(0).getString("RunningLength") + " :END");
//			//System.out.println("START: " + finalResult.getJSONObject(0).getJSONObject("Products").getJSONArray("Movie").getJSONObject(0).getString("Directors") +" :END");	
//	}
	
	public String getMovieTitle() {
		for (JsonObject result : this.movieObject.getValuesAs(JsonObject.class)){
			return result.getString("Title");
		}
		return "";
	}
	
	public String getMovieDirector() {
		for (JsonObject result : this.movieObject.getValuesAs(JsonObject.class)){
			return result.getJsonObject("Directors").getString("Person");
		}
		return "";
		
	}
	
	//public JSONObject getMovieTitle
	
	public static void main(String args[]) throws Exception{
		RedboxAPI object = new RedboxAPI();
		
		try {
			object.setMovieObject("Gravity");
			System.out.println("Title: " + object.getMovieTitle());
			System.out.println("Director: " + object.getMovieDirector());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Movie Title: " + object.getMovieTitle());
//		System.out.println("Movie Director: " + object.getMovieDirector());
	}
 
 
}
