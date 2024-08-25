package com.example.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name="full_name", columnDefinition = "VARCHAR(100)")
    private String fullName;

    @Column(name="email", columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name="phone_number", columnDefinition = "VARCHAR(20)")
    private String phoneNumber;

    @Column(name="address", columnDefinition = "VARCHAR(200)")
    private String address;

    @Column(name="note", columnDefinition = "VARCHAR(100)")
    private String note;

    @Column(name="order_date")
    private LocalDateTime orderDate;

    @Enumerated
    private Status status;

    @Column(name="total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private float totalMoney;

    @Column(name="shipping_method", columnDefinition = "VARCHAR(100)")
    private String shippingMethod;

    @Column(name="shipping_address", columnDefinition = "VARCHAR(200)")
    private String shippingAddress;

    @Column(name="shipping_date")
    private LocalDateTime shippingDate;

    @Column(name="tracking_number", columnDefinition = "VARCHAR(100)")
    private String trackingNumber;

    @Column(name="payment_method", columnDefinition = "VARCHAR(100)")
    private String paymentMethod;

    @Column(name="is_active", columnDefinition = "TINYINT(1)")
    private int isActive;
}
