package com.moviebookingapp.ajay.controller;

import com.moviebookingapp.ajay.model.LoginRequest;
import com.moviebookingapp.ajay.model.Movie;
import com.moviebookingapp.ajay.model.Ticket;
import com.moviebookingapp.ajay.model.User;
import com.moviebookingapp.ajay.service.MovieService;
import com.moviebookingapp.ajay.service.TicketService;
import com.moviebookingapp.ajay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie addedMovie = movieService.addMovie(movie);
        if (addedMovie != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
        List<Movie> movies = movieService.searchMoviesByName(movieName);
        if (!movies.isEmpty()) {
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{username}/logout")
    public String logOut(){
        return "LogOut Successful Thank You!!";
    }

    @PostMapping("/{moviename}/add")
    public ResponseEntity<Ticket> bookTickets(@PathVariable("moviename") String movieName, @RequestBody Ticket ticket) {
        ticket.setMovieName(movieName);
        Ticket bookedTicket = ticketService.bookTicket(ticket);
        return new ResponseEntity<>(bookedTicket, HttpStatus.CREATED);
    }

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
}

