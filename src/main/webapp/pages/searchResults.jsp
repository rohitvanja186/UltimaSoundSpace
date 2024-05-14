<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.ProductModel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Searched Results</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheet/searchResults.css" />
</head>
<body>
	<div class="collection" id="collectionSection">
    	<% 
			List<ProductModel> products = (List<ProductModel>) request.getAttribute("searchResults");  // This should match the attribute name set in the servlet
			if (products != null && !products.isEmpty()) {
    			for (ProductModel product : products) {
		%>
    	
        	<div class="item">
            	<!-- Adding a link around the product details to make them clickable and pass the product ID -->
            	<a href="${pageContext.request.contextPath}/SingleProductDetailServlet?id=<%= product.getProductId() %>" style="text-decoration: none; color: inherit; text-align: center">
                	<img src="${pageContext.request.contextPath}/productImages/<%= product.getProductImage() %>" alt="<%= product.getProductName() %>">
                	<p><%= product.getProductName() %></p>
                	<p>Rs.<%= product.getProductPrice() %></p>
            	</a>
            	<!-- Form for adding product to cart -->
            	<form action="${pageContext.request.contextPath}/AddtocartServlet" method="post">
           	     	<input type="hidden" name="productId" value="<%= product.getProductId() %>">
                	<input type="hidden" name="quantity" value="1"> <!-- Assuming a default quantity of 1 -->
                	<button type="submit" style="margin-top: -100px;">Add to cart</button>
            	</form>
       		 </div>
    	<%
        	}
    	} else {
        	out.println("<p>No products available.</p>");
    	}
    	%>
 	 </div>
</body>
</html>