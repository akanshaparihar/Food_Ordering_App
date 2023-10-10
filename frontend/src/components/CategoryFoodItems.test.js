import React from "react";
import { render, fireEvent, screen, waitFor } from "@testing-library/react";
import axios from "axios";
import { MemoryRouter, Route } from "react-router-dom";
import CategoryFoodItems from "./CategoryFoodItems";

jest.mock("axios");

describe("CategoryFoodItems Component", () => {
  beforeEach(() => {
    
    localStorage.clear();
  });

  it("renders food items in a category", async () => {
    
    const mockFoodItems = [
      { id: 1, name: "Food 1", description: "Description 1", price: 10 },
      { id: 2, name: "Food 2", description: "Description 2", price: 15 },
    ];
    axios.get.mockResolvedValue({ data: mockFoodItems });

    const categoryName = "CategoryName";

    const { getByText } = render(
      <MemoryRouter initialEntries={[`/category/${categoryName}`]}>
        <CategoryFoodItems />
      </MemoryRouter>
    );

    
    await waitFor(() => {
      expect(getByText("Food 1")).toBeInTheDocument();
      expect(getByText("Description 1")).toBeInTheDocument();
      expect(getByText("Price: ₹10")).toBeInTheDocument();

      expect(getByText("Food 2")).toBeInTheDocument();
      expect(getByText("Description 2")).toBeInTheDocument();
      expect(getByText("Price: ₹15")).toBeInTheDocument();
    });
  });

  it("adds food items to selected items and cart when logged in", async () => {
   
    const mockFoodItems = [
      { id: 1, name: "Food 1", description: "Description 1", price: 10 },
      { id: 2, name: "Food 2", description: "Description 2", price: 15 },
    ];
    axios.get.mockResolvedValue({ data: mockFoodItems });

    const mockNavigate = jest.fn();

    const categoryName = "CategoryName";

    const { getByText, getByTestId } = render(
      <MemoryRouter initialEntries={[`/category/${categoryName}`]}>
          <CategoryFoodItems navigate={mockNavigate} />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(getByText("Food 1")).toBeInTheDocument();
    });

    localStorage.setItem("userEmail", "test@example.com");
    const foodItem = { id: 1 };

    const addButton = getByTestId(`add-button-${foodItem.id}`);
    fireEvent.click(addButton);

    expect(getByText("Food 1")).toBeInTheDocument();
    expect(getByText("Price: ₹10")).toBeInTheDocument();

    const addToCartButton = getByText("ADD TO CART - ₹10");
    fireEvent.click(addToCartButton);
  });

});
