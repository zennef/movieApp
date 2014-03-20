package API;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import java.util.HashMap;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

 
public class RedboxAPI extends API_Top {
	
	final static String JSON = "application/json";
 
	public RedboxAPI(){
			
			Properties prop = new Properties();
			InputStream input = null;
			
			try {
				 
				input = new FileInputStream("src/Api_keys.properties");
		 
				// load a properties file
				prop.load(input);
		 
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
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("Accept", JSON);
 
		int responseCode = con.getResponseCode();
		
//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		String inputLine;
//		String response = new String();
// 
//		while ((inputLine = in.readLine()) != null) {
//			response += (inputLine);
//		}
//		in.close();
		
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
		
		try (InputStream is = con.getInputStream();
				   JsonReader rdr = Json.createReader(is)) {
				
				   JsonObject object = rdr.readObject();
				   JsonArray results = object.getJsonArray("Products");
				   
				   for (JsonObject result : results.getValuesAs(JsonObject.class)) {
						 System.out.println("Title: " + result.getJsonObject("Movie").getString("Title"));
						}
		}
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
