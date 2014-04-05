package API;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.json.JsonArray;

public class API_Top {
	
	protected URL url;
	protected String apiKey;
	
	protected URL getUrl(){ return url; };
	
	protected void setUrl(URL url){ this.url = url; };
	
	protected String getApiKey(){ return this.apiKey; }
	
	protected String parseMediaQuery(String media){ return media.replace(' ', '+'); }
	
	protected String parseAccents(String name){
		 return Normalizer.normalize(name, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	
	public static void main(String args[]){
		API_Top obj = new API_Top();
		System.out.println(obj.parseAccents("Alfonso Cuarón"));
		
	}
	
}

