package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.DbController;
import model.UserModel;
import utilities.AppUtilities;


@WebServlet(asyncSupported = true, urlPatterns = {AppUtilities.REGISTER_SERVLET})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DbController dbController = new DbController();
       

    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter(AppUtilities.FIRST_NAME);
		String lastName = request.getParameter(AppUtilities.LAST_NAME);
		String userName = request.getParameter(AppUtilities.USER_NAME);
		String email = request.getParameter(AppUtilities.EMAIL);
		String contact = request.getParameter(AppUtilities.CONTACT);
		String address = request.getParameter(AppUtilities.ADDRESS);
		String password = request.getParameter(AppUtilities.PASSWORD);
		String retypePassword = request.getParameter(AppUtilities.RETYPE_PASSWORD);
		String role = "user";


		UserModel userModel = new UserModel(firstName, lastName, userName, email, contact, address, password, role);
		
		

		int result = dbController.addUser(userModel);

		
		if (password.equals(retypePassword)) {
			switch (result) {
				case 1 -> {
					request.setAttribute(AppUtilities.SUCCESS_MESSAGE, AppUtilities.REGISTER_SUCCESS_MESSAGE);
				    response.sendRedirect(request.getContextPath() + AppUtilities.LOGIN_PAGE);
				    break;
				}
				case 0 -> {
					request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.REGISTER_ERROR_MESSAGE);
				    request.getRequestDispatcher(AppUtilities.REGISTER_PAGE).forward(request, response);
				    break;
				}
				case -1 -> {
					request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.SERVER_ERROR_MESSAGE);
				    request.getRequestDispatcher(AppUtilities.REGISTER_PAGE).forward(request, response);
				    break;
				}
				case -2 -> {
					request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.USERNAME_EXIST_ERROR_MESSAGE);
				    request.getRequestDispatcher(AppUtilities.REGISTER_PAGE).forward(request, response);
				    break;
				}
				case -3 -> {
					request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.EMAIL_EXIST_ERROR_MESSAGE);
				    request.getRequestDispatcher(AppUtilities.REGISTER_PAGE).forward(request, response);
				    break;
				}
				case -4 -> {
					request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.PHONE_NUMBER_EXIST_ERROR_MESSAGE);
				    request.getRequestDispatcher(AppUtilities.REGISTER_PAGE).forward(request, response);
				    break;
				}
				default -> {
					request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.SERVER_ERROR_MESSAGE);
				    request.getRequestDispatcher(AppUtilities.REGISTER_PAGE).forward(request, response);
				    break;
				}
			}
		}
		else {
			request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.PASSWORD_MISMATCH_ERROR_MESSAGE);
		    request.getRequestDispatcher(AppUtilities.REGISTER_PAGE).forward(request, response);
		}
	}
}