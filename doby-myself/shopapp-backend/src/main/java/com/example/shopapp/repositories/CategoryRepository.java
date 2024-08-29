package com.example.shopapp.repositories;

import com.example.shopapp.models.Category;
import com.example.shopapp.models.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
}
