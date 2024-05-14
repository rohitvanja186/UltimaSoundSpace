package utilities;

import java.io.File;

public class AppUtilities {
	//Define SQL queries as constants***
	
	//Register new user query***
	public static final String INSERT_USER = "INSERT INTO user_info (first_name, last_name, username, email, contact, address, password, role) value (?,?,?,?,?,?,?,?)";
	public static final String USER_INFO_BY_EMAIL = "SELECT * FROM user_info WHERE email = ?";
	public static final String COUNT_USERNAME = "SELECT COUNT(*) FROM user_info WHERE username = ?";
	public static final String COUNT_CONTACT = "SELECT COUNT(*) FROM user_info WHERE contact = ?";
	public static final String COUNT_EMAIL = "SELECT COUNT(*) FROM user_info WHERE email = ?";
	public static final String ALL_USER_INFO = "SELECT * FROM user_info";
	
	
	//Register page message***
	public static final String REGISTER_SUCCESS_MESSAGE = "Registration successful! Proceed to login.";
	public static final String REGISTER_ERROR_MESSAGE = "Please enter the correct form data.";
	
	public static final String SERVER_ERROR_MESSAGE = "An unexpected server error has occurred.";
	
	public static final String USERNAME_EXIST_ERROR_MESSAGE = "Username is already exist.";
	public static final String EMAIL_EXIST_ERROR_MESSAGE = "Email is already exist.";
	public static final String PHONE_NUMBER_EXIST_ERROR_MESSAGE = "Phone Number is already exitst.";
	public static final String PASSWORD_MISMATCH_ERROR_MESSAGE = "Password do not matched.";
	
	
	// login page message***
	public static final String USER_LOGIN_SUCCESS_MESSAGE = "Successfully Logged In!";	
	public static final String LOGIN_ERROR_MESSAGE = "Either username or password in not correct!";
	
	// Admin page message***
	public static final String ADMIN_LOGIN_SUCCESS_MESSAGE = "Admin, Successfully Logged In!";	
	
	// Admin Add Product page message***
	public static final String ADDPRODUCT_SUCCESS_MESSAGE = "Product Added Successfully.";
	public static final String ADDPRODUCT_ERROR_MESSAGE = "Please enter the correct form data.";
	
	//Error message***
	public static final String SUCCESS_MESSAGE = "successMessage.";
	public static final String ERROR_MESSAGE = "errorMessage.";
	
	
	//Parameter names***
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String USER_NAME = "username";
	public static final String EMAIL = "email";
	public static final String CONTACT = "contact";
	public static final String ADDRESS = "address";
	public static final String PASSWORD = "password";
	public static final String RETYPE_PASSWORD = "confirmPassword";
	
	
	
	//Add Product***
	public static final String ADD_PRODUCT = "INSERT INTO product_details (product_name, product_price, product_quantity, product_image, product_description) value (?,?,?,?,?)";
	public static final String ALL_PRODUCT_INFO = "SELECT * FROM product_details";
	public static final String SINGLE_PRODUCT_DETAILS_FETCH = "SELECT * FROM product_details WHERE product_id = ?";
	
	//Product Parameter names***
	public static final String PRODUCT_NAME = "productName";
	public static final String PRODUCT_PRICE = "productPrice";
	public static final String PRODUCT_QUANTITY = "productQuantity";
	public static final String PRODUCT_IMAGE = "productImage";
	public static final String PRODUCT_DESCRIPTION = "productDescription";
	
	

	//Product image directory***
	public static final String IMG_DIR_PATH = "Users\\DELL\\eclipse-workspace\\UltimaSoundSpace\\src\\main\\webapp\\productImages\\";
	public static final String IMG_DIR = "C:" + File.separator + IMG_DIR_PATH;
	
	
	
	//JSP Route***
	public static final String LANDING_PAGE = "/pages/landing.jsp";
	public static final String REGISTER_PAGE = "/pages/register.jsp";
	public static final String LOGIN_PAGE = "/pages/login.jsp";
	public static final String HOME_PAGE = "/pages/home.jsp";
	public static final String PERSONAL_PROFILE_PAGE = "/pages/personalProfile.jsp";
	public static final String ADMIN_PAGE = "/pages/adminPanel.jsp";
	public static final String ADD_PRODUCT_FORM_PAGE = "/pages/addProductForm.jsp";
	public static final String SINGLE_PRODUCT_DETAILS_PAGE = "/pages/singleProductDetail.jsp";
	public static final String CART_PAGE = "/pages/cart.jsp";
	
		
	//Servled Route***
	public static final String REGISTER_SERVLET = "/RegisterServlet";
	public static final String LOGIN_SERVLET = "/LoginServlet";
	public static final String PROFILE_SERVLET = "/PersonalProfileServlet";
	public static final String ADD_PRODUCT_SERVLET = "/AddProductServlet";
	public static final String SINGLE_PRODUCT_DETAILS_SERVLET = "/SingleProductDetailServlet";
	public static final String ADD_TO_CART_SERVLET = "/AddtocartServlet";
	public static final String DISPLAY_PRODUCT_ADMIN_SERVLET = "/DisplayProductAdminServlet";
	public static final String ORDER_SERVLET = "/OrderServlet";
}