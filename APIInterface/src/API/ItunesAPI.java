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

		public ItunesAPI(){

		}
		
		public JsonArray searchMedia(String mediaName) throws IOException{
			 URL url = new URL("https://itunes.apple.com/search?term=" + this.parseMediaQuery(mediaName) + "&media=movie&entity=movie&limit=1");
			  try (InputStream is = url.openStream();
			      JsonReader rdr = Json.createReader(is)) {
			 
			      JsonObject obj = rdr.readObject();
			      JsonArray results = obj.getJsonArray("results");
			      return results;
			  } 
		}
		
		public JsonArray searchTopMovies() throws IOException{
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
				JsonArray topMovies = media.searchTopMovies();
				for (JsonObject result : topMovies.getValuesAs(JsonObject.class)) {
					 System.out.println("Movie: " + result.getJsonObject("im:name").getString("label"));
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
