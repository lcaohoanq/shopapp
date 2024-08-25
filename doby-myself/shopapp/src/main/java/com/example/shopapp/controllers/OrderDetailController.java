package com.example.shopapp.controllers;

import com.example.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("${api.prefix}/orders_details")
public class OrderDetailController {

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @RequestBody @Valid OrderDetailDTO orderDetailDTO,
            BindingResult result){
        try{
            if(result.hasErrors()){
                List<FieldError> fieldErrorList = result.getFieldErrors();
                List<String> errorMessages = fieldErrorList
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Order detail created successfully");
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred while creating the order detail");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable Long id){
        try{
            return ResponseEntity.ok("Order detail with id: "+id);
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred while fetching the order detail");
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable Long orderId){
        try{
            return ResponseEntity.ok("Order detail with order id: "+orderId);
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred while fetching the order detail");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @PathVariable Long id,
            @RequestBody @Valid OrderDetailDTO orderDetailDTO,
            BindingResult result){
        try{
            if(result.hasErrors()){
                List<FieldError> fieldErrorList = result.getFieldErrors();
                List<String> errorMessages = fieldErrorList
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Order detail updated successfully");
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred while updating the order detail");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id){
        try{
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred while deleting the order detail");
        }
    }

}
