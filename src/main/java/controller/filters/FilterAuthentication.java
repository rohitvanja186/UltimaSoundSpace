package controller.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class FilterAuthentication implements Filter {

    @Override
    public void destroy() {
        // Clean up resources (not used here)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        System.out.println("Requested URI: " + uri);

        // Check if it's a resource or unsecured page
        boolean isResource = uri.matches(".*(css|js|jpg|png|gif|jpeg)$");
        boolean isUnsecuredPage = uri.endsWith("/pages/landing.jsp") || uri.endsWith("register.jsp");
        boolean isLogin = uri.endsWith("login.jsp");
        boolean isRegisterServlet = uri.endsWith("/RegisterServlet");
        boolean isLoginServlet = uri.endsWith("/LoginServlet");
        boolean isLogoutServlet = uri.endsWith("/LogoutServlet");

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = session != null && (session.getAttribute("user_email") != null || session.getAttribute("admin_email") != null);

        // Allow resources and unsecured pages
        if (isResource || isUnsecuredPage || isRegisterServlet || isLoginServlet || isLogoutServlet) {
            chain.doFilter(request, response);
            return;
        }
        
        if (uri.endsWith("/PersonalProfileServlet")) {
			chain.doFilter(req, res);
			return;
		}
        
        if (!isLoggedIn && isRegisterServlet) {
			chain.doFilter(req, res);
			return;
		}

        // Redirect based on login status
        if (!isLoggedIn && !(isLogin || isLoginServlet)) {
            res.sendRedirect(req.getContextPath() + "/pages/login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // Initialization logic can go here
    }
}


//Access control for non-static pages
//boolean isPublicPage = uri.endsWith("/login.jsp") || uri.endsWith("/RegisterServlet") || uri.endsWith("/pages/landing.jsp") || uri.endsWith("/register.jsp");
//boolean isLogoutAttempt = uri.endsWith("/LogoutServlet"); // Check if the request is for the logout servlet
//
////Allow access if it's a public page or a logout attempt
//if (isLoggedIn || isPublicPage || isLogoutAttempt) {
//    // If logged in or accessing public pages, continue the chain
//    chain.doFilter(request, response);
//} else {
//    // Not logged in and trying to access restricted pages
//    res.sendRedirect(req.getContextPath() + "/pages/login.jsp");




//boolean isLogin = uri.endsWith("login.jsp");
//boolean isLoginServlet = uri.endsWith("LoginServlet");
//boolean isLogoutServlet = uri.endsWith("LogoutServlet");
//
//HttpSession session = req.getSession(false);
//boolean isLoggedIn = session != null && session.getAttribute("user") != null;
//
//if (!isLoggedIn && !(isLogin || isLoginServlet)) {
//res.sendRedirect(req.getContextPath() + "/pages/login.jsp");
//}
//else if (isLoggedIn && !(!isLogin || isLogoutServlet)) {
//res.sendRedirect(req.getContextPath() + "/pages/home.jsp");
//}
//else {
//chain.doFilter(request, response);
//}
//}