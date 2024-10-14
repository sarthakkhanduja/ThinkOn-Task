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

}
