package API;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class Itunes
 */
@WebServlet("/Itunes")
public class Itunes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Itunes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String lastName = request.getParameter("actorLast");
		String firstName = request.getParameter("actorFirst");
		 URL url = new URL("https://itunes.apple.com/search?term=" + firstName + "+" + lastName + "&media=movie&entity=movie&limit=1");
		  try (InputStream is = url.openStream();
		      JsonReader rdr = Json.createReader(is)) {
		 
		      JsonObject obj = rdr.readObject();
		      JsonArray results = obj.getJsonArray("results");
		      for (JsonObject result : results.getValuesAs(JsonObject.class)) {
		    	  
		    	  request.getSession().setAttribute("trackName", result.getString("trackName"));
		    	  request.getSession().setAttribute("trackPrice", result.getJsonNumber("trackPrice"));
		    	  request.getSession().setAttribute("image", result.getString("artworkUrl100"));
		    	  
//		    	  response.getWriter().write("Movie: " + result.getString("trackName"));
//		    	  response.getWriter().write("Price: " + result.getString("trackPrice"));
		    	  
		     }
		 }
		  
		  response.sendRedirect("http://ec2-54-209-114-1.compute-1.amazonaws.com:8080/APIInterface/APIResults.jsp");
	}

}
