package com.myrecipestockpile.demo.services;

import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private UsersRepository usersRepository;

    public ProfileService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User findUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public User findById(long id) {
        return usersRepository.findOne(id);
    }
}
