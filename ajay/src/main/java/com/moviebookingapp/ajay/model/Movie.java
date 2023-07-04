package com.moviebookingapp.ajay.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "movies")
public class Movie {
    private String id;
    private String name;
    private List<Ticket> tickets = new ArrayList<>();

    // Constructor, getters, setters, and other class members


    public Movie(String id, String name, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.tickets = tickets;
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

    public void setTickets(List<String> tickets) {
    }
}


