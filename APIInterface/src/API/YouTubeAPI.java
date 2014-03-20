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
import javax.json.JsonValue;
import javax.servlet.ServletContext;

public class YouTubeAPI extends API_Top {
	
	
	public YouTubeAPI(){
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			 
			input = new FileInputStream("src/Api_keys.properties");
	 
			// load a properties file
			prop.load(input);
	 
			this.apiKey = prop.getProperty("youTubeKey");
	 
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
	
	
	public JsonArray searchTrailer(String mediaName) throws IOException{
		
		
	    URL url;
		url = new URL("https://www.googleapis.com/youtube/v3/search?part=id&maxResults=1&q=" + 
			 			mediaName + "trailer&type=trailer&key=" + this.apiKey);

		try (InputStream is = url.openStream();
		   JsonReader rdr = Json.createReader(is)) {
		
		   JsonObject obj = rdr.readObject();
		   JsonArray results = obj.getJsonArray("items");
		   
//		   String id = new String();
//		   for (JsonObject result : results.getValuesAs(JsonObject.class)) {
//				 id = result.getJsonObject("id").getString("videoId");
//				}
				   
		   return results;
		   	
			}
	}

}
