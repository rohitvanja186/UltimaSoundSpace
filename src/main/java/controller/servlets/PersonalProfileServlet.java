package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.DbController;
import model.UserModel;
import utilities.AppUtilities;

@WebServlet(asyncSupported = true, urlPatterns = {AppUtilities.PROFILE_SERVLET})
public class PersonalProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbController dbController = new DbController();
       
    
    public PersonalProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the session object without creating a new one
        HttpSession session = request.getSession(false);

        // Check if there is a session and it has the user identifier (e.g., "user_email")
        if (session != null && session.getAttribute("user_email") != null) {
            String userEmail = (String) session.getAttribute("user_email");

            try {
                // Fetch the user details using the email stored in the session
                UserModel user = dbController.getUserDetails(userEmail);

                if (user != null) {
                    // Set the user object as an attribute in the request object
                    request.setAttribute("user", user);

                    // Forward the request to the profile.jsp page
                    request.getRequestDispatcher(AppUtilities.PERSONAL_PROFILE_PAGE).forward(request, response);
                } else {
                    // Handle case where no user details are found
                    request.setAttribute(AppUtilities.ERROR_MESSAGE, "User profile not found.");
                    request.getRequestDispatcher(AppUtilities.LOGIN_PAGE).forward(request, response);
                }
            } catch (Exception e) {
                // Log the exception and handle it gracefully
                e.printStackTrace();  // Log to server logs
                request.setAttribute(AppUtilities.ERROR_MESSAGE, "A database error occurred.");
                request.getRequestDispatcher(AppUtilities.LOGIN_PAGE).forward(request, response);
            }
        } else {
            // Redirect to login page if there is no user logged in
            response.sendRedirect(AppUtilities.LOGIN_PAGE);
        }
    }

}
