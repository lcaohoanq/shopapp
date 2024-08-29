package com.example.shopapp.responses;


import com.example.shopapp.models.OrderDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {

    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse
            .builder()
            .id(orderDetail.getId())
            .orderId(orderDetail.getOrder().getId())
            .productId(orderDetail.getProduct().getId())
            .price(orderDetail.getPrice())
            .numberOfProducts(orderDetail.getNumberOfProducts())
            .totalMoney(orderDetail.getTotalMoney())
            .color(orderDetail.getColor())
            .build();
    }

    /*
    {
    "id": 76,
    "color": "#FFFFFF",
    "order_id": 47,
    "product_id": 1,
    "price": 123456.0,
    "number_of_products": 12345,
    "total_money": 99999.0
    }
    */
}
