package com.example.shopapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    PENDING(0),
    PROCESSING(1),
    SHIPPED(2),
    DELIVERED(3),
    CANCELLED(4);

    private final int value;

}
