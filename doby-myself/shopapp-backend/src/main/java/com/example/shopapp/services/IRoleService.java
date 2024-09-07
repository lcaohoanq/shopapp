package com.example.shopapp.services;

import com.example.shopapp.models.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleService {

    List<Role> getAllRoles();

}
