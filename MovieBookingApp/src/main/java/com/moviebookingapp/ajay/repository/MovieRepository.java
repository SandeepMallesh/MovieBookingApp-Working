/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time : 21:00
 * ProjectName :MoviebookingApp
 */
package com.moviebookingapp.ajay.repository;

import com.moviebookingapp.ajay.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {
    //Movie findByName(String name);


    List<Movie> findByNameContainingIgnoreCase(String movieName);
   // Optional<Movie> findByName(String name);
    List<Movie> findByName(String name);



    Optional<Movie> findByNameAndId(String movieName, String id);
    Optional<Movie> findByMovieNameAndId(String movieName, String id);

}
