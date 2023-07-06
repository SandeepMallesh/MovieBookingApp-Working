/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time :18:08
 * ProjectName :MovieBookingApp
 */


package com.moviebookingapp.ajay;

import static org.junit.jupiter.api.Assertions.*;

import com.moviebookingapp.ajay.controller.MovieBookingController;
import com.moviebookingapp.ajay.model.LoginRequest;
import com.moviebookingapp.ajay.model.Movie;
import com.moviebookingapp.ajay.model.Ticket;
import com.moviebookingapp.ajay.model.User;
import com.moviebookingapp.ajay.service.MovieService;
import com.moviebookingapp.ajay.service.TicketService;
import com.moviebookingapp.ajay.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class MovieBookingControllerTest {

    @Mock
    UserService userService;

    @Mock
    MovieService movieService;

    @Mock
    TicketService ticketService;

    @InjectMocks
    MovieBookingController movieBookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case for addMovieWithTicketCount method
    @Test
    public void testAddMovieWithTicketCount() {
        // Create a movie object
        Movie movie = new Movie();
        movie.setName("Test Movie");
        movie.setTicketCount(100);

        // Mock the MovieService's addMovieWithTicketCount method
        when(movieService.addMovieWithTicketCount(movie)).thenReturn(movie);

        // Add the movie with ticket count
        ResponseEntity<Movie> response = movieBookingController.addMovieWithTicketCount(movie);

        // Verify that the MovieService's addMovieWithTicketCount method was called
        verify(movieService, times(1)).addMovieWithTicketCount(movie);

        // Verify the response status and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movie, response.getBody());
    }

    // Test case for getAllMovies method
    @Test
    public void testGetAllMovies() {
        // Create a list of movies
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie 1", 100));
        movies.add(new Movie("Movie 2", 200));

        // Mock the MovieService's getAllMovies method
        when(movieService.getAllMovies()).thenReturn(movies);

        // Get all movies
        ResponseEntity<List<Movie>> response = movieBookingController.getAllMovies();

        // Verify that the MovieService's getAllMovies method was called
        verify(movieService, times(1)).getAllMovies();

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    // Test case for searchMoviesByName method
    @Test
    public void testSearchMoviesByName() {
        // Create a list of movies
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie 1", 100));
        movies.add(new Movie("Movie 2", 200));

        // Mock the MovieService's findMoviesByName method
        when(movieService.findMoviesByName("Movie")).thenReturn(movies);

        // Search movies by name
        ResponseEntity<List<Movie>> response = movieBookingController.searchMoviesByName("Movie");

        // Verify that the MovieService's findMoviesByName method was called
        verify(movieService, times(1)).findMoviesByName("Movie");

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    // Test case for deleteMovie method
    @Test
    public void testDeleteMovie() {
        // Mock the MovieService's deleteMovie method
        when(movieService.deleteMovie("123")).thenReturn(true);

        // Delete a movie
        ResponseEntity<String> response = movieBookingController.deleteMovie("123");

        // Verify that the MovieService's deleteMovie method was called
        verify(movieService, times(1)).deleteMovie("123");

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movie deleted successfully", response.getBody());
    }

    // Test case for bookTickets method
    @Test
    public void testBookTickets() {
        // Create a movie object
        Movie movie = new Movie();
        movie.setName("Test Movie");
        movie.setTotalTickets(100);

        // Create a ticket object
        Ticket ticket = new Ticket();
        ticket.setQuantity(5);

        // Mock the MovieService's findByName method
        when(movieService.findByName("Test Movie")).thenReturn(Optional.of(movie));

        // Mock the TicketService's bookTicket method
        when(ticketService.bookTicket(any(Ticket.class))).thenReturn(ticket);

        // Book tickets
        ResponseEntity<?> response = movieBookingController.bookTickets("Test Movie", ticket);

        // Verify that the MovieService's findByName method was called
        verify(movieService, times(1)).findByName("Test Movie");

        // Verify that the TicketService's bookTicket method was called
        verify(ticketService, times(1)).bookTicket(any(Ticket.class));

        // Verify the response status and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    public void registerUser_ValidUser_ReturnsRegisteredUser() {
        // Arrange
        User user = new User();
        when(userService.registerUser(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<User> response = movieBookingController.registerUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void registerUser_NullUser_ReturnsInternalServerError() {
        // Arrange
        User user = null; // User object is null intentionally for the test case

        // Act
        ResponseEntity<User> responseEntity = movieBookingController.registerUser(user);

        // Print the actual response for debugging
        System.out.println("Actual Status Code: " + responseEntity.getStatusCodeValue());
        System.out.println("Actual Response Body: " + responseEntity.getBody());

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }


    @Test
    public void login_ValidCredentials_ReturnsLoggedInSuccessfully() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        when(userService.getUserByUsername("testuser")).thenReturn(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        // Act
        ResponseEntity<String> response = movieBookingController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in successfully", response.getBody());
    }

    @Test
    public void login_InvalidCredentials_ReturnsUnauthorized() {
        // Arrange
        when(userService.getUserByUsername("testuser")).thenReturn(null);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        // Act
        ResponseEntity<String> response = movieBookingController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    // Additional test cases for other methods in MovieBookingController
}
