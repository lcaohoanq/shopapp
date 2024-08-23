package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    @JsonProperty("user_id")
    @Min(value = 1, message = "User ID is must be > 0")
    private Long userId;

    @JsonProperty("fullname")
    @NotBlank(message = "Full name is required")
    private String fullName;

    private String email;

    // do not need to be the same as the user's phone number
    // because the user can order for someone else
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 11, message = "Phone number must be between 10 and 11 characters")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("total_money")
    @Min(value= 0, message = "Total money must be >= 0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;
}

