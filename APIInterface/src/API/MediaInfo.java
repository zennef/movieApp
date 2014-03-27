package API;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.json.JSONException;

public class MediaInfo {
	private ItunesAPI ituneApi;
	private YouTubeAPI youTubeApi;
	private RedboxAPI redBoxApi;
	private HashMap<String, String> movieInfo;
	
	public MediaInfo(){
		ituneApi = new ItunesAPI();
		youTubeApi = new YouTubeAPI();
		movieInfo = new HashMap<String, String>();
		redBoxApi = new RedboxAPI();
	}
	
	private void getItuneInfo(String media) throws IOException{
		JsonArray results = ituneApi.searchMedia(media);
		
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			this.movieInfo.put("movieName", result.getString("trackName"));
			this.movieInfo.put("itunePrice", result.get("trackPrice").toString());
			this.movieInfo.put("poster", result.getString("artworkUrl100"));
		}
	}
	
	private void getRedboxInfo(String media){
			try {
				redBoxApi.setMovieObject(media);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.movieInfo.put("redBoxTitle", redBoxApi.getMovieTitle());
			this.movieInfo.put("redBoxDirector", redBoxApi.getMovieDirector());
	}
	
	private void getYouTubeInfo(String media) throws IOException{
		JsonArray results = youTubeApi.searchTrailer(media);
		
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
		this.movieInfo.put("trailerId", result.getJsonObject("id").getString("videoId"));
		//this.movieInfo.put("trailerHtml", result.getJsonObject("player").get("embedHtml"));
		}
	}
	
	public HashMap<String, String> getMovieInfo(String media) throws IOException{
		//String movie = media.replace(' ', '+');
		this.getItuneInfo(media);
		this.getYouTubeInfo(media);
		this.getRedboxInfo(media);
		
		return this.movieInfo;
	}
	
	//public static List<Movie> getTopMovies(){
		
	//}
	

}
