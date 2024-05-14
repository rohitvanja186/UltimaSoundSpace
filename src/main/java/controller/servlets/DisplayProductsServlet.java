package controller.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductModel;
import utilities.AppUtilities;
import controller.DbController;

@WebServlet("/DisplayProductsServlet")
public class DisplayProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DbController dbController = new DbController();  // Assuming DbController can be instantiated like this.

    public DisplayProductsServlet() {
        super();
    }

    // For non-authenticated users (landing page)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<ProductModel> products = this.dbController.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher(AppUtilities.HOME_PAGE).forward(request, response);
    }

    // For authenticated users (home page)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
