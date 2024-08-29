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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="fullname", columnDefinition = "VARCHAR(100)")
    private String fullName;

    @Column(name="email", columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name="phone_number", nullable = false, columnDefinition = "VARCHAR(20)")
    private String phoneNumber;

    @Column(name="address", columnDefinition = "VARCHAR(200)")
    private String address;

    @Column(name="note", columnDefinition = "VARCHAR(100)")
    private String note;

    @Column(name="order_date")
    private Date orderDate;

    @Column(name = "status")
    private String status;

    @Column(name="total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private float totalMoney;

    @Column(name="shipping_method", columnDefinition = "VARCHAR(100)")
    private String shippingMethod;

    @Column(name="shipping_address", columnDefinition = "VARCHAR(200)")
    private String shippingAddress;

    @Column(name="shipping_date")
    private LocalDate shippingDate;

    @Column(name="tracking_number", columnDefinition = "VARCHAR(100)")
    private String trackingNumber;

    @Column(name="payment_method", columnDefinition = "VARCHAR(100)")
    private String paymentMethod;

    @Column(name="active", columnDefinition = "TINYINT(1)")
    private Boolean isActive;
}
