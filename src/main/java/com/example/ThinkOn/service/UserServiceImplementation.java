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
import java.util.OptionalInt;
import java.util.stream.Stream;

@Service
public class UserServiceImplementation implements UserService {

    // Path to JSON file acting as the DB
    private static final String USER_FILE = "users.json";
    private List<User> users = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    // Helper method for generating an ID
    // The idea is to either treat this as the first ID being generated
    // or take the last ID from the users array, and add 1 to it to create the new ID
    private Long generateId() {
        return users.isEmpty() ? 1L : users.get(users.size() - 1).getId() + 1;
    }

    private void loadUsers() {
        try {
            File file = new File(USER_FILE);
            if (file.exists()) {
                users = objectMapper.readValue(file, new TypeReference<List<User>>() {
                });
            }
        } catch (IOException e) {
            // Put logging here
            e.printStackTrace();
        }
    }

     private void saveUsers() {
        try {
            objectMapper.writeValue(new File(USER_FILE), users);

        } catch (IOException e) {
            // Put logging here
            e.printStackTrace();
        }
     }

    // Load all the users before performing any business logic
    public UserServiceImplementation() {
        loadUsers();
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
    @Override
    public List<User> getAllUsers() {
        return users;
    }

    // This method takes an input of the ID, and returns the first user found with that ID.
    // I need to add error handling in this, I'll add it later.
    @Override
    public Optional<User> getUserById(Long id) {
        Stream<User> usersStream = users.stream();
        return usersStream.filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOpt = getUserById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());

            saveUsers();

            return existingUser;
        } else {
            //Error handling required here, throw an exception rather
            return null;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> user = getUserById(id);
        if (user.isPresent()) {
            users.remove(user.get());
            saveUsers();
            return true;
        } else {
            return false;
        }
    }
}
