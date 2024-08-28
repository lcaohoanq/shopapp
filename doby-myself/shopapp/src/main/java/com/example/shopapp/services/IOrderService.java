package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.models.Order;
import java.util.List;

public interface IOrderService {
 Order createOrder(OrderDTO orderDTO) throws Exception;
 Order getOrder(Long id) throws DataNotFoundException;
 Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
 void deleteOrder(Long id);
 List<Order> findByUserId(Long userId);
}
