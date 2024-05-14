package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CartModel;
import model.OrderModel;
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
	
	//fetch personal user details
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
	
	// Fetch all users details
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
	
	//Add product method in the database
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
	
	// Fetch product method
	public List<ProductModel> getAllProducts() {
	    List<ProductModel> products = new ArrayList<>();
	    try (Connection con = getConnection()) {
	        PreparedStatement stmt = con.prepareStatement(AppUtilities.ALL_PRODUCT_INFO);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            // Create a new product object
	            ProductModel product = new ProductModel();
	            // Set properties of the product object
	            product.setProductId(rs.getInt("product_id"));
	            product.setProductName(rs.getString("product_name"));
	            product.setProductPrice(rs.getDouble("product_price"));
	            product.setProductQuantity(rs.getInt("product_quantity"));
	            product.setProductImage(rs.getString("product_image"));
	            product.setProductDescription(rs.getString("product_description"));
	            // Add the product object to the list
	            products.add(product);
	        }
	    } catch (SQLException | ClassNotFoundException ex) {
	        ex.printStackTrace(); // Log the exception for debugging
	    }
	    return products;
	}
	
	public ProductModel getProductDetailsById(int productId) {
		ProductModel product = null;
		// Adjust table and column names as necessary

		try (Connection con = getConnection();
				PreparedStatement stmt = con.prepareStatement(AppUtilities.SINGLE_PRODUCT_DETAILS_FETCH)) {

			stmt.setInt(1, productId); // Set the ID parameter for the query
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				product = new ProductModel();
				product.setProductName(rs.getString("product_name"));
	            product.setProductPrice(rs.getDouble("product_price"));
	            product.setProductQuantity(rs.getInt("product_quantity"));
	            product.setProductImage(rs.getString("product_image"));
	            product.setProductDescription(rs.getString("product_description"));
				
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error in the SQL while fetching product by ID: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Driver not found");
		}
		return product;
	}
	
	//method to delete product from the database or store permanently
	public boolean deleteProduct(int productId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM product_details WHERE product_id = ?";
		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, productId);
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0; // Return true if at least one row is affected (product deleted)
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e; // Rethrow to allow higher-level handlers to manage the error
		}
	}
	
	
	public int addToCart(int userId, int productId, int quantity) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();

			// Check if the product already exists in the user's cart
			String checkQuery = "SELECT count(*) FROM user_cart WHERE user_id = ? AND product_id = ?";
			stmt = con.prepareStatement(checkQuery);
			stmt.setInt(1, userId);
			stmt.setInt(2, productId);
			rs = stmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				return 1; // Product already exists
			}

			String insertQuery = "INSERT INTO user_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
			stmt = con.prepareStatement(insertQuery);
			stmt.setInt(1, userId);
			stmt.setInt(2, productId);
			stmt.setInt(3, quantity);
			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				return -1; // Failed to add the product, no rows affected
			}

			return 0; // Successfully added
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return -1; // Indicates an error occurred during the operation
	}
	
	public int getUserIdByEmail(String email) throws SQLException, ClassNotFoundException {
		int userId = -1; 
		String userAddress = "";
		try (Connection con = getConnection()) {

			PreparedStatement statement = con.prepareStatement(AppUtilities.USER_INFO_BY_EMAIL);
			statement.setString(1, email);
		

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					userId = resultSet.getInt("user_id");
					userAddress = resultSet.getString("address");
					System.out.println("UserId: " + userId); // Print the userId for debugging
				} else {
					System.out.println("No user found with email: " + email); // Print when no user is found
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error retrieving user by email: " + e.getMessage()); // Print error message
			throw e; // Rethrow the exception to handle it upstream
		}

		return userId;
	}
	
	public List<CartModel> getCartItems(int userId) throws SQLException {
	    List<CartModel> cartItems = new ArrayList<>();
	    String sql = "SELECT c.cart_id, c.user_id, c.product_id, p.product_name, p.product_price, c.quantity, p.product_image "
	               + "FROM user_cart c "
	               + "INNER JOIN product_details p ON c.product_id = p.product_id "
	               + "WHERE c.user_id = ?";
	    try (Connection con = getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        System.out.println("Executing query with userId: " + userId); // Debug output

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (!rs.isBeforeFirst()) {
	                System.out.println("No data found for user: " + userId); // Log if no rows found
	            }
	            while (rs.next()) {
	                CartModel item = new CartModel();
	                item.setCartId(rs.getInt("cart_id"));
	                item.setUserId(rs.getInt("user_id"));
	                item.setProductId(rs.getInt("product_id"));
	                item.setQuantity(rs.getInt("quantity"));
	                item.setProductName(rs.getString("product_name"));
	                item.setProductImage(rs.getString("product_image"));
	                item.setProductPrice(rs.getDouble("product_price"));
	      
	                cartItems.add(item);
	                System.out.println("Added item: " + item.getProductName() + " with quantity: " + item.getQuantity()); // Debug output for each item added
	            	}
	        	}
	    	} catch (SQLException e) {
	    		System.err.println("Error retrieving cart items for user: " + userId + " - " + e.getMessage());
	    		throw e; // Properly handle SQL exceptions by rethrowing them to be managed at a higher level
	    } catch (ClassNotFoundException ex) {
	        ex.printStackTrace();
	    }
	    System.out.println("Total items retrieved: " + cartItems.size()); // Debug output for total items retrieved
	    return cartItems;
	}
	
	// Removes an item from cart
	public boolean removeCartItem(int cartId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM user_cart WHERE cart_id = ?";
		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, cartId);
				
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0; // Return true if at least one row is affected (item removed)
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e; // Rethrow to allow higher-level handlers to manage the error
		}
	}
	
	// Method to insert a new order into the database
	public boolean placeOrder(OrderModel orderModel) {
	    String sql = "INSERT INTO orders (user_id, product_id, order_date, order_status, total_amount) VALUES (?, ?, CURRENT_TIMESTAMP, 'pending', ?)";
	    
	    try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, orderModel.getUserId());
	        stmt.setInt(2, orderModel.getProductId());
	        stmt.setDouble(3, orderModel.getTotalAmount()); // Corrected index from 5 to 3

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; // Return true if rows were affected
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false; // Return false if an exception occurs
	    }
	}

    
	public List<OrderModel> getOrders() throws SQLException, ClassNotFoundException {
	    List<OrderModel> orders = new ArrayList<>();
	    // Ensure connection and statement are properly closed by using try-with-resources
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	                 "SELECT o.order_id, o.total_amount, p.product_name, o.order_status, u.username, u.address, o.order_date " +
	                         "FROM orders o " +
	                         "JOIN product_details p ON o.product_id = p.product_id " +
	                         "JOIN user_info u ON o.user_id = u.user_id")) {

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            OrderModel order = new OrderModel();
	            order.setOrderId(rs.getInt("order_id")); // Ensure column names are case-insensitively matched
	            order.setTotalAmount(rs.getDouble("total_amount")); // Assuming total_amount is a double
	            order.setProductName(rs.getString("product_name"));
	            order.setOrderStatus(rs.getString("order_status"));
	            order.setUserName(rs.getString("username"));
	            order.setAddress(rs.getString("address"));
	            order.setOrderDate(rs.getDate("order_date")); // Ensure correct data type handling

	            orders.add(order);
	        }
	    }
	    return orders;
	}

    
 // Combined method to search products by name or price
    public List<ProductModel> searchProducts(String searchQuery) throws Exception {
        List<ProductModel> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // Using the single connection method
            if (searchQuery.matches("-?\\d+(\\.\\d+)?")) { // Regex to check if input is numeric
                double price = Double.parseDouble(searchQuery);
                String sql = "SELECT * FROM product_details WHERE product_price >= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, price);
            } else {
                String sql = "SELECT * FROM product_details WHERE product_name LIKE ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + searchQuery + "%");
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductPrice(rs.getInt("product_price"));
				product.setProductQuantity(rs.getInt("product_quantity"));
				product.setProductDescription(rs.getString("product_description"));
				product.setProductImage(rs.getString("product_image"));
				 // Corrected to getDouble if your DB stores prices as doubles
                products.add(product);
                System.out.println(products);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return products;
    }
    
    
    
    public boolean updateProduct(int productId, ProductModel product) throws ClassNotFoundException {
        String sql = "UPDATE product_details SET product_name = ?, product_price = ?, product_quantity = ?, product_image = ?, product_description = ? WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getProductPrice());
            stmt.setInt(3, product.getProductQuantity());
            stmt.setString(4, product.getProductImage());
            stmt.setString(5, product.getProductDescription());
            stmt.setInt(6, productId);  // Bind the product ID for the WHERE clause

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Consider a more robust logging or error handling mechanism
            return false;
        }
    }


	
}
	