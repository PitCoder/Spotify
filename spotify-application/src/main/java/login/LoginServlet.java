package login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		ServletContext servletContext=request.getServletContext();
	    servletContext.log("Starting LoginServlet");
	    System.out.println("System.out: LoginServlet");
		
	    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		
		servletContext.log("Ending LoginServlet");
	    System.out.println("System.out: Ending LoginServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext=request.getServletContext();
	    servletContext.log("Starting LoginServlet");
	    System.out.println("System.out: Starting LoginServlet");
	    
		LoginService userLoginValidation = new LoginService();
		String userId = request.getParameter("userId").trim();
		String password = request.getParameter("password").trim();
		
		if(userLoginValidation.isUserValid(userId, password)) {
			request.getSession().setAttribute("userId", userId);
			request.getSession().setAttribute("userName", userLoginValidation.getUserName());
			if(userLoginValidation.getRole().equals("A")) {
				response.sendRedirect("/adminDashboard.do");
			}
			else {
				response.sendRedirect("/userDashboard.do");
			}
		}
		else {
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
		
		servletContext.log("Ending LoginServlet");
	    System.out.println("System.out: Ending LoginServlet");
	}
}
