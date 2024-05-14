package controller.servlets;

import java.io.File;
import java.io.IOException;
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


@WebServlet(asyncSupported = true, urlPatterns = {AppUtilities.ADD_PRODUCT_SERVLET})
@MultipartConfig(
		   fileSizeThreshold = 2097152,
		   maxFileSize = 10485760L,
		   maxRequestSize = 52428800L
		)

public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	DbController dbController = new DbController();
	
    public AddProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productName = request.getParameter(AppUtilities.PRODUCT_NAME);
		double productPrice = Double.parseDouble(request.getParameter(AppUtilities.PRODUCT_PRICE));
		int productQuantity = Integer.parseInt(request.getParameter(AppUtilities.PRODUCT_QUANTITY));
		Part productImage = request.getPart(AppUtilities.PRODUCT_IMAGE);
		String productDescription = request.getParameter(AppUtilities.PRODUCT_DESCRIPTION);
		
		
		ProductModel productModel = new ProductModel(productName, productPrice, productQuantity, productImage, productDescription);
		
		String savePath = AppUtilities.IMG_DIR;
		System.out.println("Save Path: " + savePath);
		
        String fileName = productModel.getProductImage();
        
        File file = new File(savePath, fileName);
        System.out.println("File Exists: " + file.exists());
        if(!fileName.isEmpty() && fileName != null)
      	productImage.write(savePath + fileName);
		
		int productResult = dbController.addProduct(productModel);
		
		if (productResult > 0) {
	        // Product added successfully
			request.setAttribute(AppUtilities.SUCCESS_MESSAGE, AppUtilities.ADDPRODUCT_SUCCESS_MESSAGE);
			response.sendRedirect(request.getContextPath() + AppUtilities.ADD_PRODUCT_SERVLET);
	    } else {
	        // Error adding product
	        request.setAttribute(AppUtilities.ERROR_MESSAGE, AppUtilities.ADDPRODUCT_ERROR_MESSAGE);
	        request.getRequestDispatcher(AppUtilities.ADD_PRODUCT_SERVLET).forward(request, response);
	    }
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(AppUtilities.ADD_PRODUCT_FORM_PAGE).forward(request, response);
    }
}

