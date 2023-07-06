/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time :15:14
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequest {
    private String username;
    private String password;

    // Constructors, getters, setters

    Logger logger = LoggerFactory.getLogger(LoginRequest.class);



    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
        logger.info("LoginRequest created with username: {}", username);
    }


    public LoginRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}