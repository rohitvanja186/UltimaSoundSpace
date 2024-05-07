package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.PasswordEncryption;
import model.ProductModel;
import model.UserModel;
import utilities.AppUtilities;

public class DbController {
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/ultima_soundspace";
		String user = "root";
		String pass = "";
		
		
		
	
		return DriverManager.getConnection(url, user, pass);
	}
	
	public int addUser(UserModel userModel) {
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(AppUtilities.INSERT_USER);

            PreparedStatement checkUsernameSt = con.prepareStatement(AppUtilities.COUNT_USERNAME);
            checkUsernameSt.setString(1, userModel.getUsername());
            ResultSet checkUsernameRs = checkUsernameSt.executeQuery();

            checkUsernameRs.next();

            if (checkUsernameRs.getInt(1) > 0) {
                return -2; // Username already exists
            }

            PreparedStatement checkContactSt = con.prepareStatement(AppUtilities.COUNT_CONTACT);
            checkContactSt.setString(1, userModel.getContact());
            ResultSet checkContactRs = checkContactSt.executeQuery();

            checkContactRs.next();

            if (checkContactRs.getInt(1) > 0) {
                return -4; // Phone Number already exists
            }

            PreparedStatement checkEmailSt = con.prepareStatement(AppUtilities.COUNT_EMAIL);
            checkEmailSt.setString(1, userModel.getEmail());
            ResultSet checkEmailRs = checkEmailSt.executeQuery();

            checkEmailRs.next();

            if (checkEmailRs.getInt(1) > 0) {
                return -3; // Email already exists
            }

            // Encrypt password before storing it in the database
            String encryptedPassword = PasswordEncryption.encryptPassword(userModel.getPassword(), "U3CdwubLD5yQbUOG92ZnHw==");

            st.setString(1, userModel.getFirstName());
            st.setString(2, userModel.getLastName());
            st.setString(3, userModel.getUsername());
            st.setString(4, userModel.getEmail());
            st.setString(5, userModel.getContact());
            st.setString(6, userModel.getAddress());
            st.setString(7, encryptedPassword);
            st.setString(8, userModel.getRole());

            int result = st.executeUpdate();
            return result > 0 ? 1 : 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
	}
	
	public int getUserLoginInfo(String email, String password) {
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(AppUtilities.USER_INFO_BY_EMAIL);
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String emailDb = rs.getString("email");
                String encryptedPassword = rs.getString("password");
                String roleDb = rs.getString("role");

                // Decrypt password from database to compare it
                String decryptedPassword = PasswordEncryption.decryptPassword(encryptedPassword, "U3CdwubLD5yQbUOG92ZnHw==");

                if (decryptedPassword!=null && emailDb.equals(email) && decryptedPassword.equals(password) && roleDb.equals("admin")) {
                    return 7; // Successfull admin login
                	}
                else if (decryptedPassword!=null && emailDb.equals(email) && decryptedPassword.equals(password)) {
                	return 1; //Successfull user login
                } else {
                    return 0; // Mismatch password
                }
            } else {
                // No matching record found
                return 0;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
	
	public UserModel getUserDetails(String email) throws ClassNotFoundException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(AppUtilities.USER_INFO_BY_EMAIL)) {
            statement.setString(1, email);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Fetch user details from the result set and create a user object
                	UserModel user = new UserModel();
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setContact(resultSet.getString("contact"));
                    user.setAddress(resultSet.getString("address"));
                    // Populate other fields as needed
                    return user;
                } else {
                    // No user found with the given username
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
            return null;
        }
    }
	
	
	public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(AppUtilities.ALL_USER_INFO);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setContact(rs.getString("contact"));
                user.setAddress(rs.getString("address"));
                // Populate other fields as needed
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
        }
        return users;
    }
	
	
	public int addProduct(ProductModel productModel) {
        try (Connection con = getConnection()) {
            try (PreparedStatement addProductSt = con.prepareStatement(AppUtilities.ADD_PRODUCT)) {
                // Input validation
                if (productModel == null || 
                    productModel.getProductName() == null || productModel.getProductName().isEmpty() ||
                    productModel.getProductPrice() <= 0 ||
                    productModel.getProductQuantity() <= 0||
                    productModel.getProductDescription() == null || productModel.getProductDescription().isEmpty() ||
                    productModel.getProductImage() == null || productModel.getProductImage().isEmpty()) {
                    System.err.println("Product data invalid.");
                    return -2; // Return -2 indicating invalid data
                }

                addProductSt.setString(1, productModel.getProductName());
                addProductSt.setDouble(2, productModel.getProductPrice());
                addProductSt.setInt(3, productModel.getProductQuantity());
                addProductSt.setString(4, productModel.getProductImage());
                addProductSt.setString(5, productModel.getProductDescription());
                
                int productResult = addProductSt.executeUpdate();

                return productResult; // Return the result of the execution

            } catch (SQLException e) {
                e.printStackTrace();
                return -1; // Return -1 indicating an SQL exception
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return -1; // Return -1 indicating an SQL or class not found exception
        }
    }
	
//	public List<ProductModel> getAllProducts() {
//        List<ProductModel> products = new ArrayList<>();
//        try (Connection con = getConnection()) {
//            PreparedStatement st = con.prepareStatement(AppUtilities.ALL_PRODUCT_INFO);
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//            	ProductModel product = new ProductModel();
//                product.setProductName(rs.getString("productName"));
//                product.setProductPrice(rs.getDouble("productPrice"));
//                product.setProductQuantity(rs.getInt("productQuantity"));
//                product.setProductImage(rs.getPart("productImage"));
//                product.setProductDescription(rs.getString("productDescription"));
//                // Populate other fields as needed
//            	products.add(product);
//            	
//            }
//        } catch (SQLException | ClassNotFoundException ex) {
//            ex.printStackTrace(); // Log the exception for debugging
//        }
//        return products;
//    }
	
}
	