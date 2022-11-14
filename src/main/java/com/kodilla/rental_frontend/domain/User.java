package com.kodilla.rental_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private long userId;
    private String firstName;
    private String lastName;
    private long pesel;
    private String address;
    private String mail;
    private String password;
    private String creditCardNo;
    private BigDecimal toPay;
    private LocalDate signupDate;

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + pesel + ")";
    }
}
