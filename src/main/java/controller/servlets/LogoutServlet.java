package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utilities.AppUtilities;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
        // Auto-generated constructor stub
    }
    
    // Override doGet to call doPost, allowing both GET and POST requests to perform logout
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate any cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_email") || cookie.getName().equals("admin_email")) {
                    cookie.setValue(""); // Clear value
                    cookie.setPath("/"); // Set the path as needed, assuming root path
                    cookie.setMaxAge(0); // Set max age to 0 to delete the cookie
                    cookie.setHttpOnly(true); // Prevent client-side script access
                    response.addCookie(cookie);
                }
            }
        }

        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Set JSESSIONID cookie to empty
	    Cookie jsessionCookie = new Cookie("JSESSIONID", "");
	    jsessionCookie.setMaxAge(0);
	    jsessionCookie.setPath("/");
	    jsessionCookie.setHttpOnly(true);
	    response.addCookie(jsessionCookie);

        // Redirect to landing page or login page
        response.sendRedirect(request.getContextPath() + AppUtilities.LANDING_PAGE); // Ensure this points to a valid path
    }

}	
