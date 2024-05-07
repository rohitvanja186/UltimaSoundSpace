<%@page import="model.UserModel"%>
<%@page import="utilities.AppUtilities"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register page</title>
<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/stylesheet/register.css" />
</head>
<body>
	<div class="container">
		<img src="${pageContext.request.contextPath}/images/register.jpg" alt="">
        <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
            <h1 style="color:#1C6675;">Register</h1>
            <p style="color: #1C6675;">Please complete to create your account</p>
            <div class="name">
            <input type="text" name="firstName" placeholder="First Name">
            <input type="text" name="lastName" placeholder="Last Name">
            </div>
            <input type="text" name="username" placeholder="Username">
            <input type="email" name="email" placeholder="Email">
            <input type="text" name="address" placeholder="Address">
            <input type="text" name="contact" placeholder="Phone Number">
            <input type="password" name="password" placeholder="Password">
            <input type="password" name="confirmPassword" placeholder="Confirm Password">
            <%
        	String errorMessage = (String) request.getAttribute(AppUtilities.ERROR_MESSAGE);
    
        
        	if (errorMessage !=null && !errorMessage.isEmpty()) {
    		%>
        	<div class="alert alert-danger" role="alert">
            	<%= errorMessage %>
        	</div>
        	<% 
        	}
        	%>
                <button id="sign up">SIGN UP</button>
                <a href="login.jsp">I am already a member</a>
        </form>
        
    </div>
</body>
</html>
