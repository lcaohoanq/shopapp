package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
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
public class OrderDetailDTO {

    //id will be generated automatically

    @JsonProperty("order_id")
    @Min(value = 1, message = "Order ID must be > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be > 0")
    private Long productId;

    @Min(value = 0, message = "Price must be >= 0")
    private Long price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "Number of products must be > 0")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Long totalMoney;

    private String color;

}
