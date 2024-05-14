package controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.DbController;
import model.ProductModel;
import utilities.AppUtilities;

/**
 * Servlet implementation class RemoveCartItemServlet
 */
@WebServlet("/ManageProductServlet")
@MultipartConfig(
		   fileSizeThreshold = 2097152,
		   maxFileSize = 10485760L,
		   maxRequestSize = 52428800L
		)
public class ManageProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DbController dbController = new DbController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String updateProductId = request.getParameter("updateProductId");  // Ensure this matches your form's input name
        String deleteProductId = request.getParameter("deleteProductId");  // Ensure this matches your form's input name

        System.out.println("Update Product ID: " + updateProductId);
        System.out.println("Delete Product ID: " + deleteProductId);

        
        try {
            if (updateProductId != null && !updateProductId.isEmpty()) {
                int updateId = Integer.parseInt(updateProductId);
                doPut(request, response, updateId);
            } else if (deleteProductId != null && !deleteProductId.isEmpty()) {
                int deleteId = Integer.parseInt(deleteProductId);
                doDelete(request, response, deleteId);
            } else {
                response.sendRedirect("/pages/message.jsp"); // Redirect to an error page if no valid action is found
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        }
    }

    
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response, int productId)
            throws ServletException, IOException {
        try {
  
            // Assuming you need to update based on productId, hence not getting it again from the request
        	int updateProductId = Integer.parseInt(request.getParameter("updateProductId"));
            String productName = request.getParameter("productName");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int productQuantity = Integer.parseInt(request.getParameter("productQuantity"));
            Part imagePart = request.getPart("productImage"); // Handling file upload
            String productDescription = request.getParameter("productDescription");
            

            ProductModel product = new ProductModel(productName,productPrice, productQuantity, imagePart, productDescription);

            dbController.updateProduct(updateProductId, product);
            
            String savePath = AppUtilities.IMG_DIR;
			String fileName = product.getProductImage();
			
			if (!fileName.isEmpty() && fileName != null) {
				imagePart.write(savePath + fileName);
			}
            response.sendRedirect(request.getContextPath() + AppUtilities.DISPLAY_PRODUCT_ADMIN_SERVLET);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to update product due to invalid input or server error.");
        }
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response, int productId)
    		throws ServletException, IOException {
        String deleteProductId = request.getParameter("deleteProductId");
        if (deleteProductId != null && !deleteProductId.isEmpty()) {
            try {
                int productsId = Integer.parseInt(deleteProductId);
                if (dbController.deleteProduct(productsId)) {
                    request.setAttribute("successMessage", "Product removed from database successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete product from database.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid product ID format.");
            } catch (ClassNotFoundException e) {
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            request.getRequestDispatcher(AppUtilities.DISPLAY_PRODUCT_ADMIN_SERVLET).forward(request, response);
        } else {
            request.setAttribute("errorMessage", "No product ID provided.");
            request.getRequestDispatcher(AppUtilities.DISPLAY_PRODUCT_ADMIN_SERVLET).forward(request, response);
        }
    }

}
