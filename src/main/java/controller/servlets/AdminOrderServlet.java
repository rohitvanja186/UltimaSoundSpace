package controller.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.OrderModel;
import controller.DbController;

/**
 * Servlet implementation class AdminOrderServlet
 */
@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DbController dbController = new DbController(); // Ensure DbController is properly implemented

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrderServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<OrderModel> orders = null;
        try {
            orders = dbController.getOrders(); // Fetch orders from database
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve orders: " + e.getMessage());
        }

        request.setAttribute("orders", orders); // Set the fetched orders as a request attribute
        request.getRequestDispatcher("/pages/adminOrders.jsp").forward(request, response); // Forward to JSP page
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Handle POST request via GET
    }

}
