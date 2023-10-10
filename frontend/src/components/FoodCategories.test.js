import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { MemoryRouter } from 'react-router-dom';
import axios from 'axios';
import FoodCategories from './FoodCategories';

jest.mock('axios'); 

describe('FoodCategories Component', () => {
  it('renders the FoodCategories component', async () => {
    const mockFoodCategories = [
      { id: 1, name: 'Chinese' },
      { id: 2, name: 'North Indian' },
      { id: 3, name: 'South Indian' },
      
    ];

   
    axios.get.mockResolvedValue({ data: mockFoodCategories });

    render(
      <MemoryRouter>
        <FoodCategories />
      </MemoryRouter>
    );

    
    await waitFor(() => {
      expect(screen.getByText('Food Categories')).toBeInTheDocument();

      expect(screen.getByText('Chinese')).toBeInTheDocument();
      expect(screen.getByText('North Indian')).toBeInTheDocument();
      expect(screen.getByText('South Indian')).toBeInTheDocument();

    });
  });

  it('handles API request error gracefully', async () => {
   
    axios.get.mockRejectedValue(new Error('API request failed'));

    render(
      <MemoryRouter>
        <FoodCategories />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText(/Error fetching food categories:/i)).toBeInTheDocument();
    });
  });
});
