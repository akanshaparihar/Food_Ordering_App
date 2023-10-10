package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.exception.UserAlreadyExistsException;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.model.LoginForm;
import com.example.backend.model.RegisterRequest;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(RegisterRequest request) {
        String email = request.getEmail();
        String plainPassword = request.getPassword();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String phoneNo = request.getPhoneNo();

        if (userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));

        User savedUser = new User();
        savedUser.setEmail(email);
        savedUser.setPassword(hashedPassword);
        savedUser.setFirstName(firstName);
        savedUser.setLastName(lastName);
        savedUser.setPhoneNumber(phoneNo);

        userRepository.save(savedUser);
    }

    public User loginUser(LoginForm loginForm) {
        String email = loginForm.getEmail();
        String plainPassword = loginForm.getPassword();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User Not Found");

        }

        String hashedPassword = user.getPassword();

        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            return user;
        } else {
            throw new UserNotFoundException("Wrong Password");
        }
    }

}
