import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../css/Login.css";
import Navbar from "./Navbar";
import axios from "axios";

const Login = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [formErrors, setFormErrors] = useState({});
  const [isFormSubmitted, setIsFormSubmitted] = useState(false);
  const [loginError, setLoginError] = useState("");
  const validateForm = () => {
    const errors = {};
    if (!formData.email.trim()) {
      errors.email = "Email is required";
    } else if (
      !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(formData.email)
    ) {
      errors.email = "Invalid email format";
    }
    if (!formData.password.trim()) {
      errors.password = "Password is required";
    } else if (formData.password.length < 6) {
      errors.password = "Password must be at least 6 characters long";
    }
    return errors;
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const errors = validateForm();
    setFormErrors(errors);
    setLoginError("");
    if (Object.keys(errors).length === 0) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/login",
          formData
        );

        if (response.status === 200) {
          var email = formData.email;
          localStorage.setItem("userEmail", email);
          setIsFormSubmitted(true);
          navigate("/");
        }
      } catch (error) {
        if (error.response) {
          if (error.response.status === 404) {
            setLoginError("Invalid email or password");
          } else {
            setLoginError("Server Error");
          }
        } else {
          setLoginError("Error");
        }
      }
    }
  };

  return (
    <div>
      <Navbar />
      <div className="login-container">
        <h2>Login</h2>
        {isFormSubmitted ? (
          <p className="success-message">Login Successful!</p>
        ) : (
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                data-testid="email-input"
              />
              {formErrors.email && <p className="error">{formErrors.email}</p>}
            </div>
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                data-testid="password-input"
              />
              {formErrors.password && (
                <p className="error">{formErrors.password}</p>
              )}
              {loginError && (
                <div className="alert-alert-danger">{loginError}</div>
              )}
            </div>
            <button type="submit" data-testid="login-button">Login</button>

            <p>
              Don't have an account? <Link to="/signup">Signup</Link>
            </p>
          </form>
        )}
      </div>
    </div>
  );
};

export default Login;
