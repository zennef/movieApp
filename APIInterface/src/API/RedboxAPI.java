package API;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


 
public class RedboxAPI extends API_Top {
	
	final static String JSON = "application/json";
	private JSONObject movieObject;
	
	public RedboxAPI(String movieName){
		
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
		
		try {
			this.movieObject = this.getMovieArray(movieName);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
 
	// HTTP GET request
	public JSONObject getMovieArray(String movieName) throws Exception {
 
		String url = " https://api.redbox.com/v3/products?apiKey=" + this.apiKey + "&pageNum=1&pageSize=2&q=" + this.parseMediaQuery(movieName) + "&operator=Contains";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("Accept", JSON);
		
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (String line = null; (line = in.readLine()) != null;) {
		    builder.append(line);
		}
		in.close();
		builder.append(']');
		System.out.println("Builder: " + builder);
		
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONArray finalResult = new JSONArray(tokener);
		
		JSONObject movieInfo = finalResult.getJSONObject(0).getJSONObject("Products").getJSONArray("Movie").getJSONObject(0);
		
		return movieInfo;
			//System.out.println("START: " + finalResult.getJSONObject(i).getJSONObject("Products").getJSONArray("Movie").getJSONObject(0).getString("RunningLength") + " :END");
			//System.out.println("START: " + finalResult.getJSONObject(0).getJSONObject("Products").getJSONArray("Movie").getJSONObject(0).getString("Directors") +" :END");

		

	}
	
	public String getMovieTitle() throws JSONException{
		return this.movieObject.getString("Title");
	}
	
	public String getMovieDirector() throws JSONException{
		return this.movieObject.getJSONObject("Directors").getString("Person");
	}
	
	//public JSONObject getMovieTitle
	
	public static void main(String args[]) throws Exception{
		RedboxAPI object = new RedboxAPI("Thor");
		System.out.println("Movie Title: " + object.getMovieTitle());
		System.out.println("Movie Director: " + object.getMovieDirector());
	}
 
	// HTTP POST request
	private void sendPost() throws Exception {
 
		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
 
}
