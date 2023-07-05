package com.moviebookingapp.ajay.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "movies")
public class Movie {
    private String id;
    private String name;
    private int ticketCount;
    private int quantity;
    private int totalTickets;

    private String ticketStatus; // New field for ticket status

    private List<Ticket> tickets = new ArrayList<>();

    public Movie() {
        // Initialize the tickets list
        this.tickets = new ArrayList<>();
    }

    // Constructor, getters, setters, and other class members


    public Movie(String id, String name, int ticketCount, int quantity, String ticketStatus, List<Ticket> tickets, String movieName) {
        this.id = id;
        this.name = name;
        this.ticketCount = ticketCount;
        this.quantity = quantity;
        this.ticketStatus = ticketStatus;
        this.tickets = tickets;
        this.movieName = movieName;
    }

    @Field("movieName")
    private String movieName;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTickets(List<String> tickets) {
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    public String getTicketStatus() {
        return ticketStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getAvailableTickets() {
        int totalBookedQuantity = tickets.stream()
                .mapToInt(Ticket::getQuantity)
                .sum();
        return totalTickets - totalBookedQuantity;
    }

}


