package com.example.ThinkOn.service;

import com.example.ThinkOn.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImplementation implements UserService {

    // Path to JSON file acting as the DB
    private static final String USER_FILE = "users.json";
    private List<User> users = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    public UserServiceImplementation() {
        loadUsers();
    }

    // Helper method for generating an ID
    // The idea is to either treat this as the first ID being generated
    // or take the last ID from the users array, and add 1 to it to create the new ID
    private Long generateId() {
        return users.isEmpty() ? 1L : users.get(users.size() - 1).getId() + 1;
    }

    // This method generates an ID using the helper function,
    // adds it to the users array list,
    // saves it to the file
    // Returns the created user
    @Override
    public User createUser(User user) {
        user.setId(generateId());
        users.add(user);
        saveUsers();
        return user;
    }

    // This simply returns all the users present in the file
    public List<User> getAllUsers() {
        return users;
    }
}
