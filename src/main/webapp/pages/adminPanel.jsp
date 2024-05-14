<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.ProductModel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/stylesheet/adminPanel.css" />

	<!-- javaScript for the logout button  --> 
    <script defer>
    function confirmLogout() {
        if (confirm('Are you sure you want to log out?')) {
            document.getElementById('logoutForm').submit();
        }
    }

    </script>
    
    <script>
	function confirmDelete() {
    	return confirm('Are you sure you want to delete this product?');
	}
	</script>
    
</head>
<body>
	<div class="container">
        <div class="sidebar">
            <div class="logo">
                <h3>Admin Panel</h3>
            </div>
            <nav>
                <ul>
                    <li><a href="#"><i class="fas fa-box"></i> Products</a></li>
                    <li><a href="#"><i class="fas fa-users"></i> Customers</a></li>
                    <li><a href="${pageContext.request.contextPath}/AdminOrderServlet""><i class="fas fa-shopping-cart"></i> Orders</a></li>
                </ul>
 
  					<div class="icon-wrapper">
    					<form action="${pageContext.request.contextPath}/LogoutServlet" method="POST"  id="logoutForm">
      						<button type="button" onclick="confirmLogout()" style="background: none; border: none; cursor: pointer; outline: none; width: 100%;">
        						<i class="fa-solid fa-right-from-bracket" title="Logout"></i>
        						<span style="margin-left: 5px;">Logout</span>
      						</button>
    					</form>
  					</div>
			
            </nav>
        </div>
        <div class="main-content">
            <header>
                <div class="user-info">
                    <img src="${pageContext.request.contextPath}/images/about.jpg" alt="User Profile">
                    <div class="user-details">
                        <span>Sujan</span>
                        <span>Subedi</span>
                    </div>
                </div>
            </header>
            <main>
                <div class="product-section">
    				<div class="header-flex" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
        				<h2>Products</h2>
        				<a href="${pageContext.request.contextPath}/AddProductServlet" style="text-decoration: none;">
            				<button class="add-product-btn" style="background-color: #1C6675; color: white; border: none; padding: 10px 15px; font-size: 16px; cursor: pointer; border-radius: 5px; text-decoration: none;">
                			<i class="fas fa-plus" style="margin-right: 5px;"></i> Add Product
            				</button>
        				</a>
    				</div>
    				<table>
        				<thead>
            				<tr>
                				<th>Product Name</th>
                				<th>Price</th>
                				<th>Stock Quantity</th>
                				<th>Actions</th>
            				</tr>
        				</thead>
        				<tbody>
            				<% 
                        	List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
                        	if (products != null && !products.isEmpty()) {
                            	for (ProductModel product : products) {
                        	%>
                        	<tr>
                            	<td><%= product.getProductName() %></td>
                            	<td>$<%= product.getProductPrice() %></td>
                            	<td><%= product.getProductQuantity() %></td>
                            	<td>
                                	<!-- <button class="edit-btn"><i class="fas fa-edit"></i></button> -->
                                	<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/DisplayEditProductFormServlet?productId=<%= product.getProductId() %>'" class="edit-btn">
    									<i class="fas fa-edit"></i> Edit
									</button>
                                	
                                	<form action="${pageContext.request.contextPath}/ManageProductServlet" method="post">
    									<input type="hidden" name="deleteProductId" value="<%= product.getProductId() %>">
    									<button type="submit" class="delete-btn" onclick="return confirmDelete();">
        									<i class="fas fa-trash"></i>
    									</button>
									</form>
                                	
                           	 	</td>
                        	</tr>
                        	<% 
                            	}
                        	} else {
                            	out.println("<tr><td colspan='4'>No products available.</td></tr>");
                        	}
                        	%>
        				</tbody>
    				</table>
				</div>

            </main>
        </div>
    </div>

</body>
</html>