package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.repository.UserRepository;
import com.dungnt.healthclinic.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Long id = null;
        try {
            userService.findById(id);
        } catch (Exception e) {
            assertEquals("Gia tri id null", e.getMessage());
        }
    }

    @Test
    public void testFindByIdWhenTrue() throws Exception {
        Long id = 1L;
        User user = new User();
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(id)).thenReturn(optionalUser);
        Optional<User> userTest = userService.findById(id);
        assertNotNull(userTest);
    }

    @Test
    public void testFindAllByRoomWhenRoomIsNull() {
        String room = null;
        try {
            userService.findAllByRoom(room);
        } catch (Exception e) {
            assertEquals("Gia tri room null", e.getMessage());
        }
    }

    @Test
    public void testFindAllByRoomWhenTrue() throws Exception {
        String room = "301";
        List<User> users = new ArrayList<>();
        when(userRepository.findAllByRoom(room)).thenReturn(users);
        List<User> testUsers = userService.findAllByRoom(room);
        assertNotNull(testUsers);
    }


}