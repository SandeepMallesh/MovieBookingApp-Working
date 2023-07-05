/**
 * Created by : Ajaymallesh
 * Date :05-07-2023
 * Time :21:50
 * ProjectName :MoviebookingApp
 */

package com.moviebookingapp.ajay.repository;

import com.moviebookingapp.ajay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
