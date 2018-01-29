package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    // Long is the data type for the primary key

    User findByUsername(String username);
}