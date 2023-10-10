import axios from "axios";
import React, { useState, useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import "../css/Cart.css";
import Navbar from "./Navbar";

const Cart = () => {
  const storedCart = JSON.parse(localStorage.getItem("cart")) || {};
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const [cart, setCart] = useState(storedCart);

  const navigate = useNavigate();

  const calculateItemTotal = (item) => {
    return item.price * item.quantity;
  };

  const updateQuantity = (itemId, newQuantity) => {
    const updatedCart = { ...storedCart };
    if (newQuantity > 0) {
      updatedCart[itemId].quantity = newQuantity;
    } else {
      delete updatedCart[itemId];
    }
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    setCart(updatedCart);
  };

  const clearCart = () => {
    localStorage.removeItem("cart");
    setCart({});
  };

  const calculateGrandTotal = () => {
    const itemArray = Object.values(storedCart);
    return itemArray.reduce(
      (total, item) => total + calculateItemTotal(item),
      0
    );
  };

  useEffect(() => {
    if (localStorage.getItem("userEmail") != null) {
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }

    if (JSON.stringify(cart) !== JSON.stringify(storedCart)) {
      setCart(storedCart);
    }
  }, [storedCart, cart]);

  const placeOrder = async () => {
    if (!isLoggedIn) {
      alert("To place order, please log in first.");
      navigate("/login");
      return;
    }

    try {
      const orderRequest = {
        orderDate: new Date(),
        userEmail: localStorage.getItem("userEmail"),
        orderItemsList: Object.values(cart).map((item) => ({
          name: item.name,
          quantity: item.quantity,
        })),
        totalPrice: calculateGrandTotal(),
      };
      const response = await axios.post(
        "http://localhost:8080/api/orders",
        orderRequest
      );

      clearCart();
      alert("Order Placed Successfully!!");
      localStorage.removeItem("selectedItems");
    } catch (error) {
      console.error("Error placing order:", error);
    }
  };

  return (
    <>
      <Navbar />
      <div className="cart">
        <h1>Shopping Cart</h1>
        {isLoggedIn ? (
          <>
            <ul>
              {Object.values(cart).map((item) => (
                <li key={item.id} className="cart-item">
                  <p>{item.name}</p>
                  <p>Price: ₹{item.price}</p>
                  <div className="quantity-controls">
                    <button
                      onClick={() => updateQuantity(item.id, item.quantity - 1)}
                      className="quantity-button"
                    >
                      -
                    </button>
                    <span className="quantity">Quantity: {item.quantity}</span>
                    <button
                      onClick={() => updateQuantity(item.id, item.quantity + 1)}
                      className="quantity-button"
                      data-testid="increase-button-item1"
                    >
                      +
                    </button>
                  </div>
                  <p>Total Price: ₹{calculateItemTotal(item)}</p>
                </li>
              ))}
            </ul>
            {Object.keys(cart).length > 0 && (
              <div className="grand-total">
                <span className="total-label">Grand Total:</span>
                <span className="total-amount">₹{calculateGrandTotal()}</span>
              </div>
            )}

            <div className="button-container">
              {Object.keys(cart).length > 0 && (
                <button
                  onClick={placeOrder}
                  className="place-order-button"
                  data-testid="place-order-button"
                >
                  PLACE ORDER
                </button>
              )}
              {Object.keys(cart).length > 0 && (
                <button
                  onClick={clearCart}
                  className="clear-cart-button"
                  data-testid="clear-cart-button"
                >
                  CLEAR CART
                </button>
              )}
              {Object.keys(cart).length == 0 && (
                <h4 className="flavor-text">
                  Your food cart is feeling a bit light, time to add some
                  flavor!
                </h4>
              )}
            </div>
          </>
        ) : (
          <h4 className="flavor-text">
                  Please login to view your cart :)
                </h4>
        )}
      </div>
    </>
  );
};

export default Cart;
