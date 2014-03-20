package API;

import java.io.IOException;
import java.util.HashMap;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class MediaInfo {
	private ItunesAPI ituneApi;
	private YouTubeAPI youTubeApi;
	private HashMap<String, JsonValue> movieInfo;
	
	public MediaInfo(){
		ituneApi = new ItunesAPI();
		youTubeApi = new YouTubeAPI();
		movieInfo = new HashMap<String, JsonValue>();
	}
	
	private void getItuneInfo(String media) throws IOException{
		JsonArray results = ituneApi.searchMedia(media);
		
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			this.movieInfo.put("movieName", result.get("trackName"));
			this.movieInfo.put("itunePrice", result.get("trackPrice"));
			this.movieInfo.put("poster", result.get("artworkUrl100"));
		}
	}
	
	private void getYouTubeInfo(String media) throws IOException{
		JsonArray results = youTubeApi.searchTrailer(media);
		
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
		this.movieInfo.put("trailerId", result.getJsonObject("id").get("videoId"));
		//this.movieInfo.put("trailerHtml", result.getJsonObject("player").get("embedHtml"));
		}
	}
	
	public HashMap<String, JsonValue> getMovieInfo(String media) throws IOException{
		String movie = media.replace(' ', '+');
		this.getItuneInfo(movie);
		this.getYouTubeInfo(movie);
		
		return this.movieInfo;
	}
	

}
