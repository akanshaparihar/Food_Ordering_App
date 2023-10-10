import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';
import Login from './Login';


jest.mock('axios');

describe('Login Component', () => {
 
  const successfulResponse = {
    status: 200,
  };

  
  const invalidCredentialsError = {
    response: {
      status: 404,
    },
  };

  const serverError = {
    response: {
      status: 500,
    },
  };

  const renderLoginComponent = () => {
    return render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );
  };

  it('renders without errors', () => {
    renderLoginComponent();
    const loginHeader = screen.getByRole('heading', { name: 'Login' });
    expect(loginHeader).toBeInTheDocument();
  });
  

  it('submits the form with valid credentials', async () => {
    axios.post.mockResolvedValue(successfulResponse);

    renderLoginComponent();

    const emailInput = screen.getByTestId('email-input');
    const passwordInput = screen.getByTestId('password-input');
    const loginButton = screen.getByTestId('login-button');

    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });

    fireEvent.click(loginButton);

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith(
        'http://localhost:8080/api/login',
        {
          email: 'test@example.com',
          password: 'password123',
        }
      );
      expect(screen.getByText('Login Successful!')).toBeInTheDocument();
    });
  });

  it('displays an error message for invalid credentials', async () => {
    axios.post.mockRejectedValue(invalidCredentialsError);

    renderLoginComponent();

    const emailInput = screen.getByTestId('email-input');
    const passwordInput = screen.getByTestId('password-input');
    const loginButton = screen.getByTestId('login-button');

    fireEvent.change(emailInput, { target: { value: 'invalid@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'invalidpassword' } });

    fireEvent.click(loginButton);

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith(
        'http://localhost:8080/api/login',
        {
          email: 'invalid@example.com',
          password: 'invalidpassword',
        }
      );
      expect(screen.getByText('Invalid email or password')).toBeInTheDocument();
    });
  });

  it('displays an error message for server error', async () => {
    axios.post.mockRejectedValue(serverError);

    renderLoginComponent();

    const emailInput = screen.getByTestId('email-input');
    const passwordInput = screen.getByTestId('password-input');
    const loginButton = screen.getByTestId('login-button');

    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });

    fireEvent.click(loginButton);

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith(
        'http://localhost:8080/api/login',
        {
          email: 'test@example.com',
          password: 'password123',
        }
      );
      expect(screen.getByText('Server Error')).toBeInTheDocument();
    });
  });
});
