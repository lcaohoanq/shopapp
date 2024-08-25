package com.example.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="fullname", length = 100)
    private String fullName;

    @Column(name="phone_number", nullable = false,  length = 10)
    private String phoneNumber;

    @Column(name="address", length = 200)
    private String address;

    @Column(name="password", length = 200)
    private String password;

    @Column(name="is_active", columnDefinition = "TINYINT(1)")
    private boolean isActive;

    @Column(name="date_of_birth")
    private Date dob;

    @Column(name="facebook_account_id", columnDefinition = "INT(11)")
    private int facebookAccountId;

    @Column(name="google_account_id", columnDefinition = "INT(11)")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role roleId;

}
