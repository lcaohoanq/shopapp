package com.example.shopapp.repositories;

import com.example.shopapp.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    //user with .isPresent() to check if the user exists or .isEmpty() to check if the user does not exist
    Optional<User> findByPhoneNumber(String phoneNumber);

}
