/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time :15:20
 * ProjectName :MoviebookingApp
 */
package com.moviebookingapp.ajay.model;
public class TicketResponse {
    private String movieName;
    private int totalTicketsBooked;
    private int ticketsAvailable;
    private String ticketStatus;
    private String message;


    // Getters and setters for the properties
    // Constructor(s) if needed


    public TicketResponse(String movieName, int totalTicketsBooked, int ticketsAvailable, String ticketStatus) {
        this.movieName = movieName;
        this.totalTicketsBooked = totalTicketsBooked;
        this.ticketsAvailable = ticketsAvailable;
        this.ticketStatus = ticketStatus;
    }

    public TicketResponse() {
    }

    public TicketResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getTotalTicketsBooked() {
        return totalTicketsBooked;
    }

    public void setTotalTicketsBooked(int totalTicketsBooked) {
        this.totalTicketsBooked = totalTicketsBooked;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
