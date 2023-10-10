import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { MemoryRouter } from 'react-router-dom';
import axios from 'axios';
import UserOrders from './UserOrders';

jest.mock('axios'); 

describe('UserOrders Component', () => {
  it('renders the UserOrders component', async () => {
    const mockUserOrders = [
      {
        id: 1,
        orderDate: [2023, 9, 15, 10, 30, 0], 
        totalPrice: 50.0,
        orderItems: [
          { id: 101, name: 'Item 1', quantity: 2, price: 10.0 },
          { id: 102, name: 'Item 2', quantity: 3, price: 15.0 },
        ],
      },
      d
    ];

    
    axios.get.mockResolvedValue({ data: mockUserOrders });

    render(
      <MemoryRouter>
        <UserOrders />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('Your Orders')).toBeInTheDocument();

      expect(screen.getByText('Order Date: 15/09/2023, 10:30:00')).toBeInTheDocument();
      expect(screen.getByText('Total Price: 50')).toBeInTheDocument();
      expect(screen.getByText('Item: Item 1')).toBeInTheDocument();
      expect(screen.getByText('Quantity: 2')).toBeInTheDocument();
      expect(screen.getByText('Price: 10')).toBeInTheDocument();
      expect(screen.getByText('Item: Item 2')).toBeInTheDocument();
      expect(screen.getByText('Quantity: 3')).toBeInTheDocument();
      expect(screen.getByText('Price: 15')).toBeInTheDocument();
    });
  });
});
