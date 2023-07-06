/**
 * Created by : Ajaymallesh
 * Date :06-07-2023
 * Time :10:00
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.controller;

import com.moviebookingapp.ajay.model.*;
import com.moviebookingapp.ajay.service.MovieService;
import com.moviebookingapp.ajay.service.TicketService;
import com.moviebookingapp.ajay.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class MovieBookingController {
     Logger logger = LoggerFactory.getLogger(MovieBookingController.class);

     UserService userService;
    MovieService movieService;
    TicketService ticketService;

    @Autowired
    public MovieBookingController(UserService userService, MovieService movieService, TicketService ticketService) {
        this.userService = userService;
        this.movieService = movieService;
        this.ticketService = ticketService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        logger.info("Received registration request for user: {}", user.getUsername());

        User registeredUser = userService.registerUser(user);
        logger.info("User registered successfully: {}", registeredUser.getUsername());

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Received login request for username: {}", loginRequest.getUsername());

        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            logger.info("Login successful for username: {}", loginRequest.getUsername());
            return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
        } else {
            logger.info("Invalid username or password for username: {}", loginRequest.getUsername());
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{username}/forgot")
    public ResponseEntity<String> forgotPassword(@PathVariable("username") String username) {
        logger.info("Received forgot password request for username: {}", username);

        User user = userService.getUserByUsername(username);
        if (user != null) {
            // Generate and send password reset token to user's email
            // Your code to generate and send the password reset token

            logger.info("Password reset instructions sent to email for username: {}", username);
            return new ResponseEntity<>("Password reset instructions sent to your email", HttpStatus.OK);
        } else {
            logger.info("User not found for username: {}", username);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovieWithTicketCount(@RequestBody Movie movie) {
        logger.info("Received request to add movie with ticket count: {}", movie.getTitle());

        Movie addedMovie = movieService.addMovieWithTicketCount(movie);
        logger.info("Movie added successfully: {}", addedMovie.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        logger.info("Received request to get all movies");

        List<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            logger.info("No movies found");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Returning {} movies", movies.size());
            return ResponseEntity.ok(movies);
        }
    }

    @GetMapping("/movies/search/{moviename}")
    public ResponseEntity<List<Movie>> searchMoviesByName(@PathVariable("moviename") String movieName) {
        logger.info("Received search request for movies by name: {}", movieName);

        List<Movie> movies = movieService.findMoviesByName(movieName);
        if (movies.isEmpty()) {
            logger.info("No movies found with name: {}", movieName);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Returning {} movies with name: {}", movies.size(), movieName);
            return ResponseEntity.ok(movies);
        }
    }

    @GetMapping("/{username}/logout")
    public String logOut(){
        logger.info("Received logout request");

        return "LogOut Successful Thank You!!";
    }

    @PutMapping("/{moviename}/update/{ticket}")
    public ResponseEntity<Ticket> updateTicketStatus(@PathVariable("moviename") String movieName,
                                                     @PathVariable("ticket") String ticketId,
                                                     @RequestParam("status") String status) {
        logger.info("Received request to update ticket status for movie: {}, ticket: {}", movieName, ticketId);

        Optional<Ticket> optionalTicket = ticketService.getTicketByMovieNameAndId(movieName, ticketId);
        if (optionalTicket.isPresent()) {
            Ticket existingTicket = optionalTicket.get();
            existingTicket.setStatus(status);
            Ticket updatedTicket = ticketService.updateTicket(existingTicket);

            logger.info("Ticket status updated successfully for movie: {}, ticket: {}", movieName, ticketId);
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } else {
            logger.info("Ticket not found for movie: {}, ticket: {}", movieName, ticketId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{moviename}/delete/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable("movieId") String movieId) {
        logger.info("Received request to delete movie with ID: {}", movieId);

        boolean deleted = movieService.deleteMovie(movieId);
        if (deleted) {
            logger.info("Movie deleted successfully with ID: {}", movieId);
            return ResponseEntity.ok("Movie deleted successfully");
        } else {
            logger.info("Movie not found with ID: {}", movieId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{movieName}/book")
    public ResponseEntity<?> bookTickets(@PathVariable("movieName") String movieName, @RequestBody Ticket ticket) {
        logger.info("Received request to book tickets for movie: {}", movieName);

        Optional<Movie> optionalMovie = movieService.findByName(movieName);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            int totalBookedTickets = movie.getTickets().stream()
                    .mapToInt(Ticket::getQuantity)
                    .sum();

            int availableTickets = movie.getTotalTickets() - totalBookedTickets;

            if (availableTickets <= 0) {
                logger.info("No tickets available for movie: {}", movieName);

                TicketResponse ticketResponse = new TicketResponse();
                ticketResponse.setMovieName(movieName);
                ticketResponse.setTotalTicketsBooked(totalBookedTickets);
                ticketResponse.setTicketsAvailable(availableTickets);
                ticketResponse.setTicketStatus("SOLD OUT");
                ticketResponse.setMessage(null);

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ticketResponse);
            }

            int requestedQuantity = ticket.getQuantity();

            if (requestedQuantity > availableTickets) {
                logger.info("Insufficient tickets available for movie: {}", movieName);

                TicketResponse ticketResponse = new TicketResponse();
                ticketResponse.setMovieName(movieName);
                ticketResponse.setTotalTicketsBooked(totalBookedTickets);
                ticketResponse.setTicketsAvailable(availableTickets);
                ticketResponse.setTicketStatus("AVAILABLE");
                ticketResponse.setMessage("Insufficient tickets available");

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ticketResponse);
            }

            Ticket bookedTicket = new Ticket();
            bookedTicket.setMovieName(movieName);
            bookedTicket.setStatus("BOOKED");
            bookedTicket.setQuantity(requestedQuantity);

            Ticket savedTicket = ticketService.bookTicket(bookedTicket);

            movie.setTotalTickets(movie.getTotalTickets() - requestedQuantity);
            movieService.updateMovie(movie);

            logger.info("Tickets booked successfully for movie: {}, quantity: {}", movieName, requestedQuantity);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedTicket);
        } else {
            logger.info("Movie not found for booking tickets: {}", movieName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
