<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ProductModel" %>
<%@ page import="controller.DbController" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= request.getAttribute("product") != null ? ((ProductModel) request.getAttribute("product")).getProductName() : "Product Details" %></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
            integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
            crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/singleProductDetails.css" />
</head>
<body>
    <!-- Top Navigation with Back and Cart Icon -->
    <div style="display: flex; align-items: center; padding: 20px;">
        <a href="${pageContext.request.contextPath}/DisplayProductsServlet" style="text-decoration: none; color: white; background-color: #007BFF; padding: 10px 20px; border-radius: 5px; margin-right: 20px;">Back to Home</a>
        <a href="${pageContext.request.contextPath}/cart" style="color: white; background-color: #007BFF; padding: 10px 20px; border-radius: 5px;">
            <i class="fa-solid fa-cart-shopping" title="Cart"></i>
        </a>
    </div>
    <div class="container">
        <%
        String productId = request.getParameter("id");
    	DbController dbController = new DbController();
    	ProductModel product = null;

    	if (productId != null && !productId.isEmpty()) {
    		try {
    			product = dbController.getProductDetailsById(Integer.parseInt(productId));
    		} catch (Exception e) {
    			e.printStackTrace();
    			response.getWriter().write("Error retrieving product details.");
    			return; // Stop further execution in case of error
    		}
    	}

        if (request.getAttribute("product") != null) {
            /* ProductModel product = (ProductModel) request.getAttribute("product"); */
        %>
            <div style="display: flex; justify-content: space-between;">
                <!-- Product Image -->
                <div>
                    <img src="<%= request.getContextPath() %>/productImages/<%= product.getProductImage() %>" alt="<%= product.getProductName() %>" style="width: 50%;">
                </div>
                <!-- Product Details -->
                <div class="product_detail">
                    <h1><%= product.getProductName() %></h1>
                    <p><%= product.getProductDescription() %></p>
                    <h2>Rs. <%= product.getProductPrice() %></h2>
                    <h2>Quantity: <%= product.getProductQuantity() %></h2>
                    
                    <!-- Form for adding product to cart -->
                	<form action="${pageContext.request.contextPath}/AddtocartServlet" method="post">
                    	<input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    	<input type="hidden" name="quantity" value="1"> <!-- Assuming a default quantity of 1 -->
                    	<button type="submit" style="margin-top: 10px;">Add to cart</button>
                	</form>
                </div>
            </div>
        <% } else { %>
            <p>Product not found.</p>
        <% } %>
    </div>
</body>
</html>
