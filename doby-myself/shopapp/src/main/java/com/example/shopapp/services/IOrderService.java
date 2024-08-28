package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.models.Order;
import java.util.List;

public interface IOrderService {
 Order createOrder(OrderDTO orderDTO) throws Exception;
 Order getOrder(Long id);
 Order updateOrder(Long id, OrderDTO orderDTO);
 void deleteOrder(Long id);
 List<Order> getAllOrders(Long userId);
}
