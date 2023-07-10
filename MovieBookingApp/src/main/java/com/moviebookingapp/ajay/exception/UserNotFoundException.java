/**
 * Created by : Ajaymallesh
 * Date :10-07-2023
 * Time :13:40
 * ProjectName :MoviBookingApp
 */


package com.moviebookingapp.ajay.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}