<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.ProductModel" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Landing page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/stylesheet/landing.css" />
	<style>
        html {
            scroll-behavior: smooth;
        }
    </style>

    <%
        if (request.getAttribute("products") == null) {
            response.sendRedirect(request.getContextPath() + "/DisplayProductServlet");
            return;
        }
    %>
    
</head>
<body>
	<div class="navbar">
            <div class="menu">
                <ul>
                    <li><a href="">Home</a></li>
                    <li><a href="#collectionSection">Collection</a></li>
                    <li><a href="#contactSection">Contact</a></li>
                </ul>
            </div>
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="">
            <div class="login">
             <a href="${pageContext.request.contextPath}/pages/login.jsp">
                <button id="login">Login <i class="fa-solid fa-user"></i></button>
             </a>
             <a href="${pageContext.request.contextPath}/pages/login.jsp">
                <i class="fa-solid fa-cart-shopping"></i>
             </a>   
            </div>
        </div>
        <div class="content">
            <div class="about">
                <h1>Your Ultimate <br>Speaker Destination</h1>
                <p>Welcome to Ultima SoundSpace, your ultimate destination for premium speakers. Explore our curated collection of high-fidelity audio solutions, crafted to elevate your sound experience. From sleek wireless designs to powerful home theater systems, discover the perfect speaker to enhance every moment. Elevate your audio journey with Ultima SoundSpace.</p>
                <a href="#aboutSection">
        			<button class="about">About us</button>
   				</a>
            </div>
            <img src="${pageContext.request.contextPath}/images/content.jpg" alt="">
        </div>
        
        <div class="collection" id="collectionSection">
    		<% 
			List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
			if (products != null && !products.isEmpty()) {
    			for (ProductModel product : products) {
			%>
        		<div class="item">
            		<img src="${pageContext.request.contextPath}/productImages/<%= product.getProductImage() %>" alt="<%= product.getProductName() %>">
            		<p><%= product.getProductName() %></p>
            		<p>Rs.<%= product.getProductPrice() %></p>
            		<button>Add to cart</button>
        		</div>
			<%
    			}
			} else {
    			// Optionally display a message if no products are found
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
                     <img src="${pageContext.request.contextPath}/images/about.jpg" alt="">
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
            <form action="">
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