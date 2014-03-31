package API;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.JsonArray;

public class API_Top {
	
	protected URL url;
	protected String apiKey;
	
	protected URL getUrl(){ return url; };
	
	protected void setUrl(URL url){ this.url = url; };
	
	protected String getApiKey(){ return this.apiKey; }
	
	protected String parseMediaQuery(String media){ return media.replace(' ', '+'); }

}
