package com.kodilla.rental_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kodilla.rental_frontend.domain.enums.BodyType;
import com.kodilla.rental_frontend.domain.enums.FuelType;
import com.kodilla.rental_frontend.domain.enums.TransmissionType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Model {
    private long modelId;
    private Manufacturer manufacturer;
    private String name;
    private double engineSize;
    private BodyType bodyType;
    private int productionYear;
    private String color;
    private int seatsQuantity;
    private int doorQuantity;
    private FuelType fuelType;
    private TransmissionType transmissionType;

    @Override
    public String toString() {
        return manufacturer + " " + name + " (" + productionYear + ")";
    }
}
