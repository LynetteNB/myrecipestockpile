package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

    public User findByUsernameOrEmail(String username, String email);

    public User findDistinctByUsername(String username);
    
}

