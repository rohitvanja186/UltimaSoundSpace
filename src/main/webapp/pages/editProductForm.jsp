<%@ page import="model.ProductModel" %>
<%@ page import="controller.DbController" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/stylesheet/addProductForm.css" />
</head>
<body>
    <% 
        int productId = Integer.parseInt(request.getParameter("productId"));
        DbController dbController = new DbController();
        ProductModel product = dbController.getProductDetailsById(productId); // Ensure you have a method to get a single product by ID
    %>
    <div style="padding: 20px;">
        <a href="${pageContext.request.contextPath}/DisplayProductAdminServlet" style="text-decoration: none; color: white; background-color: #007BFF; padding: 10px 20px; border-radius: 5px; display: inline-block;">Back to Panel</a>
    </div>

    <div class="add-product-form">
        <h2>Edit Product</h2>
        <form action="${pageContext.request.contextPath}/ManageProductServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="updateProductId" value="<%= productId %>">
            <div class="form-group">
                <label for="productName">Product Name</label>
                <input type="text" id="productName" name="productName" value="<%= product.getProductName() %>" required>
            </div>
            <div class="form-group">
                <label for="productImage">Product Image</label>
                <input type="file" id="productImage" name="productImage" accept="image/*">
            </div>
            <div class="form-group">
                <label for="productQuantity">Quantity</label>
                <input type="number" id="productQuantity" name="productQuantity" value="<%= product.getProductQuantity() %>" min="1" required>
            </div>
            <div class="form-group">
                <label for="productDescription">Description</label>
                <textarea id="productDescription" name="productDescription" rows="4" required><%= product.getProductDescription() %></textarea>
            </div>
            <div class="form-group">
                <label for="productPrice">Price</label>
                <input type="text" id="productPrice" name="productPrice" value="<%= product.getProductPrice() %>" required>
            </div>
            <button type="submit" class="btn">Update Product</button>
        </form>
    </div>
</body>
</html>
