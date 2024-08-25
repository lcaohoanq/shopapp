package com.example.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name="fullname", columnDefinition = "VARCHAR(100)")
    private String fullName;

    @Column(name="phone_number", columnDefinition = "VARCHAR(15)")
    private String phoneNumber;

    @Column(name="address", columnDefinition = "VARCHAR(60)")
    private String address;

    @Column(name="password", columnDefinition = "CHAR(60)")
    private String password;

    @Column(name="is_active", columnDefinition = "TINYINT(1)")
    private int isActive;

    @Column(name="date_of_birth")
    private Date dob;

    @Column(name="facebook_account_id", columnDefinition = "INT(11)")
    private int facebookId;

    @Column(name="google_account_id", columnDefinition = "INT(11)")
    private int googleId;

    @Column(name="role_id", columnDefinition = "INT(11)")
    private int roleId;

}
