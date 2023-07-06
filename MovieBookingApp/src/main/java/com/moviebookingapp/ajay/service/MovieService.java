package com.moviebookingapp.ajay.service;

import com.moviebookingapp.ajay.model.Movie;
import com.moviebookingapp.ajay.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        LOGGER.info("Fetching all movies");
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieByNameAndId(String movieName, String id) {
        LOGGER.info("Fetching movie by name: {} and id: {}", movieName, id);
        return movieRepository.findByMovieNameAndId(movieName, id);
    }

    public List<Movie> searchMoviesByName(String movieName) {
        LOGGER.info("Searching movies by name: {}", movieName);
        return movieRepository.findByNameContainingIgnoreCase(movieName);
    }

    public Movie saveMovie(Movie movie) {
        LOGGER.info("Saving movie: {}", movie);
        return movieRepository.save(movie);
    }

    public boolean deleteMovie(String movieId) {
        LOGGER.info("Deleting movie by id: {}", movieId);
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            movieRepository.deleteById(movieId);
            return true;
        } else {
            return false;
        }
    }

    public Movie addMovie(Movie movie) {
        LOGGER.info("Adding movie: {}", movie);
        return movieRepository.save(movie);
    }

    public boolean deleteMovieByNameAndId(String movieName, String id) {
        LOGGER.info("Deleting movie by name: {} and id: {}", movieName, id);
        Optional<Movie> optionalMovie = movieRepository.findByNameAndId(movieName, id);
        if (optionalMovie.isPresent()) {
            movieRepository.delete(optionalMovie.get());
            return true;
        } else {
            return false;
        }
    }

    public Movie addMovieWithTicketCount(Movie movie) {
        LOGGER.info("Adding movie with ticket count: {}", movie);
        return movieRepository.save(movie);
    }

    public void updateTicketStatus(Movie movie) {
        LOGGER.info("Updating ticket status for movie: {}", movie.getName());
        int ticketCount = movie.getTicketCount();
        if (ticketCount == 0) {
            movie.setTicketStatus("SOLD OUT");
        } else {
            movie.setTicketStatus("BOOK ASAP");
        }
        movieRepository.save(movie);
    }

    public Optional<Movie> findByName(String name) {
        LOGGER.info("Searching for movie by name: {}", name);
        List<Movie> movies = movieRepository.findByName(name);
        if (!movies.isEmpty()) {
            LOGGER.info("Found movie by name: {}", name);
            return Optional.of(movies.get(0));
        } else {
            LOGGER.info("No movie found by name: {}", name);
            return Optional.empty();
        }
    }

    public Movie updateMovie(Movie movie) {
        LOGGER.info("Updating movie: {}", movie);
        Movie updatedMovie = movieRepository.save(movie);
        LOGGER.info("Movie updated: {}", updatedMovie);
        return updatedMovie;
    }

    public List<Movie> findMoviesByName(String movieName) {
        LOGGER.info("Finding movies by name: {}", movieName);
        return movieRepository.findByName(movieName);
    }
}


