<%@page import="utilities.AppUtilities"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/stylesheet/login.css" />
</head>
<body>
    <div class="container">
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="">
            <h1>Welcome in <br> Ultima SoundSpace</h1>
            </div>
            <div class="field">
                <input type="email" name="email" placeholder="Email" style="color: white;">
            <input type="password" name="password" placeholder="Password" style="color: white;">
            </div>
            <% 
        String successMessage = (String) request.getAttribute(AppUtilities.SUCCESS_MESSAGE);
        String errorMessage = (String) request.getAttribute(AppUtilities.ERROR_MESSAGE);

        if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
        <!-- Display error message -->
         <div class="alert alert-danger mt-2" role="alert">
            <%= errorMessage %>
        </div>
        <% } %>

        <% 
        if (successMessage != null && !successMessage.isEmpty()) {
        %>
        <!-- Display success message -->
        <div class="alert alert-success mt-2" role="alert">
            <%= successMessage %>
        </div>
        <% } %>
            <button id="login">LOGIN</button>
            <div class="signup">
                <p>Don't have an account?</p>
                <a href="register.jsp">sign up</a>
            </div>
        </form>
    </div>
</body>
</html>