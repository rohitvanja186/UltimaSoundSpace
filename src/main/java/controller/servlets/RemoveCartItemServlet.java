package controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.DbController;
import utilities.AppUtilities;

/**
 * Servlet implementation class RemoveCartItemServlet
 */
@WebServlet("/RemoveCartItemServlet")
public class RemoveCartItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DbController dbController = new DbController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deleteId = request.getParameter("deleteId");

        if (deleteId != null && !deleteId.isEmpty()) {
			doDelete(request, response);
		}
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deleteId = request.getParameter("deleteId");
        if (deleteId != null && !deleteId.isEmpty()) {
            try {
                int cartId = Integer.parseInt(deleteId);
                if (dbController.removeCartItem(cartId)) {
                    request.setAttribute("successMessage", "Item removed from cart successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to remove item from cart.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid cart ID format.");
            } catch (ClassNotFoundException e) {
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            request.getRequestDispatcher(AppUtilities.ADD_TO_CART_SERVLET).forward(request, response);
        } else {
            request.setAttribute("errorMessage", "No cart ID provided.");
            request.getRequestDispatcher(AppUtilities.ADD_TO_CART_SERVLET).forward(request, response);
        }
    }

}
