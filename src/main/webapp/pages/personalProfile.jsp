<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/personalProfile.css" />

</head>
<body>
    <!-- Button to go back to the home page -->
    <div style="padding: 20px;">
        <a href="${pageContext.request.contextPath}/DisplayProductsServlet" style="text-decoration: none; color: white; background-color: #007BFF; padding: 10px 20px; border-radius: 5px; display: inline-block;">Back to Home</a>
    </div>
    <div class="profile">
        <img src="${pageContext.request.contextPath}/images/about.jpg" alt="About">
        <div class="name">
            <h2>${user.firstName} ${user.lastName}</h2>
            <p>${user.address}</p>
        </div>
    </div>
    <div class="user">
        <div class="contact">
            <p>${user.contact}</p>
            <p>${user.email}</p>
            <button onclick="location.href='${pageContext.request.contextPath}/editProfile'">Edit Profile</button>
        </div>
        <div class="order">
            <h2>Order History</h2>
            <c:forEach var="order" items="${orders}">
                <div class="order_history">
                    <h4>${order.itemName}</h4>
                    <p>${order.date}</p>
                    <p>Quantity: ${order.quantity}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
