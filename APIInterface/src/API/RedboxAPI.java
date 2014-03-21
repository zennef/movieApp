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





import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.jayway.restassured.RestAssured.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


 
public class RedboxAPI extends API_Top {
	
	final static String JSON = "application/json";
 
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
 
	// HTTP GET request
	private void sendGet() throws Exception {
 
		String url = " https://api.redbox.com/v3/products?apiKey=" + this.apiKey + "&pageNum=3&pageSize=2";
 
		
		HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
				  .header("accept", "application/json")
				  .asJson();
		
		JsonArray array = (JsonArray) jsonResponse.getBody();
		
		for (JsonObject result : array.getValuesAs(JsonObject.class)) {
			System.out.println(result.get("Products"));
			}



		

		// if you need any parameters
//		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//		urlParameters.add(new BasicNameValuePair("paramName", "paramValue"));
//		post.setEntity(new UrlEncodedFormEntity(urlParameters));

//		HttpResponse response = client.execute(post);
//		
//		try (InputStream is = (response.getEntity().getContent());
//				   JsonReader rdr = Json.createReader(is)) {
//				
//					
//					JsonObject object = rdr.readObject();
//					JsonArray array = object.getJsonArray("Products");
//					System.out.println(object.toString());
//					
//					for (JsonObject result : array.getValuesAs(JsonObject.class)) {
//						System.out.println(result.get("Movie"));
//						}
////					System.out.println(rdr.readArray());
//				  
//		}

//		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//		StringBuffer result = new StringBuffer();
//		String line = "";
//		while ((line = rd.readLine()) != null) {
//		    result.append(line);
//		}
//
//		JsonObject o = new JsonObject(result.toString());
		
		
	    //String url = "https://www.googleapis.com/youtube/v3/search?part=id&maxResults=1&q=Gravitytrailer&type=trailer&key=AIzaSyCcE9Fjlo5d-4gpRJkZ97a7KFB2k8pvcaM";

//		URL obj = new URL(url);
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
// 
//		// optional default is GET
//		con.setRequestMethod("GET");
// 
//		//add request header
//		con.setRequestProperty("Accept", JSON);
		
		
//		try (InputStream is = con.getInputStream();
//				   JsonReader rdr = Json.createReader(is)) {
//				
//				   JsonObject object = rdr.readObject();
//				   JsonArray results = object.getJsonArray("P");
//		}
		
//		try (InputStream is = con.getInputStream();
//				   JsonReader rdr = Json.createReader(is)) {
//				
//					
//					JsonObject object = rdr.readObject();
//					JsonArray array = object.getJsonArray("Movie");
//					System.out.println(object.toString());
//					
//					for (JsonObject result : array.getValuesAs(JsonObject.class)) {
//						System.out.println(result.get("Title"));
//						}
////					System.out.println(rdr.readArray());
//				  
//		}
 
		
//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		String inputLine;
//		String response = new String();
// 
//		while ((inputLine = in.readLine()) != null) {
//			response += (inputLine);
//		}
//		in.close();
		
//		ObjectMapper mapper = new ObjectMapper();
//		
//		//Map<String, Object> map = mapper.readTree(response);
//		JsonNode map = mapper.readTree(con.getInputStream());
//		JsonParser parser;
//		JsonNode map1 = map.get("Products");
//		System.out.println("Movie" + ": " + map1.asText());
		

//		for (String key : map.keySet())
//		{
//			System.out.println(key + ": " + map.get(key));
//		}
//		LinkedHashMap list = (LinkedHashMap)map.get("Products");
//		
//		for (Object item : list)
//		{
//			Map<String, Object> innerMap = (Map<String, Object>)item;
//			for (String key : innerMap.keySet())
//			{
//				System.out.println(key + ": " + innerMap.get(key));
//			}
//		}

		
		//JSONArray json = new JSONArray();
//		InputStream is = con.getInputStream();
		
		//JSONArray objects = JSONArray.parse(is);
//		
//		System.out.println("response: " + response);
//		JSONObject array = JSONObject.parse(response);
		//JSONObject object2 = JSONObject.parse(response);
		
//		for(int i = 0; i < array.size(); i++){
//			System.out.println(array.get(i));
//		}
		
//		try (InputStream is = con.getInputStream();
//				   JsonReader rdr = Json.createReader(is)) {
//				
//				   JsonObject object = rdr.readObject();
//				   JsonArray results = object.getJsonArray("Products");
//				   
//				   for (JsonObject result : results.getValuesAs(JsonObject.class)) {
//						 System.out.println("Title: " + result.getJsonObject("Movie").getString("Title"));
//						}
//		}
//		JSONObject array2 = JSONObject.parse(array.get("Products").toString());
//		HashMap<String, String> values = array2.v
//		
//		System.out.println("Title: " + values.get("Title"));
		
		
//				   for (JsonObject result : objects.) {
//						 System.out.println("Title: " + result.getJsonObject("Movie").get("Title"));
//						//this.movieInfo.put("trailerHtml", result.getJsonObject("player").get("embedHtml"));
//						}
//		}

	}
	
	public static void main(String args[]) throws Exception{
		RedboxAPI object = new RedboxAPI();
		object.sendGet();
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
