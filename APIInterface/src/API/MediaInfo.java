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
	private RottenTomAPI rottenTomApi;
	private HashMap<String, String> movieInfo;
	
	public MediaInfo(){
		ituneApi = new ItunesAPI();
		youTubeApi = new YouTubeAPI();
		movieInfo = new HashMap<String, String>();
		redBoxApi = new RedboxAPI();
		rottenTomApi = new RottenTomAPI();
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
					
					if (rottenTomApi.setMediaObject(title, director)){
						movie.setAudienceRating(rottenTomApi.getAudienceRating());
						movie.setAudienceScore(rottenTomApi.getAudienceScore());
						movie.setCriticRating(rottenTomApi.getCriticRating());
						movie.setCriticScore(rottenTomApi.getCriticScore());
					}
					
					// make sure the movie is the right one and itune info to it
					if (title.equals(ituneApi.getMediaTitle()) && director.equals(ituneApi.getMediaDirector()) ){
						if (movie.getItunesUrl() == null){
							movie.setItunesUrl(ituneApi.getMediaLink());
						}
						movie.setItunesBuyPrice(ituneApi.getMediaBuyPrice());
						movie.setItunesRentPrice(ituneApi.getMediaRentPrice());
						if(movie.getMPAARating() == null){
							movie.setMPAARating(ituneApi.getMediaMPAARating());
						}
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
					String title = redObject.getString("Title");
					if (title.contains("(Blu-ray)")){ // cut out double prints of same movie
						break;
					}
					Movie newMovie = new Movie();
					newMovie.setTitle(redBoxApi.parseRedBoxTitle(title));
					newMovie.setDirector(redBoxApi.parseAccents(redObject.getJsonObject("Directors").getString("Person")));
					newMovie.setRedBoxUrl(redObject.getString("@websiteUrl"));
					newMovie.setSynopsis(redObject.getString("SynopsisShort"));
					newMovie.setMPAARating(redObject.getString("MPAARating"));
					newMovie.setPosterUrl(redObject.getJsonObject("BoxArtImages").getJsonArray("link").getJsonObject(1).getString("@href"));
					movieList.add(newMovie);
			}
			
			Integer itunesIndex = 0;
			boolean inList; // variable to keep track if movie is in list
			for(JsonObject itunesObject : itunesTopMovies.getValuesAs(JsonObject.class)){
				//System.out.println("itunes Title: " + itunesObject.getJsonObject("im:name").getString("label"));
				
				String itunesTitle = itunesObject.getJsonObject("im:name").getString("label");
				String itunesDirector = ituneApi.parseMovieDirector(itunesObject.getJsonObject("im:artist").getString("label"));
				String itunesLink = itunesObject.getJsonArray("link").getJsonObject(0).getJsonObject("attributes").getString("href");


				
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
					String itunesPoster = itunesObject.getJsonArray("im:image").getJsonObject(2).getString("label");
					newMovie.setPosterUrl(itunesPoster);
					String itunesSynopsis = itunesObject.getJsonObject("summary").getString("label");
					newMovie.setSynopsis(itunesSynopsis);
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
			System.out.println("Movie Rating: " + movie.getMPAARating());
			System.out.println("Critic Rating: " + movie.getCriticRating());
			System.out.println("Critic Score: " + movie.getCriticScore());
			System.out.println("Audience Rating: " + movie.getAudienceRating());
			System.out.println("Audience Score: " + movie.getAudienceScore());
			System.out.println("Synopsis: " + movie.getSynopsis());
			System.out.println();
			
		}
	}

}
