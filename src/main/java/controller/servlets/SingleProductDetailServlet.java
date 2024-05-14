package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.DbController;
import model.ProductModel;
import utilities.AppUtilities;

@WebServlet(asyncSupported = true, urlPatterns = { AppUtilities.SINGLE_PRODUCT_DETAILS_SERVLET })
public class SingleProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("id");
        if (productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                DbController dbController = new DbController();
                ProductModel product = dbController.getProductDetailsById(productId);

                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher(AppUtilities.SINGLE_PRODUCT_DETAILS_PAGE).forward(request, response);
                } else {
                    response.sendRedirect("errorPage.jsp?message=Product Not Found");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("errorPage.jsp?message=Invalid Product ID");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("errorPage.jsp?message=Internal Server Error");
            }
        } else {
            response.sendRedirect("errorPage.jsp?message=No Product ID Provided");
        }
    }

}
