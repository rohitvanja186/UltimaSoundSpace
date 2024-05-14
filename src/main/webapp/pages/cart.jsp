<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.CartModel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Cart</title>
<style>
  body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 20px;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.cart-item:last-child {
  border-bottom: none;
}

.cart-item img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  margin-right: 10px;
}

.cart-item-details {
  flex-grow: 1;
  margin-right: 10px;
}

.cart-item-actions {
  display: flex;
  align-items: center;
}

.cart-item-actions input {
  width: 40px;
  text-align: center;
  margin-right: 10px;
}

.cart-total {
  font-weight: bold;
  margin-top: 20px;
  text-align: right;
}

/* Button styles */
button {
  border: none;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

button.remove {
  background-color: #dc3545;
  color: #fff;
}

button.remove:hover {
  background-color: #c82333;
}

#checkout-btn {
  background-color: #28a745;
  color: #fff;
  float: right;
  margin-top: 20px;
  margin-right: 20px;
}

#checkout-btn:hover {
  background-color: #218838;
}
</style>
  
  <!-- JavaScript to update total amount -->
  <script>
	function updateTotal() {
    	var items = document.getElementsByClassName('cart-item');
    	var total = 0;
    	for (var i = 0; i < items.length; i++) {
        	var price = parseFloat(items[i].getElementsByClassName('price')[0].innerText.substring(1)); // Skip '$'
        	var quantity = parseInt(items[i].getElementsByClassName('quantity')[0].value, 10);
        	total += price * quantity;
    	}
    	document.getElementById('total').innerText = 'Total: $' + total.toFixed(2);
	}

	window.onload = function() {
    	var quantityInputs = document.getElementsByClassName('quantity');
    	for (var i = 0; i < quantityInputs.length; i++) {
        	quantityInputs[i].onchange = updateTotal;
        	quantityInputs[i].onkeyup = updateTotal;
    	}
    	updateTotal(); // Initial total update
	}
	</script>
  
</head>
<body>

	<div style="text-align: center; margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/DisplayProductsServlet" style="text-decoration: none; color: white; background-color: #007BFF; padding: 10px 20px; border-radius: 5px; display: inline-block;">Back to Home</a>
    </div>
    
	<h1>My Cart</h1>

	<div class="cart-items">
    <% 
    List<CartModel> cartItems = (List<CartModel>) request.getAttribute("cartItems");
    
    if (cartItems == null || cartItems.isEmpty()) {
        out.println("<p>Your cart is empty.</p>");
    } else {
        for (CartModel item : cartItems) {
            out.println("<div class='cart-item'>");
            out.println("<img src='" + request.getContextPath() + "/productImages/" + item.getProductImage() + "' alt='" + item.getProductName() + "' style='width: 80px; height: 80px;'>");
            out.println("<div class='cart-item-details'>");
            out.println("<h3>" + item.getProductName() + "</h3>");
            out.println("<p class='price'>$" + item.getProductPrice() + "</p>");
            out.println("</div>");
            out.println("<div class='cart-item-actions'>");
            out.println("<input type='number' class='quantity' value='" + item.getQuantity() + "' min='1'>");
            out.println("<form action='" + request.getContextPath() + "/RemoveCartItemServlet' method='post'>");
            out.println("<input type='hidden' name='deleteId' value='" + item.getCartId() + "'>");
            out.println("<button type='submit' class='remove'>Remove</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
        	}
    	}
    	%>
	</div>

	<div class="cart-total" style="margin-right: 20px;">
    	<span id="total">Total: $0.00</span>
	</div>

	<form action="${pageContext.request.contextPath}/OrderServlet" method="post">
    <% if (!cartItems.isEmpty()) {
        double totalAmount = 0;
        int totalQuantity = 0;
        for (CartModel item : cartItems) {
            totalAmount += item.getProductPrice() * item.getQuantity();
            totalQuantity += item.getQuantity();
    %>
            <input type="hidden" name="productId" value="<%= item.getProductId() %>">
            <input type="hidden" name="totalAmount" value="<%= totalAmount %>">
            <input type="hidden" name="quantity" value="<%= totalQuantity %>">
    <% 
        }
    } %>
        <button id="checkout-btn" type="submit">Checkout</button>
	</form>
	
</body>
</html>
