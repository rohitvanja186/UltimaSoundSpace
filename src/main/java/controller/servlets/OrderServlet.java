package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.DbController;
import model.OrderModel;
import utilities.AppUtilities;

@WebServlet(asyncSupported = true, urlPatterns = {AppUtilities.ORDER_SERVLET})
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DbController dbController = new DbController();

    public OrderServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_email") == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        String userEmail = (String) session.getAttribute("user_email");
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String orderStatus = "pending";

            int userId = dbController.getUserIdByEmail(userEmail);

            // Create and populate the OrderModel
            OrderModel order = new OrderModel();
            order.setUserId(userId);
            order.setProductId(productId);
            order.setTotalAmount(totalAmount);
            order.setQuantity(quantity);
            order.setOrderStatus(orderStatus);

            // Assume method to place order now accepts an OrderModel object
            boolean orderPlaced = dbController.placeOrder(order);

            if (orderPlaced) {
                request.setAttribute("message", "Order placed successfully.");
                request.getRequestDispatcher("/DisplayProductServlet").forward(request, response);
            } else {
                throw new Exception("Failed to place the order due to unknown error.");
            }
        } catch (NumberFormatException | IOException | ServletException e) {
            e.printStackTrace();
            request.setAttribute("message", "Invalid input for product ID, total amount, or quantity.");
            request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Failed to place order: " + e.getMessage());
            request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
        }
    }
}
