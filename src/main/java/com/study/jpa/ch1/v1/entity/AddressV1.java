package com.study.jpa.ch1.v1.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AddressV1 {
    private String city;
    private String street;
    private String zipcode;
}
