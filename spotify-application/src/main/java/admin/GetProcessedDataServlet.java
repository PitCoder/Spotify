package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import login.LoginService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class GetProcessedDataServlet
 */
@WebServlet(urlPatterns="/GetProcessedDataServlet")
public class GetProcessedDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProcessedDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ArrayList<String>> set = new ArrayList<>();
		ArrayList<String> attribute;
		String data = "";
		
		
		  try {
		    BufferedReader reader = request.getReader();
		    JSONArray jsonArray = new JSONArray(reader.readLine());
		    JSONArray innerJsonArray;
		    
		    
		    for(int i = 0; i < jsonArray.length(); i++) {
		    	attribute = new ArrayList<>();
		    	innerJsonArray = jsonArray.getJSONArray(i);
		    	
		    	for(int j = 0; j < innerJsonArray.length(); j++) {
		    		data = innerJsonArray.getString(j);
		    		attribute.add(data);
		    		System.out.println(data);
		    	}
		    	set.add(attribute);
		    }
		  } 
		  catch (IOException e) { 
			  e.printStackTrace();
		  }
	}
}
