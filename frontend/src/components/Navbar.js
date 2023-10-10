import React, { useState, useEffect } from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import Button from "@mui/material/Button";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { Link, useNavigate } from "react-router-dom";
import Hidden from "@mui/material/Hidden"; 

const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("userEmail") != null) {
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("userEmail");
    setIsLoggedIn(false);
    navigate("/");
  };

  const clickHandler = () => {
    navigate("/");
  };

  return (
    <AppBar position="static" style={{ padding: "20px", fontSize: "24px" }}>
      <Toolbar>
        <Typography
          variant="h4"
          component="div"
          onClick={clickHandler}
          style={{ cursor: "pointer" }}
          data-testid="test"
        >
          Food Ordering App
        </Typography>
        <Hidden smDown> {}
          <Button
            component={Link}
            to="/"
            color="inherit"
            style={{ marginLeft: "10px", fontSize: "18px" }}
          >
            Home
          </Button>
        </Hidden>
        {isLoggedIn ? (
          <Hidden smDown> {}
            <Button
              component={Link}
              to="/user-orders"
              color="inherit"
              style={{ marginLeft: "6px", fontSize: "18px" }}
            >
              Orders
            </Button>
          </Hidden>
        ) : (
          <></>
        )}
        <div style={{ marginLeft: "auto" }}>
          {isLoggedIn ? (
            <>
              <Button
                component={Link}
                color="inherit"
                onClick={handleLogout}
                style={{ fontSize: "18px" }}
              >
                Logout
              </Button>
              <IconButton component={Link} to="/cart" color="inherit">
                <ShoppingCartIcon style={{ fontSize: "28px" }} />
              </IconButton>
            </>
          ) : (
            <>
              <Button
                component={Link}
                to="/login"
                color="inherit"
                style={{ fontSize: "18px" }}
              >
                Login
              </Button>
              <Button
                component={Link}
                to="/signup"
                color="inherit"
                style={{ fontSize: "18px" }}
              >
                Signup
              </Button>
              <IconButton component={Link} to="/cart" color="inherit">
                <ShoppingCartIcon style={{ fontSize: "28px" }} />
              </IconButton>
            </>
          )}
        </div>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
