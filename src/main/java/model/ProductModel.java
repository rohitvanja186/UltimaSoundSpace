package model;

import java.io.File;

import javax.servlet.http.Part;

import utilities.AppUtilities;

public class ProductModel {
	
	//product main details attributes
	private int productId;
	private String productName;
	private double productPrice;
	private int productQuantity;
	private String productImage;
	private String productDescription;
	
	// Constructor
    public ProductModel(String productName, double productPrice, int productQuantity, Part productImagePart,
                        String productDescription) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImage = getImageUrl(productImagePart);
        this.productDescription = productDescription;
    }
    
    public ProductModel() {
    	
    }

    // Main details getter and setter methods
    public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
    
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	
	
	private String getImageUrl(Part part) {
	      String savePath = AppUtilities.ADD_PRODUCT;
	      File fileSaveDir = new File(savePath);
	      String imageUrlFromPart = null;
	      if (!fileSaveDir.exists()) {
	         fileSaveDir.mkdir();
	      }

	      String contentDisp = part.getHeader("content-disposition");
	      String[] items = contentDisp.split(";");
	      String[] var10 = items;
	      int var9 = items.length;

	      for(int var8 = 0; var8 < var9; ++var8) {
	         String s = var10[var8];
	         if (s.trim().startsWith("filename")) {
	            imageUrlFromPart = s.substring(s.indexOf("=") + 2, s.length() - 1);
	         }
	      }

	      if (imageUrlFromPart == null || imageUrlFromPart.isEmpty()) {
	         imageUrlFromPart = "default.jpg";
	      }

	      return imageUrlFromPart;
	   }
    
}