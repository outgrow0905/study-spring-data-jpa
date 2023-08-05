package com.study.jpa.v1.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class AddressV1 {
    private String city;
    private String street;
    private String zipcode;
}
