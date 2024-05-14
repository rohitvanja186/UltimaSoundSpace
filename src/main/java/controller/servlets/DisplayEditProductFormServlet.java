package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.DbController;
import model.ProductModel;

@WebServlet("/DisplayEditProductFormServlet")
public class DisplayEditProductFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbController dbController = new DbController();   
  
    public DisplayEditProductFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        if(productIdStr != null) {
            try {
                int productId = Integer.parseInt(productIdStr);
                ProductModel product = dbController.getProductDetailsById(productId);  // Assuming you have such a method
                request.setAttribute("product", product);
                request.getRequestDispatcher("/pages/editProductForm.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("errorPage.jsp");
            }
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}
