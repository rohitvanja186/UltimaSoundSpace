<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.ProductModel" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/stylesheet/home.css" />
	<style>
        html {
            scroll-behavior: smooth;
        }
    </style>
    
    <!-- javaScript for the logout button  --> 
    <script defer>
    function confirmLogout() {
        if (confirm('Are you sure you want to log out?')) {
            document.getElementById('logoutForm').submit();
        }
    }

    </script> 

    <script>console.log('Context Path: <%= request.getContextPath() %>');</script>
    
</head>
<body>
	<div class="navbar" id="homeSection">
        <div class="menu">
            <ul>
                <li><a href="#homeSection">Home</a></li>
                <li><a href="#collectionSection">Collection</a></li>
                <li><a href="#contactSection">Contact</a></li>
                <li><a href="#aboutSection">About us</a></li>
            </ul>
        </div>
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
        <div class="login">
    		<div class="icon-wrapper">
        		<a href="${pageContext.request.contextPath}/PersonalProfileServlet">
            		<i class="fa-solid fa-user" title="Profile"></i>
        		</a>
    		</div>
   		 	<div class="icon-wrapper">
        		<a href="${pageContext.request.contextPath}/AddtocartServlet">
            		<i class="fa-solid fa-cart-shopping" title="Cart"></i>
      		 	</a>
    	 	</div>
    		<div class="icon-wrapper">
        		<form action="${pageContext.request.contextPath}/LogoutServlet" method="POST" style="display: inline;" id="logoutForm">
            		<button type="button" onclick="confirmLogout()" style="background: none; border: none; cursor: pointer; outline: none; width: 100%;">
                		<i class="fa-solid fa-right-from-bracket" title="Logout"></i>
            		</button>
        		</form>
   			</div>
		</div>
    </div>
    
    <% if (session.getAttribute("cartMessage") != null) { %>
        <div style="color: green; text-align: center; margin-top: 20px;">
            <%= session.getAttribute("cartMessage") %>
        </div>
        <% session.removeAttribute("cartMessage"); %>
    <% } %>
    
    <div class="container">
        <div class="search">
            <h2>Amplify Your Experience:<br> Shop Speakers Today!</h2>
            <form action="${pageContext.request.contextPath}/SearchServlet" method="GET">
            <div class="search-bar">
                <input type="text" name="search" placeholder="Search..." required>
                <button type="submit" style="border: none; background: none;">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        	</form>
        </div> 
        <img src="${pageContext.request.contextPath}/images/home.jpg" alt="Home Decor">
    </div>
    <div class="collection" id="collectionSection">
    	<% 
    	List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
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
    
     <div class="about" id="aboutSection">
        <h1>About us</h1>
        <div class="about_us">
            <p>At Speaker Haven, we're passionate about bringing the joy of immersive audio experiences to every home.
                 Founded by a team of audio enthusiasts with a shared vision for superior sound quality, our journey began with a simple desire to redefine the way people listen to music, watch movies, and enjoy their favorite content.
                  With years of industry expertise and a deep understanding of sound engineering, we set out to create speakers that not only deliver exceptional performance but also elevate the overall audio experience.
                 Our commitment to excellence drives every aspect of our business, from the meticulous design process to the rigorous testing and quality assurance procedures.
                 But our mission goes beyond just making great speakers; it's about connecting people with the power of music and sound. Whether you're an audiophile seeking studio-grade clarity or a casual listener looking to enhance your home entertainment setup, we're here to help you discover the perfect speakers for your unique needs.
                 Welcome to Speaker Haven, where every note, every beat, and every sound matters.</p>
                 <img src="${pageContext.request.contextPath}/images/about.jpg" alt="About Us">
        </div>
     </div>
     <div class="contact" id="contactSection">
        <div class="contact_us">
            <h2 style="color: #1C6675; font-size: 30px;">Contact Us</h2>
            <p style="color: #1C6675; font-size: 20px;">Any question? we would be<br>happy to help you</p>
            <div class="address_btn">
                <button><i class="fa-solid fa-phone"></i>9827089956</button>
                <button><i class="fa-solid fa-envelope"></i>np05cp4a220054@iic.edu.np</button>
                <button><i class="fa-solid fa-location-dot"></i>Sundar Haaraicha, 5</button>
            </div>
        </div>
        <form action="${pageContext.request.contextPath}/sendMessage">
            <div class="name">
                <input type="text" name="first_name" placeholder="First Name">
                <input type="text" name="last_name" placeholder="Last Name">
            </div>
                <input type="text" name="email" placeholder="Email">
                <input type="text" name="phone_number" placeholder="Phone Number">
                <textarea name="" id="" cols="47" rows="10" placeholder="Message" style="border-radius: 10px; padding: 10px; border: 1px solid #1C6675;"></textarea>
                <button>Send Mesage</button>
        </form>
     </div>
     <footer>
        <h2 style="color: aliceblue;">© 2024 Ultima SoundSpace. All Rights Reserved</h2>
     </footer>

</body>
</html>