<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product Form</title>
<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/stylesheet/addProductForm.css" />
</head>
<body>

	<div class="add-product-form">
        <h2>Add Product</h2>
        <form action="${pageContext.request.contextPath}/AddProductServlet" method="post" enctype="multipart/form-data">
        	<div class="form-group">
                <label for="productName">Product Name</label>
                <input type="text" id="productName" name="productName" required>
            </div>
            <div class="form-group">
                <label for="productImage">Product Image</label>
                <input type="file" id="productImage" name="productImage" accept="image/*" required>
            </div>
            <div class="form-group">
                <label for="productQuantity">Quantity</label>
                <input type="number" id="productQuantity" name="productQuantity" min="1" required>
            </div>
            <div class="form-group">
                <label for="productDescription">Description</label>
                <textarea id="productDescription" name="productDescription" rows="4" required></textarea>
            </div>
            <div class="form-group">
                <label for="productPrice">Price</label>
                <input type="text" id="productPrice" name="productPrice" required>
            </div>
            <button type="submit" class="btn">Add Product</button>
        </form>
    </div>
</body>
</html>