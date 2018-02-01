package com.myrecipestockpile.demo.services;

import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User getUser(String username) {
        return usersRepository.findDistinctByUsername(username);
    }
}
