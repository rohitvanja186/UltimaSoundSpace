package controller.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import controller.DbController;
import model.CartModel;
import utilities.AppUtilities;           

@WebServlet("/AddtocartServlet")
public class AddtocartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private DbController dbController = new DbController(); 

    public AddtocartServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user_email");

        if (userEmail == null) {
            response.sendRedirect(AppUtilities.LOGIN_PAGE);
            return;
        }

        try {
            int userId = dbController.getUserIdByEmail(userEmail);
            if (userId == -1) {
                session.setAttribute("cartMessage", "User not found.");
                response.sendRedirect("/pages/home.jsp"); // Consider redirecting if user not found
                return;
            }

            Integer productId = Integer.parseInt(request.getParameter("productId"));
            String quantityStr = request.getParameter("quantity");
            int quantity = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 1;

            int result = dbController.addToCart(userId, productId, quantity);
            if (result == 0) {
                session.setAttribute("cartMessage", "Item successfully added to your cart!");
            } else if (result == 1) {
                session.setAttribute("cartMessage", "Item already exists in your cart.");
            } else {
                session.setAttribute("cartMessage", "An unexpected error occurred.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("cartMessage", "Items deleted successfully!.");
        } catch (Exception e) {
            session.setAttribute("cartMessage", "An unexpected error occurred due to an internal issue.");
            e.printStackTrace();
        }

        // Only forward if everything goes well
        request.getRequestDispatcher("/DisplayProductServlet").forward(request, response);
    }
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user_email");

        // Redirect to the login page if no user is logged in
        if (userEmail == null) {
            response.sendRedirect(AppUtilities.LOGIN_PAGE);
            return;
        }

        try {
            int userId = dbController.getUserIdByEmail(userEmail);
            List<CartModel> cartItems = dbController.getCartItems(userId);
            request.setAttribute("cartItems", cartItems);

            // Forward to the cart page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/cart.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error retrieving cart items: " + e.getMessage());
        }
    }



}
