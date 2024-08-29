package com.example.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
@Entity
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="token",  length = 255)
    private String token;

    @Column(name="token_type", length = 50)
    private String tokenType;

    @Column(name="expiration_date")
    private LocalDateTime expiryDate;

    @Column(name="revoked")
    private int revoked;

    @Column(name="expired")
    private int expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name="is_mobile")
    private int isMobile;

    @Column(name="refresh_token", columnDefinition = "VARCHAR(255)")
    private String refreshToken;

    @Column(name="refresh_expiration_date")
    private LocalDateTime refreshExpirationDate;

}
