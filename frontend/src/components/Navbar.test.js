import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect"; 
import Navbar from "./Navbar";
import { MemoryRouter, useNavigate } from "react-router-dom";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: jest.fn(),
}));

const navigate = useNavigate();

describe("Navbar Component", () => {
  beforeEach(() => {
   
    jest.clearAllMocks();
  });

  it("renders without errors", () => {
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );
    const title = screen.getByText("Food Ordering App");
    expect(title).toBeInTheDocument();
  });


  it('displays "Home" button and "Login" button when not logged in', () => {
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );
    expect(screen.getByText("Home")).toBeInTheDocument();
    expect(screen.getByText("Login")).toBeInTheDocument();
  });

  it('displays "Orders", "Logout", and "Cart" buttons when logged in', () => {
   
    Object.defineProperty(window, "localStorage", {
      value: {
        getItem: jest.fn(() => "user@example.com"), 
      },
      writable: true,
    });

    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );
    expect(screen.getByText("Home")).toBeInTheDocument();
    expect(screen.getByText("Orders")).toBeInTheDocument();
    expect(screen.getByText("Logout")).toBeInTheDocument();
  });

});
