package com.kodilla.rental_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kodilla.rental_frontend.domain.enums.Currency;
import com.kodilla.rental_frontend.domain.enums.RentalStatus;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rental {
    private long rentalId;
    private Car car;
    private User user;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private Currency currency;
    private Double priceRate;
    private BigDecimal totalValue;
    private BigDecimal leftToPay;
    private RentalStatus rentalStatus;
    private LocalDate paymentDate;
}
