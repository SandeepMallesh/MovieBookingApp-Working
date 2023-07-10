/**
 * Created by : Ajaymallesh
 * Date :10-07-2023
 * Time :13:38
 * ProjectName :MovieBookingApp
 */


package com.moviebookingapp.ajay.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
