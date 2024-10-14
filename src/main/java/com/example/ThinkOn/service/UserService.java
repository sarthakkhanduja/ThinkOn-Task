package com.example.ThinkOn.service;

import com.example.ThinkOn.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User updateUser(Long id, User user);
    boolean deleteUser(Long id);
}
