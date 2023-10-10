import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Cart from './Cart';
import { MemoryRouter } from 'react-router-dom';
import axios from 'axios';
import { act } from 'react-dom/test-utils';

jest.mock('axios');

const localStorageMock = (() => {
  let store = {};

  return {
    getItem: (key) => store[key] || null,
    setItem: (key, value) => {
      store[key] = value.toString();
    },
    removeItem: (key) => {
      delete store[key];
    },
    clear: () => {
      store = {};
    },
  };
})();

beforeAll(() => {
  
  Object.defineProperty(window, 'localStorage', { value: localStorageMock });
});

afterEach(() => {
  
  localStorage.clear();
});

describe('Cart Component', () => {
  test('it renders cart items when logged in', () => {
    
    localStorage.setItem('userEmail', 'test@example.com');

   
    localStorage.setItem('cart', JSON.stringify({ item1: { id: 'item1', name: 'Item 1', price: 10, quantity: 2 } }));

    render(
      
      <MemoryRouter>
        <Cart />
      </MemoryRouter>
    );

   
    expect(screen.getByText('Shopping Cart')).toBeInTheDocument();
    expect(screen.getByText('Item 1')).toBeInTheDocument();
    expect(screen.getByText('Price: ₹10')).toBeInTheDocument();
  });

  test('it renders login message when not logged in', () => {
    render(
      
      <MemoryRouter>
        <Cart />
      </MemoryRouter>
    );

    
    expect(screen.getByText('Please login to view your cart :)')).toBeInTheDocument();
  });

  test('it updates item quantity when clicking buttons', () => {
    
    localStorage.setItem('userEmail', 'test@example.com');

    localStorage.setItem('cart', JSON.stringify({ item1: { id: 'item1', name: 'Item 1', price: 10, quantity: 2 } }));

    render(
     
      <MemoryRouter>
        <Cart />
      </MemoryRouter>
    );

    expect(screen.getByText('Quantity: 2')).toBeInTheDocument();

    fireEvent.click(screen.getByTestId('increase-button-item1'));

    expect(screen.getByText('Quantity: 3')).toBeInTheDocument();

    fireEvent.click(screen.getByText('-'));

    
    expect(screen.getByText('Quantity: 2')).toBeInTheDocument();
  });

  test('it places an order successfully when logged in', async () => {
    
    localStorage.setItem('userEmail', 'test@example.com');

    
    localStorage.setItem('cart', JSON.stringify({ item1: { id: 'item1', name: 'Item 1', price: 10, quantity: 2 } }));

    axios.post.mockResolvedValueOnce({ data: { orderId: '12345' } });

    const { container } = render(
      
      <MemoryRouter>
        <Cart />
      </MemoryRouter>
    );

    
    await act(async () => {
      fireEvent.click(screen.getByTestId('place-order-button'));
    });

  
    expect(localStorage.getItem('cart')).toBe(null);
    expect(localStorage.getItem('selectedItems')).toBe(null);
  });
});
