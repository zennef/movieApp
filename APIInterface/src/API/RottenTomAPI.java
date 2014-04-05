package API;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class RottenTomAPI extends API_Top {	
	private JsonObject movieObject;
	
	public RottenTomAPI(){
			
			Properties prop = new Properties();
			InputStream input = null;
			
			try {
				// load a properties file 
				prop.load(getClass().getResourceAsStream("Api_keys.properties"));			 
				this.apiKey = prop.getProperty("rottenTomKey");
		 
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
	
	public boolean setMediaObject(String movieName, String director) throws IOException{
		 URL url = new URL("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + this.apiKey + "&q=" + this.parseMediaQuery(movieName) + "&page_limit=5");
		 InputStream is = url.openStream();
		 JsonReader rdr = Json.createReader(is);
		 
		 JsonObject obj = rdr.readObject();
		 JsonArray results = obj.getJsonArray("movies");
		 
		 for (JsonObject result : results.getValuesAs(JsonObject.class)){
			 //System.out.println("id: " + result.getString("id"));
			 URL url2 = new URL("http://api.rottentomatoes.com/api/public/v1.0/movies/" + result.getString("id") + ".json?apikey=u89xcs2pkzy49hwmf9f43j8y");
			 is = url2.openStream();
			 rdr = Json.createReader(is);
			 obj = rdr.readObject();
			// System.out.println("title: " + obj.getString("title"));
			 if (movieName.equals(obj.getString("title"))){
				 JsonArray innerResults = obj.getJsonArray("abridged_directors");
				 for (JsonObject dirObj : innerResults.getValuesAs(JsonObject.class)){
					 //System.out.println("director: " + dirObj.getString("name"));
					 if (director.equals(dirObj.getString("name"))){
						 this.movieObject = obj.getJsonObject("ratings");	 
						 return true;
					 }
				 }
			 }
			 
		 }	
		 return false;
		  
	}
	
    /*"critics_rating": "Certified Fresh",
    "critics_score": 89,
    "audience_rating": "Upright",
    "audience_score": 89*/
	public String getCriticRating(){
		if (this.movieObject != null){
			return this.movieObject.getString("critics_rating");
		}
		return"";
	}
	
	public String getCriticScore(){
		if (this.movieObject != null){
			return this.movieObject.getJsonNumber("critics_score").toString();
		}
		return "";
	}
	
	public String getAudienceRating(){
		if (this.movieObject != null){
			return this.movieObject.getString("audience_rating");
		}
		return "";
	}
	
	public String getAudienceScore(){
		if (this.movieObject != null){
			return this.movieObject.getJsonNumber("audience_score").toString();
		}
		return "";
	}

	public static void main(String args[]){
		RottenTomAPI media = new RottenTomAPI();
		
		try {

			media.setMediaObject("Gravity", "Alfonso Cuarón");
			
			System.out.println("Critic Rating: " + media.getCriticRating());
			System.out.println("Critic Score: " + media.getCriticScore());
			System.out.println("Audience Rating: " + media.getAudienceRating());
			System.out.println("Audience Score: " + media.getAudienceScore());
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
