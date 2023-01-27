package com.LibraryApplication.Library.Management.System.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        User user = new User(1, "John Doe", "johndoe@example.com", "password");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
    }

    @Test
    public void testCreateUser() {
        User user = new User(1, "John Doe", "johndoe@example.com", "password");
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.createUser(user));
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1, "Jane Doe", "janedoe@example.com", "password123");
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.updateUser(1L, user));
    }
}