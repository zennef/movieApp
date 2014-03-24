package API;

import java.io.IOException;
import java.util.HashMap;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.json.JSONException;

public class MediaInfo {
	private ItunesAPI ituneApi;
	private YouTubeAPI youTubeApi;
	private RedboxAPI redBoxApi;
	private String media;
	private HashMap<String, String> movieInfo;
	
	public MediaInfo(String media){
		this.media = media;
		ituneApi = new ItunesAPI();
		youTubeApi = new YouTubeAPI();
		movieInfo = new HashMap<String, String>();
		redBoxApi = new RedboxAPI(media);
	}
	
	private void getItuneInfo(String media) throws IOException{
		JsonArray results = ituneApi.searchMedia(media);
		
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			this.movieInfo.put("movieName", result.getString("trackName"));
			this.movieInfo.put("itunePrice", result.get("trackPrice").toString());
			this.movieInfo.put("poster", result.getString("artworkUrl100"));
		}
	}
	
	private void getRedboxInfo(){
		try {
			this.movieInfo.put("redBoxTitle", redBoxApi.getMovieTitle());
			this.movieInfo.put("redBoxDirector", redBoxApi.getMovieDirector());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getYouTubeInfo(String media) throws IOException{
		JsonArray results = youTubeApi.searchTrailer(media);
		
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
		this.movieInfo.put("trailerId", result.getJsonObject("id").getString("videoId"));
		//this.movieInfo.put("trailerHtml", result.getJsonObject("player").get("embedHtml"));
		}
	}
	
	public HashMap<String, String> getMovieInfo() throws IOException{
		//String movie = media.replace(' ', '+');
		this.getItuneInfo(media);
		this.getYouTubeInfo(media);
		this.getRedboxInfo();
		
		return this.movieInfo;
	}
	

}
