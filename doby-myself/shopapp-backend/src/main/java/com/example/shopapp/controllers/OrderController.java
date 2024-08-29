package com.example.shopapp.controllers;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOder(
        @RequestBody @Valid OrderDTO orderDTO,
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
            Order orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while creating the order");
        }
    }

    //GET /orders/123?userId=456
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getOrderByUserId(@Valid @RequestParam Long userId){
//        try{
//            return ResponseEntity.ok("Order with id: "+userId);
//        }catch (Exception e){
//            return ResponseEntity.status(500).body("An error occurred while fetching the order");
//        }
//    }

    //GET: /orders/{user_id}  ->  /orders/123
    //PathVariable is used to extract the value from the URI
    /*
        if the name of the path variable is the same as the name of the parameter
        then you can omit the name of the path variable

        @GetMapping("/users/{id}")
        public ResponseEntity<?> getUserById(@PathVariable Long id) {
            return ResponseEntity.ok("User with id: " + id);
        }
    */
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrderByUserId(@Valid @PathVariable("user_id") Long userId) {
        try {
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the orders");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            Order existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok(existingOrder);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the orders");
        }
    }

    //PUT /api/v1/orders/123
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
        @Valid @PathVariable Long id,
        @Valid @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while updating the order");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
        //xoa mem, update truong active = false
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order with id: " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the order");
        }
    }

}
