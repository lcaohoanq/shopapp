package com.example.shopapp.controllers;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.dtos.UserLoginDTO;
import com.example.shopapp.services.IUserService;
import com.example.shopapp.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    @Autowired
    private IUserService userService;
    //why need to inject an interface rather than the implementation?
    //loose coupling, if we want to change the implementation of the service, we just need to change the implementation of the service and not the controller

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
        @Valid @RequestBody UserDTO userDTO,
        BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<FieldError> fieldErrorList = result.getFieldErrors();
                List<String> errorMessages = fieldErrorList
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return  ResponseEntity.badRequest().body("Password and Retype Password must be the same");
            };
            userService.createUser(userDTO);
            return ResponseEntity.ok("Register Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userDTO) {

        String token = userService.login(userDTO.getPhoneNumber(), userDTO.getPassword());

        return ResponseEntity.ok("Some token");
    }


}
