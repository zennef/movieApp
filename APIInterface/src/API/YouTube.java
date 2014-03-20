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

/**
 * Servlet implementation class YouTube
 */
@WebServlet("/YouTube")
public class YouTube extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YouTube() {
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
		
		String previewName = request.getParameter("previewName").replace(' ', '+');
		 URL url = new URL("https://www.googleapis.com/youtube/v3/search?part=id&maxResults=1&q=" + 
				 			previewName + "trailer&type=trailer&key=AIzaSyCcE9Fjlo5d-4gpRJkZ97a7KFB2k8pvcaM");
		  try (InputStream is = url.openStream();
		      JsonReader rdr = Json.createReader(is)) {
		 
		      JsonObject obj = rdr.readObject();
		      JsonArray results = obj.getJsonArray("items");
		      for (JsonObject result : results.getValuesAs(JsonObject.class)) {
		    	  
		    	  request.getSession().setAttribute("videoId", result.getJsonObject("id").getString("videoId"));
		    	 // response.getWriter().write("youtube video: " + result.getJsonObject("id").getString("videoId"));
//		    	  response.getWriter().write("Movie: " + result.getString("trackName"));
//		    	  response.getWriter().write("Price: " + result.getString("trackPrice"));
		    	  
		     }
		 }
		
		response.sendRedirect("http://ec2-54-209-114-1.compute-1.amazonaws.com:8080/APIInterface/YouTubeApiResults.jsp");
	}

}
