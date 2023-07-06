/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time : 20:00
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.service;
import com.moviebookingapp.ajay.model.User;
import com.moviebookingapp.ajay.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        logger.info("Registering user: {}", user);
        User registeredUser = userRepository.save(user);
        logger.info("User registered: {}", registeredUser);
        return registeredUser;
    }

    public User getUserByUsername(String username) {
        logger.info("Retrieving user by username: {}", username);
        User user = userRepository.findByUsername(username);
        logger.info("Retrieved user: {}", user);
        return user;
    }
}

