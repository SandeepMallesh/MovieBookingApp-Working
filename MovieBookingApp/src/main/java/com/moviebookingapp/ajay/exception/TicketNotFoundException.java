/**
 * Created by : Ajaymallesh
 * Date :10-07-2023
 * Time :13:41
 * ProjectName :MovieBookingApp
 */


package com.moviebookingapp.ajay.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
