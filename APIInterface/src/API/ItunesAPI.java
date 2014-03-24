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
			Properties prop = new Properties();
			InputStream input = null;
			
			try {
				 
				input = new FileInputStream("Api_keys.properties");
		 
				// load a properties file
				prop.load(input);
		 
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
		
		public JsonArray searchMedia(String mediaName) throws IOException{
			 URL url = new URL("https://itunes.apple.com/search?term=" + this.parseMediaQuery(mediaName) + "&media=movie&entity=movie&limit=1");
			  try (InputStream is = url.openStream();
			      JsonReader rdr = Json.createReader(is)) {
			 
			      JsonObject obj = rdr.readObject();
			      JsonArray results = obj.getJsonArray("results");
			      return results;
			  } 
		}
}
