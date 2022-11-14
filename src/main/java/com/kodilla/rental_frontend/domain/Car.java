package com.kodilla.rental_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kodilla.rental_frontend.domain.enums.CarStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car {
    private long carId;
    private Model model;
    private String licenseNumber;
    private BigDecimal price;
    private CarStatus carStatus;

    @Override
    public String toString() {
        return model + " - " + licenseNumber;
    }
}
