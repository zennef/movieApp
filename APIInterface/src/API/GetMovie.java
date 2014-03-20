package API;

import java.io.IOException;
import java.util.HashMap;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetMovie
 */
@WebServlet("/GetMovie")
public class GetMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMovie() {
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
		MediaInfo media = new MediaInfo();
		
		String movieName = request.getParameter("mediaName");
		
		if (movieName != null){
			HashMap<String, JsonValue> movieInfo = media.getMovieInfo(movieName);
			
			
			request.getSession().setAttribute("trailerId", movieInfo.get("trailerId"));
			//request.getSession().setAttribute("trailerHtml", movieInfo.get("trailerHtml"));
			request.getSession().setAttribute("movieName", movieInfo.get("movieName"));
			request.getSession().setAttribute("itunePrice", movieInfo.get("itunePrice"));
			request.getSession().setAttribute("poster", movieInfo.get("poster"));
		}
		response.sendRedirect("http://ec2-54-209-114-1.compute-1.amazonaws.com:8080/APIInterface/APIResults.jsp");
	}

}
