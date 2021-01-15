package com.java.proiect;

import com.java.proiect.dto.UserUpdateDto;
import com.java.proiect.entity.User;
import com.java.proiect.exception.EntityNotFoundException;
import com.java.proiect.repository.UserRepository;
import com.java.proiect.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceT {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User expected;

    @Test
    @DisplayName("Create driver - happy flow")
    public void createClientServiceTestWhenClientPass() {
        //arrange
        User user = new User((long) 1, "username", "pass", "email@yahoo.com","fn","ln",20);
        User saveUser = new User((long) 1, "username", "pass", "email@yahoo.com","fn","ln",20);
        when(userRepository.createAndSaveUser(user)).thenReturn(saveUser);
        //act
        User result = userService.create(user);
        assertEquals(user.getId(),result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(),result.getPassword());
        assertEquals(user.getEmail(),result.getEmail());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertEquals(user.getLastname(), result.getLastname());
        assertEquals(user.getAge(),result.getAge());
        verify(userRepository).createAndSaveUser(any());
    }


    @Test
    @DisplayName("Get user - happy flow")
    void test_getById() {
        // Arrange
        Long id = 1L;
        User user = new User((long) 1, "username", "pass", "email@yahoo.com","fn","ln",20);
            when(userRepository.findby(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getbyid(id);

        //Assert
        assertEquals(user,result);
    }

    @DisplayName("Get user - sad flow")
    @Test
    void test_getById_throwsEntityNotFoundException_whenProductNotFound() {
        // Arrange
        Long id = 1L;

        when(userRepository.findby(id)).thenThrow(new EntityNotFoundException("User not found"));

        // Act
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                userService.getbyid(id)
        );

        // Assert
        assertEquals(exception.getMessage(),"User not found");
    }

}