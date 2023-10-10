
import axios from "axios";
import React, { useState } from "react";
import {Link, useNavigate } from "react-router-dom";
import "../css/Signup.css";
import Navbar from "./Navbar";

function Signup() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    password: "",
  });
  const navigate = useNavigate();

  const [formErrors, setFormErrors] = useState({});
  const [isFormSubmitted, setIsFormSubmitted] = useState(false);

  const validateForm = () => {
    const errors = {};
    const nameRegex = /^[a-zA-Z]*$/; 
  
    if (!formData.firstName.trim()) {
      errors.firstName = "First Name is required";
    } else if (!nameRegex.test(formData.firstName)) {
      errors.firstName = "First Name must contain only alphabets";
    }
  
    if (!formData.lastName.trim()) {
      errors.lastName = "Last Name is required";
    } else if (!nameRegex.test(formData.lastName)) {
      errors.lastName = "Last Name must contain only alphabets";
    }
  
    if (!formData.email.trim()) {
      errors.email = "Email is required";
    } else if (
      !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(formData.email)
    ) {
      errors.email = "Invalid email format";
    }
  
    if (!formData.phoneNumber.trim()) {
      errors.phoneNumber = "Phone Number is required";
    } else if (!/^\d{10}$/.test(formData.phoneNumber)) {
      errors.phoneNumber = "Phone Number must be a 10-digit number";
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
    if (Object.keys(errors).length === 0) {
      try {
        const response = await axios.post("http://localhost:8080/api/signup", {
          email: formData.email,
          password: formData.password,
          firstName: formData.firstName,
          lastName: formData.lastName,
          phoneNo: formData.phoneNumber,
        });
        console.log("Signup successful!", response.data);
      } catch (error) {
        if (error.response) {
      
          console.error(
            "Server Error:",
            error.response.status,
            error.response.data
          );
        } else if (error.request) {
         
          console.error("No response received from the server.");
        } else {
          
          console.error("Error setting up the request:", error.message);
        }
      }
      navigate("/login");
      setIsFormSubmitted(true);
    }
  };

  return (
    <>
      <Navbar />
      <div className="signup-container">
        <h2 className="signup-title">Signup</h2>
        {isFormSubmitted ? (
          <p className="success-message">
            Signup Successful! You can now log in.
          </p>
        ) : (
          <form className="signup-form" onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="firstName" className="signup-label">
                First Name
              </label>
              <input
                type="text"
                id="firstName"
                name="firstName"
                value={formData.firstName}
                onChange={handleInputChange}
                className="signup-input"
              />
              {formErrors.firstName && (
                <p className="error-message">{formErrors.firstName}</p>
              )}
            </div>
            <div className="form-group">
              <label htmlFor="lastName" className="signup-label">
                Last Name
              </label>
              <input
                type="text"
                id="lastName"
                name="lastName"
                value={formData.lastName}
                onChange={handleInputChange}
                className="signup-input"
              />
              {formErrors.lastName && (
                <p className="error-message">{formErrors.lastName}</p>
              )}
            </div>
            <div className="form-group">
              <label htmlFor="email" className="signup-label">
                Email
              </label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                className="signup-input"
              />
              {formErrors.email && (
                <p className="error-message">{formErrors.email}</p>
              )}
            </div>
            <div className="form-group">
              <label htmlFor="phoneNumber" className="signup-label">
                Phone Number
              </label>
              <input
                type="tel"
                id="phoneNumber"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleInputChange}
                className="signup-input"
              />
              {formErrors.phoneNumber && (
                <p className="error-message">{formErrors.phoneNumber}</p>
              )}
            </div>
            <div className="form-group">
              <label htmlFor="password" className="signup-label">
                Password
              </label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                className="signup-input"
              />
              {formErrors.password && (
                <p className="error-message">{formErrors.password}</p>
              )}
            </div>
            <button type="submit" className="signup-button">
              Register
            </button>
            <p>
              Already have an account? <Link to="/login">Login</Link>
            </p>
          </form>
        )}
      </div>
    </>
  );
}

export default Signup;
