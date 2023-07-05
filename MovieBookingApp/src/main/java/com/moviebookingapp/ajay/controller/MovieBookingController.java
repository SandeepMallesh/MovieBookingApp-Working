/**
 * Created by : Ajaymallesh
 * Date :06-07-2023
 * Time :10:00
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.controller;

import com.moviebookingapp.ajay.model.*;
import com.moviebookingapp.ajay.repository.MovieRepository;
import com.moviebookingapp.ajay.service.MovieService;
import com.moviebookingapp.ajay.service.TicketService;
import com.moviebookingapp.ajay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moviebookingapp.ajay.repository.MovieRepository;
import java.util.List;
import java.util.Optional;

// @Author : AjayMallesh

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class MovieBookingController {
    private final UserService userService;
    private final MovieService movieService;
    private final TicketService ticketService;
    @Autowired
    public MovieBookingController(UserService userService, MovieService movieService, TicketService ticketService) {
        this.userService = userService;
        this.movieService = movieService;
        this.ticketService = ticketService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{username}/forgot")
    public ResponseEntity<String> forgotPassword(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            // Generate and send password reset token to user's email
            // Your code to generate and send the password reset token

            return new ResponseEntity<>("Password reset instructions sent to your email", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    /*@PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie addedMovie = movieService.addMovie(movie);
        if (addedMovie != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovieWithTicketCount(@RequestBody Movie movie) {
        Movie addedMovie = movieService.addMovieWithTicketCount(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(movies);
        }
    }

    @GetMapping("/movies/search/{moviename}")
    public ResponseEntity<List<Movie>> searchMoviesByName(@PathVariable("moviename") String movieName) {
        List<Movie> movies = movieService.findMoviesByName(movieName);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(movies);
        }
    }

    @GetMapping("/{username}/logout")
    public String logOut(){
        return "LogOut Successful Thank You!!";
    }

    /*@PostMapping("/{moviename}/add")
    public ResponseEntity<Ticket> bookTickets(@PathVariable("moviename") String movieName, @RequestBody Ticket ticket) {
        ticket.setMovieName(movieName);
        Ticket bookedTicket = ticketService.bookTicket(ticket);
        return new ResponseEntity<>(bookedTicket, HttpStatus.CREATED);
    }*/

    // PUT Request: Update ticket status
    @PutMapping("/{moviename}/update/{ticket}")
    public ResponseEntity<Ticket> updateTicketStatus(@PathVariable("moviename") String movieName,
                                                     @PathVariable("ticket") String ticketId,
                                                     @RequestParam("status") String status) {
        // Retrieve the ticket by movie name and ticket id
        Optional<Ticket> optionalTicket = ticketService.getTicketByMovieNameAndId(movieName, ticketId);
        if (optionalTicket.isPresent()) {
            Ticket existingTicket = optionalTicket.get();
            // Update the ticket status
            existingTicket.setStatus(status);
            Ticket updatedTicket = ticketService.updateTicket(existingTicket);
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE Request: Delete movie by id
    @DeleteMapping("{moviename}/delete/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable("movieId") String movieId) {
        boolean deleted = movieService.deleteMovie(movieId);
        if (deleted) {
            return ResponseEntity.ok("Movie deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{movieName}/book")
    public ResponseEntity<?> bookTickets(@PathVariable("movieName") String movieName, @RequestBody Ticket ticket) {
        Optional<Movie> optionalMovie = movieService.findByName(movieName);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            int totalBookedTickets = movie.getTickets().stream()
                    .mapToInt(Ticket::getQuantity)
                    .sum();

            int availableTickets = movie.getTotalTickets() - totalBookedTickets;

            if (availableTickets <= 0) {
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

            // Update the available tickets count
            movie.setTotalTickets(movie.getTotalTickets() - requestedQuantity);

            // Save the updated movie
            movieService.updateMovie(movie);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedTicket);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }





}

