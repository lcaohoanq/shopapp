package com.example.shopapp.services;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.models.User;

public interface IUserService {

    User createUser(UserDTO user) throws Exception;
    String login(String phoneNumber, String password) throws Exception;

}
