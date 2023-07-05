/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time :15:14
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String movieName;
    private String status;
    private int quantity;

    public Ticket() {

    }


    // Other ticket properties

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Constructors, getters, setters

    public Ticket(String id, String movieName, String status, int quantity) {
        this.id = id;
        this.movieName = movieName;
        this.status = status;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
