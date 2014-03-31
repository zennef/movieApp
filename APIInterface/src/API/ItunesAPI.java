package API;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ItunesAPI extends API_Top {
		private JsonArray mediaObject;
		public ItunesAPI(){

		}
		
		public void setMediaObject(String mediaName) throws IOException{
			 URL url = new URL("https://itunes.apple.com/search?term=" + this.parseMediaQuery(mediaName) + "&media=movie&entity=movie&limit=1");
			  try (InputStream is = url.openStream();
			      JsonReader rdr = Json.createReader(is)) {
			 
			      JsonObject obj = rdr.readObject();
			      JsonArray results = obj.getJsonArray("results");
			      this.mediaObject = results;
			  } 
		}
		
		public String getMediaTitle(){
			if (mediaObject != null){
				for(JsonObject media : this.mediaObject.getValuesAs(JsonObject.class)){
					return media.getString("trackName");
				}
				return null;
			}
			else{
				return "No media object set";
			}
		}
		
		public String getMediaBuyPrice(){
			if (mediaObject != null){
				for(JsonObject media : this.mediaObject.getValuesAs(JsonObject.class)){
					return media.getJsonNumber("trackPrice").toString();
				}
				return "";
			}
			else{
				return "No media object set";
			}
		}
		
		public String getMediaRentPrice(){
			if (mediaObject != null){
				for(JsonObject media : this.mediaObject.getValuesAs(JsonObject.class)){
					try{
						String rentalPrice = media.getJsonNumber("trackRentalPrice").toString();
						if (rentalPrice != null){
							String[] prices = rentalPrice.split("\\.");
							prices[1] = prices[1].substring(0, 2);
							return prices[0] + '.' + prices[1];
						}
					}
					catch(NullPointerException e){
						return"";
					}
				}
				return "";
			}
			else{
				return "No media object set";
			}
		}
		
		public String getMediaPoster(){
			if (mediaObject != null){
				for(JsonObject media : this.mediaObject.getValuesAs(JsonObject.class)){
					return media.getString("artworkUrl100");
				}
				return null;
			}
			else{
				return "No media object set";
			}
		}
		
		public String getMediaDirector(){
			if (mediaObject != null){
				for(JsonObject media : this.mediaObject.getValuesAs(JsonObject.class)){
					return media.getString("artistName");
				}
				return null;
			}
			else{
				return "No media object set";
			}
		}
		
		public String getMediaLink(){
			if (mediaObject != null){
				for(JsonObject media : this.mediaObject.getValuesAs(JsonObject.class)){
					return media.getString("trackViewUrl");
				}
				return null;
			}
			else{
				return "No media object set";
			}
		}
		
		public JsonArray getTopMovies() throws IOException{
			 URL url = null;
			try {
				url = new URL("https://itunes.apple.com/us/rss/topmovies/genre=33/json");
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
			  try (InputStream is = url.openStream();
			      JsonReader rdr = Json.createReader(is)) {
			 
			      JsonObject obj = rdr.readObject();
			      JsonObject result = obj.getJsonObject("feed");
			      JsonArray results = result.getJsonArray("entry");
			      return results;
			  }
		}
		
		public static void main(String args[]){
			ItunesAPI media = new ItunesAPI();
			
			try {
//				JsonArray topMovies = media.getTopMovies();
//				for (JsonObject result : topMovies.getValuesAs(JsonObject.class)) {
//					 System.out.println("Movie: " + result.getJsonObject("im:name").getString("label"));
//					 System.out.println("Itunes link: " + result.getJsonArray("link").getJsonObject(0).getJsonObject("attributes").getString("href"));
//					}
				media.setMediaObject("Thor");
				System.out.println("Movie Title: " + media.getMediaTitle());
				System.out.println("Movie Director: " + media.getMediaDirector());
				System.out.println("Movie Poster: " + media.getMediaPoster());
				System.out.println("Movie Buy Price: " + media.getMediaBuyPrice());
				System.out.println("Movie Rental Price: " + media.getMediaRentPrice());
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
