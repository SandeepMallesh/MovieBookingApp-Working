/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time : 20:00
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.service;
import com.moviebookingapp.ajay.model.User;
import com.moviebookingapp.ajay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
