package com.moviebookingapp.ajay.repository;

import com.moviebookingapp.ajay.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    Optional<Ticket> findByMovieNameAndId(String movieName, String ticketId);
    // Define any custom queries if needed
}
