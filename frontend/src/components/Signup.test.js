import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';
import Signup from './Signup';

jest.mock('axios'); 

describe('Signup Component', () => {
    it('renders the Signup form', () => {
        render(
          <MemoryRouter>
            <Signup />
          </MemoryRouter>
        );
    
        expect(screen.getByText('Signup', { selector: 'h2' })).toBeInTheDocument();
        expect(screen.getByLabelText('First Name')).toBeInTheDocument();
        expect(screen.getByLabelText('Last Name')).toBeInTheDocument();
        expect(screen.getByLabelText('Email')).toBeInTheDocument();
        expect(screen.getByLabelText('Phone Number')).toBeInTheDocument();
        expect(screen.getByLabelText('Password')).toBeInTheDocument();
        expect(screen.getByText('Register')).toBeInTheDocument();
        expect(screen.getByText('Already have an account?')).toBeInTheDocument();
      });
    

  it('handles form submission successfully', async () => {
    axios.post.mockResolvedValueOnce({ data: 'Success' }); 

    render(
      <MemoryRouter>
        <Signup />
      </MemoryRouter>
    );

    const firstNameInput = screen.getByLabelText('First Name');
    const lastNameInput = screen.getByLabelText('Last Name');
    const emailInput = screen.getByLabelText('Email');
    const phoneNumberInput = screen.getByLabelText('Phone Number');
    const passwordInput = screen.getByLabelText('Password');
    const registerButton = screen.getByText('Register');

    fireEvent.change(firstNameInput, { target: { value: 'John' } });
    fireEvent.change(lastNameInput, { target: { value: 'Doe' } });
    fireEvent.change(emailInput, { target: { value: 'john@example.com' } });
    fireEvent.change(phoneNumberInput, { target: { value: '1234567890' } });
    fireEvent.change(passwordInput, { target: { value: 'password' } });

    fireEvent.click(registerButton);

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/api/signup', {
        email: 'john@example.com',
        password: 'password',
      });
      expect(screen.getByText('Signup Successful! You can now log in.')).toBeInTheDocument();
    });
  });

  it('handles form submission with validation errors', async () => {
    render(
      <MemoryRouter>
        <Signup />
      </MemoryRouter>
    );

    const registerButton = screen.getByText('Register');

    fireEvent.click(registerButton);

    await waitFor(() => {
      expect(screen.getByText('First Name is required')).toBeInTheDocument();
      expect(screen.getByText('Last Name is required')).toBeInTheDocument();
      expect(screen.getByText('Email is required')).toBeInTheDocument();
      expect(screen.getByText('Phone Number is required')).toBeInTheDocument();
      expect(screen.getByText('Password is required')).toBeInTheDocument();
    });
  });

});
