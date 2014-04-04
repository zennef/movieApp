package API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		ituneApi.setMediaObject(media);
		this.movieInfo.put("movieName", ituneApi.getMediaTitle());
		this.movieInfo.put("itunePrice", ituneApi.getMediaBuyPrice());
		this.movieInfo.put("poster", ituneApi.getMediaPoster());

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
	
	private ArrayList<Movie> finalizeMovieList(ArrayList<Movie> movieList){
		for(Movie movie : movieList){
				try {
					String title = movie.getTitle();
					String director = movie.getDirector();
					ituneApi.setMediaObject(title);
					
					// make sure the movie is the right one
					if (title.equals(ituneApi.getMediaTitle()) && director.equals(ituneApi.getMediaDirector()) ){
						if (movie.getItunesUrl() == null){
							movie.setItunesUrl(ituneApi.getMediaLink());
						}
						movie.setItunesBuyPrice(ituneApi.getMediaBuyPrice());
						movie.setItunesRentPrice(ituneApi.getMediaRentPrice());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return movieList;
	}
	
	public List<Movie> getTopMovies(){
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		try {
			// get redBox top movies
			JsonArray redBoxTopMovies = redBoxApi.getTopMovies();
			
			// get Itunes top movies
			JsonArray itunesTopMovies = ituneApi.getTopMovies();
			
			// add redBox movies as base
			for(JsonObject redObject : redBoxTopMovies.getValuesAs(JsonObject.class)){
					//System.out.println("RedBox Title: " + redObject.getString("Title"));
					Movie newMovie = new Movie();
					newMovie.setTitle(redObject.getString("Title"));
					newMovie.setDirector(redObject.getJsonObject("Directors").getString("Person"));
					newMovie.setRedBoxUrl(redObject.getString("@websiteUrl"));
					newMovie.setPosterUrl(redObject.getJsonObject("BoxArtImages").getJsonArray("link").getJsonObject(1).getString("@href"));
					movieList.add(newMovie);
			}
			
			Integer itunesIndex = 0;
			boolean inList; // variable to keep track if movie is in list
			for(JsonObject itunesObject : itunesTopMovies.getValuesAs(JsonObject.class)){
				//System.out.println("itunes Title: " + itunesObject.getJsonObject("im:name").getString("label"));
				
				String itunesTitle = itunesObject.getJsonObject("im:name").getString("label");
				String itunesDirector = itunesObject.getJsonObject("im:artist").getString("label");
				String itunesLink = itunesObject.getJsonArray("link").getJsonObject(0).getJsonObject("attributes").getString("href");
				String itunesPoster = itunesObject.getJsonArray("im:image").getJsonObject(2).getString("label");
				
				
				inList = false; 
				// find movie in list and add itunes link
				for(Movie movie : movieList){
					if (movie.getDirector().equals(itunesDirector) && movie.getTitle().equals(itunesTitle)){
						inList = true;
						movie.setItunesUrl(itunesLink);
						break;
					}
				}
				// if movie is not yet added add it
				if (!inList){
					itunesIndex += 2;
					Movie newMovie = new Movie();
					newMovie.setTitle(itunesTitle);
					newMovie.setDirector(itunesDirector);
					newMovie.setItunesUrl(itunesLink);
					newMovie.setPosterUrl(itunesPoster);
					movieList.add(itunesIndex, newMovie );
				}
				
		   }
			ArrayList<Movie> finalMovieList = finalizeMovieList(movieList);
			return finalMovieList;
		} catch (IOException e) {
			e.printStackTrace();
			return movieList;
		}
		//return ArrayList<Movie> movies = new ArrayList<Movie>();
	}
	
	public static void main(String args[]){
		MediaInfo media = new MediaInfo();
		ArrayList<Movie> movieList = (ArrayList<Movie>) media.getTopMovies();
		
		for(Movie movie : movieList){
			System.out.println("Movie Title: " + movie.getTitle());
			System.out.println("Movie Director: " + movie.getDirector());
			System.out.println("Movie Poster URL: " + movie.getPosterUrl());
			System.out.println("Redbox Link: " + movie.getRedBoxUrl());
			System.out.println("Itunes Link: " + movie.getItunesUrl());
			System.out.println("Itunes Rent Price: " + movie.getItunesRentPrice());
			System.out.println("Itunes Buy Price: " + movie.getItunesBuyPrice());
			System.out.println();
			
		}
	}

}
