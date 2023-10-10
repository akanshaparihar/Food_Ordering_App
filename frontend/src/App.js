import React, { useEffect, useState } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import WelcomePage from "./pages/WelcomePage";
import Signup from "./components/Signup";
import Login from "./components/Login";
import CategoryFoodItems from "./components/CategoryFoodItems";
import Cart from "./components/Cart";
import UserOrders from "./components/UserOrders";

function App() {
  const [cart, setCart] = useState({});

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<WelcomePage />} />
          <Route path="user-orders" element={<UserOrders />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
          <Route
            path="/food-items/:categoryName"
            element={<CategoryFoodItems cart={cart} setCart={setCart} />}
          />
          <Route path="/cart" element={<Cart cart={cart} />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
