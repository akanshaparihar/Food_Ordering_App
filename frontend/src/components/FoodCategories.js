import React, { useState, useEffect } from "react";
import axios from "axios";
import "../css/FoodCategories.css";
import { useNavigate } from "react-router-dom";

const FoodCategories = () => {
  const [foodCategories, setFoodCategories] = useState([]);
  const [error, setError] = useState(null); 
  const navigate = useNavigate();

  const fetchFoodCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/food-categories"
      );
      setFoodCategories(response.data);
    } catch (error) {
      console.error("Error fetching food categories:", error);
      setError(error); 
    }
  };


  useEffect(() => {
    fetchFoodCategories();
  }, []); 

  const handleCategoryClick = (categoryName) => {
    const encodedCategoryName = encodeURIComponent(categoryName);
    navigate(`/food-items/${encodedCategoryName}`);
  };

  return (
    <div className="food-categories">
      <h2>Food Categories</h2>
      {error ? ( 
        <div className="error-message">
          Error fetching food categories: {error.message}
        </div>
      ) : (
        <div>
          <div className="category-row">
            {foodCategories.slice(0, 3).map((category) => (
              <div
                key={category.id}
                className="category-item"
                onClick={() => handleCategoryClick(category.name)}
              >
                <img
                  src={category.image}
                  alt={category.name}
                  className="category-image"
                />
                <span className="category-name">{category.name}</span>
              </div>
            ))}
          </div>
          <div className="category-row">
            {foodCategories.slice(3, 6).map((category) => (
              <div
                key={category.id}
                className="category-item"
                onClick={() => handleCategoryClick(category.name)}
              >
                <img
                
                 src={category.image}
                  alt={category.name}
                  className="category-image"
                />
                <span className="category-name">{category.name}</span>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default FoodCategories;
