package com.example.shopapp.services;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.models.User;

public interface IUserService {

    User createUser(UserDTO user) throws DataNotFoundException;
    String login(String phoneNumber, String password);

}
