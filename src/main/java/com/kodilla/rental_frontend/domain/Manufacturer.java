package com.kodilla.rental_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Manufacturer {

    private long manufacturerId;
    private String name;

}
