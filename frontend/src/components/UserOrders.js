import React, { useState, useEffect } from "react";
import axios from "axios";
import { format } from "date-fns";
import "../css/UserOrder.css";
import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

const UserOrders = () => {
  const [userOrders, setUserOrders] = useState([]);
  const userEmail = localStorage.getItem("userEmail");
  const navigate = useNavigate(); 

  useEffect(() => {
    
    const fetchUserOrders = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/orders/user/${userEmail}`
        );
        setUserOrders(response.data);
      } catch (error) {
        console.error("Error fetching user orders:", error);
      }
    };

    fetchUserOrders();
  }, [userEmail]);

  
  const reversedUserOrders = [...userOrders].reverse();

  
  const formatDate = (dateArray) => {
    const [year, month, day, hours, minutes, seconds] = dateArray;
    const formattedDate = new Date(
      year,
      month - 1,
      day,
      hours,
      minutes,
      seconds
    );
    return formattedDate.toLocaleString();
  };

  return (
    <>
      <Navbar />
      <div className="user-orders-container">
        <h2>Your Orders</h2>
        <ul>
          {reversedUserOrders.map((order) => (
            <li key={order.id} className="order-item">
              <div className="order-header">
                <p className="order-date">
                  Order Date: {formatDate(order.orderDate)}
                </p>
                <p className="total-price">Total Price: {order.totalPrice}</p>
              </div>
              <ul className="order-items-list">
                {order.orderItems.map((orderItem) => (
                  <li key={orderItem.id} className="order-item-list">
                    <p className="item-name">Item: {orderItem.name}</p>
                    <p className="item-quantity">
                      Quantity: {orderItem.quantity}
                    </p>
                    <p className="item-price">Price: {orderItem.price}</p>
                  </li>
                ))}
              </ul>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
};

export default UserOrders;
