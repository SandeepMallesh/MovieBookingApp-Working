/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time : 17 : 50
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.service;

import com.moviebookingapp.ajay.model.Ticket;
import com.moviebookingapp.ajay.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket bookTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> getTicketByMovieNameAndId(String movieName, String ticketId) {
        return ticketRepository.findByMovieNameAndId(movieName, ticketId);

        // Implement the retrieval logic based on movie name and ticket id
        // For example: ticketRepository.findByMovieNameAndId(movieName, ticketId);
        // Return an optional ticket
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}