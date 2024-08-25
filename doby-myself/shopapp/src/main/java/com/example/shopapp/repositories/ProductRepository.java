package com.example.shopapp.repositories;

import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(
        String name); //how can i have this function, the function will correspond to the name of the field, properties, or column in the database

    Page<Product> findAll(Pageable pageable); //pagination

}
