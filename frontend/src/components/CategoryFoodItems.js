import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import "../css/CategoryFoodItems.css";
import Navbar from "./Navbar";

const CategoryFoodItems = () => {
  const { categoryName } = useParams();
  const [foodItems, setFoodItems] = useState([]);
  const [cart, setCart] = useState({});
  const [selectedItems, setSelectedItems] = useState({});
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const storedCart = JSON.parse(localStorage.getItem("cart")) || {};
    const storedSelectedItems =
      JSON.parse(localStorage.getItem("selectedItems")) || {};

    setCart(storedCart);
    setSelectedItems(storedSelectedItems);

    if (localStorage.getItem("userEmail") != null) {
      setIsLoggedIn(true);
    }

    const fetchFoodItemsByCategory = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/food-items/by-category/${categoryName}`
        );
        setFoodItems(response.data);
      } catch (error) {
        console.error(
          `Error fetching food items for category: ${categoryName}`,
          error
        );
      }
    };

    fetchFoodItemsByCategory();
  }, [categoryName]);

  const updateSelectedItems = (foodItemId, quantity) => {
    const updatedItems = { ...selectedItems };
    if (quantity > 0) {
      updatedItems[foodItemId] = {
        ...foodItems.find((item) => item.id === foodItemId),
        quantity,
      };
    } else {
      delete updatedItems[foodItemId];
    }
    setSelectedItems(updatedItems);

    localStorage.setItem("selectedItems", JSON.stringify(updatedItems));
  };

  const calculateTotalPrice = () => {
    return Object.values(selectedItems).reduce((total, item) => {
      return total + item.price * item.quantity;
    }, 0);
  };

  const calculateItemTotalPrice = (foodItemId) => {
    const selectedItem = selectedItems[foodItemId];
    if (selectedItem) {
      return selectedItem.price * selectedItem.quantity;
    }
    return 0;
  };

  const handleAddToCartClick = () => {
    if (!isLoggedIn) {
      alert("To add items to the cart, please log in first.");
      navigate("/login");
      return;
    }

    const updatedCart = { ...cart, ...selectedItems };
    setCart(updatedCart);

    localStorage.setItem("cart", JSON.stringify(updatedCart));

    navigate("/cart");
  };

  return (
    <>
      <Navbar cartItemCount={Object.keys(cart).length} />
      <div className="category-food-items">
      <h1>Food Items in Category: {categoryName}</h1>

        <ul className="food-list">
          {foodItems.map((foodItem) => (
            <li key={foodItem.id} className="food-card">
              <div className="food-details">
                <h4>{foodItem.name}</h4>
                <p>{foodItem.description}</p>
                <p className="price">Price: ₹{foodItem.price}</p>
                {selectedItems[foodItem.id]?.quantity > 0 && (
                  <p className="item-total-price">
                    Total: ₹{calculateItemTotalPrice(foodItem.id)}
                  </p>
                )}
              </div>
              <div className="food-image-and-quantity">
              <img
                src={foodItem.imageUrl}
                alt={foodItem.name}
                className="food-image"
              />
              <div className="quantity-and-price">
                <div className="quantity-controls">
                  <button
                    className="quantity-button"
                    onClick={() =>
                      updateSelectedItems(
                        foodItem.id,
                        (selectedItems[foodItem.id]?.quantity || 0) - 1
                      )
                    }
                  >
                    -
                  </button>
                  <span className="quantity">
                    {selectedItems[foodItem.id]?.quantity || 0}
                  </span>
                  <button
                    className="quantity-button"
                    data-testid={`add-button-${foodItem.id}`}
                    onClick={() =>
                      updateSelectedItems(
                        foodItem.id,
                        (selectedItems[foodItem.id]?.quantity || 0) + 1
                      )
                    }
                  >
                    +
                  </button>
                </div>
                
              </div>
              </div>
            </li>
          ))}
        </ul>
        {Object.keys(selectedItems).length > 0 && (
          <button className="add-to-cart-button" onClick={handleAddToCartClick}>
            ADD TO CART - ₹{calculateTotalPrice()}
          </button>
        )}
      </div>
    </>
  );
};

export default CategoryFoodItems;
