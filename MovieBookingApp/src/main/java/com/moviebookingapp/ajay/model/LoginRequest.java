/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time :15:14
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.model;

public class LoginRequest {
    private String username;
    private String password;

    // Constructors, getters, setters


    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
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