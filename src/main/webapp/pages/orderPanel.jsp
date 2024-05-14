<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Order View</title>
  <style>
    .order-container {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      grid-gap: 20px;
    }

    .order-item {
      background-color: #f2f2f2;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .order-details {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }

    .order-details span {
      font-weight: bold;
    }

    .order-products {
      display: flex;
      flex-direction: column;
    }

    .order-product {
      display: flex;
      justify-content: space-between;
      margin-bottom: 5px;
    }

    .delivered-btn {
      background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
  </style>
</head>
<body>
  <h1>Order Management</h1>
  <div class="order-container">
    <div class="order-item">
      <div class="order-details">
        <span>Order ID:</span> <span>1234</span>
      </div>
      <div class="order-details">
        <span>Customer Name:</span> <span>John Doe</span>
      </div>
      <div class="order-details">
        <span>Order Date:</span> <span>05/10/2024</span>
      </div>
      <div class="order-products">
        <div class="order-product">
          <span>Product Name</span>
          <span>Quantity</span>
        </div>
        <div class="order-product">
          <span>JLB</span>
          <span>2</span>
        </div>
      </div>
      <button class="delivered-btn">Delivered</button>
    </div>
    <!-- Add more order items here -->
  </div>
</body>
</html>