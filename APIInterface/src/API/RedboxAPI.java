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
 
	
	public String getMovieTitle() {
		if (movieObject != null){
			for (JsonObject result : this.movieObject.getValuesAs(JsonObject.class)){
				return result.getString("Title");
			}
			return "";
		}
		else{
			return "No media set";
		}
	}
	
	public String getMovieDirector() {
		if (movieObject != null){
			for (JsonObject result : this.movieObject.getValuesAs(JsonObject.class)){
				return result.getJsonObject("Directors").getString("Person");
			}
			return "";
		}
		else{
			return "No media set";
		}
	}
	
	public String getMoviePoster(){
		if (movieObject != null){
			for (JsonObject result : this.movieObject.getValuesAs(JsonObject.class)){
				return result.getJsonObject("BoxArtImages").getJsonArray("link").getJsonObject(1).getString("@href");
			}
			return "";
		}
		else{
			return "No media set";
		}
	}
	
	public JsonArray getTopMovies() throws IOException {
		
		
		String url = "https://api.redbox.com/v3/products/movies/top20?period=30&apiKey=" + this.apiKey;

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
			      JsonObject result = obj.getJsonObject("Top20");
			      System.out.println(result);
			      JsonArray results = result.getJsonArray("Item");
			      return results;
			  }
	}
	
	//public JSONObject getMovieTitle
	
	public static void main(String args[]) throws Exception{
		RedboxAPI object = new RedboxAPI();
		
		try {
			object.setMovieObject("Gravity");
			System.out.println("Title: " + object.getMovieTitle());
			System.out.println("Director: " + object.getMovieDirector());
			System.out.println("Movie Poster: " + object.getMoviePoster());
//			JsonArray topMovies = object.getTopMovies();
//			for (JsonObject result : topMovies.getValuesAs(JsonObject.class)){
//				System.out.println("Movie: " + result.getString("Title"));
//			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Movie Title: " + object.getMovieTitle());
//		System.out.println("Movie Director: " + object.getMovieDirector());
	}
 
 
}
