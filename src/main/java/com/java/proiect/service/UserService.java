package com.java.proiect.service;

import com.java.proiect.dto.UserLoginDto;
import com.java.proiect.dto.UserUpdateDto;
import com.java.proiect.entity.User;
import com.java.proiect.exception.EntityNotFoundException;
import com.java.proiect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public User create(User user){
        return userRepository.createAndSaveUser(user);
    }

    public User getbyid(Long id)
    {
        return userRepository.findby(id).orElseThrow(() -> new EntityNotFoundException(String.format("No user exist with id %s", id.toString())));
    }

    public List<User> getAll() {

        return userRepository.findall();
    }

    public void update(UserUpdateDto user, Long id) {
        userRepository.updateUser(user,id);
    }

    public void delete(Long id) {
        userRepository.deleteUser(id);
    }

    public User Login(UserLoginDto userLoginDto)
    {
        return userRepository.Login(userLoginDto).orElseThrow(() -> new EntityNotFoundException(String.format("Username sau parola incorecta!")));
    }
}
