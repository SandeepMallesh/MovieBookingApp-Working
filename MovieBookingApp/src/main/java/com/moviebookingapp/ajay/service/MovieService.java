/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time :17:00
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.service;
import com.moviebookingapp.ajay.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.moviebookingapp.ajay.model.Movie;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    /*public Optional<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    } */

    public List<Movie> findMoviesByName(String movieName) {
        return movieRepository.findByName(movieName);
    }

    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }


    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }


    public List<Movie> searchMoviesByName(String movieName) {
        return movieRepository.findByNameContainingIgnoreCase(movieName);

        // Implement the search logic based on the movie name
        // For example: movieRepository.findByNameContaining(movieName);
        // Return the list of matching movies
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public boolean deleteMovie(String movieId) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            movieRepository.deleteById(movieId);
            return true;
        } else {
            return false;
        }
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    private List<String> generateTickets(Movie movie) {
        List<String> tickets = new ArrayList<>();

        // Generate tickets for the movie

        // Add generated tickets to the movie
        movie.setTickets(tickets);

        // Return the generated tickets
        return tickets;
    }

    public Optional<Movie> getMovieByNameAndId(String movieName, String id) {
        return movieRepository.findByMovieNameAndId(movieName, id);
    }

    /*public Ticket updateTicketStatus(String movieName, String ticketId, String newStatus) {
        // Retrieve the movie by name
        Movie movie = movieRepository.findByName(movieName);
        if (movie == null) {
            return null; // Movie not found


        // Retrieve the ticket by ID
        Ticket ticket = movie.getTickets().stream()
                .filter(t -> t.getId().equals(ticketId))
                .findFirst()
                .orElse(null);
        if (ticket == null) {
            return null; // Ticket not found
        }

        // Update the ticket status
        ticket.setStatus(newStatus);

        // Save the updated movie
        movieRepository.save(movie);

        return ticket;
    }*/
    public boolean deleteMovieByNameAndId(String movieName, String id) {
        Optional<Movie> optionalMovie = movieRepository.findByNameAndId(movieName, id);
        if (optionalMovie.isPresent()) {
            movieRepository.delete(optionalMovie.get());
            return true;
        } else {
            return false;
        }
    }
    public Movie addMovieWithTicketCount(Movie movie) {
        return movieRepository.save(movie);
    }


    public void updateTicketStatus(Movie movie) {
        int ticketCount = movie.getTicketCount();

        if (ticketCount == 0) {
            movie.setTicketStatus("SOLD OUT");
        } else {
            movie.setTicketStatus("BOOK ASAP");
        }

        // Update the movie in the database
        movieRepository.save(movie);
    }


    public Optional<Movie> findByName(String name) {
        List<Movie> movies = movieRepository.findByName(name);
        if (!movies.isEmpty()) {
            return Optional.of(movies.get(0));
        } else {
            return Optional.empty();
        }
    }
}
