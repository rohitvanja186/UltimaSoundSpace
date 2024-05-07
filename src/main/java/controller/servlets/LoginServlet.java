package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.DbController;
import model.UserModel;
import utilities.AppUtilities;


@WebServlet(asyncSupported = true, urlPatterns = { AppUtilities.LOGIN_SERVLET})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DbController dbController = new DbController();
       
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		int loginResult = dbController.getUserLoginInfo(email, password);
		System.out.println(loginResult);
		
		if (loginResult == 7) {
            HttpSession adminSession = request.getSession();
            adminSession.setAttribute("admin_email", email);
            adminSession.setMaxInactiveInterval(30 * 3);

            Cookie adminCookie = new Cookie("admin_email", email);
            adminCookie.setMaxAge(30 * 60); // 30 minutes
            response.addCookie(adminCookie);

            UserModel admin = null;

            try {
                admin = dbController.getUserDetails(email);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            System.out.println(admin);

            if (admin != null) {
                // Set success message and redirect to admin panel
                request.setAttribute(AppUtilities.SUCCESS_MESSAGE, AppUtilities.ADMIN_LOGIN_SUCCESS_MESSAGE);
                request.getRequestDispatcher(AppUtilities.ADMIN_PAGE).forward(request, response);
            } else {
                // If admin details are not found, handle the error appropriately
                request.setAttribute(AppUtilities.ERROR_MESSAGE, "Admin details not found");
                request.getRequestDispatcher(AppUtilities.LOGIN_PAGE).forward(request, response);
            }
        }
		
		else if (loginResult == 1) {
			HttpSession userSession = request.getSession();
			userSession.setAttribute("user_email", email);
			userSession.setMaxInactiveInterval(30*3);
			
			Cookie userCookie = new Cookie("user_email", email);
			userCookie.setMaxAge(30*60); // 30 minutes
			response.addCookie(userCookie);
			
			UserModel user = null;
			try {
				user = dbController.getUserDetails(email);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
            if (user != null) {
            	// Set success message and redirect to home page
                request.setAttribute(AppUtilities.SUCCESS_MESSAGE, AppUtilities.USER_LOGIN_SUCCESS_MESSAGE);
                request.getRequestDispatcher(AppUtilities.HOME_PAGE).forward(request, response);
            } else {
                // If user details are not found, handle the error appropriately
                request.setAttribute(AppUtilities.ERROR_MESSAGE, "User details not found");
                request.getRequestDispatcher(AppUtilities.LOGIN_PAGE).forward(request, response);
            }
        } else if (loginResult == 0) {
            // Set error message for invalid credentials and forward to login page
            request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.LOGIN_ERROR_MESSAGE);
            request.getRequestDispatcher(AppUtilities.LOGIN_PAGE).forward(request, response);
        } else {
            // Set server error message and forward to login page
            request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.SERVER_ERROR_MESSAGE);
            request.getRequestDispatcher(AppUtilities.LOGIN_PAGE).forward(request, response);
        }
	}

}



