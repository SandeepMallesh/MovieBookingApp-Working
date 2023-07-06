/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time : 17 : 50
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.service;

import com.moviebookingapp.ajay.model.Ticket;
import com.moviebookingapp.ajay.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket bookTicket(Ticket ticket) {
        logger.info("Booking ticket: {}", ticket);
        Ticket bookedTicket = ticketRepository.save(ticket);
        logger.info("Ticket booked: {}", bookedTicket);
        return bookedTicket;
    }

    public Optional<Ticket> getTicketByMovieNameAndId(String movieName, String ticketId) {
        logger.info("Retrieving ticket by movie name: {} and ticket ID: {}", movieName, ticketId);
        Optional<Ticket> ticket = ticketRepository.findByMovieNameAndId(movieName, ticketId);
        logger.info("Retrieved ticket: {}", ticket.orElse(null));
        return ticket;
    }

    public Ticket updateTicket(Ticket ticket) {
        logger.info("Updating ticket: {}", ticket);
        Ticket updatedTicket = ticketRepository.save(ticket);
        logger.info("Ticket updated: {}", updatedTicket);
        return updatedTicket;
    }
}