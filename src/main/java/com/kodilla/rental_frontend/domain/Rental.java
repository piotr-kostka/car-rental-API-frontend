//package com.kodilla.rental_frontend.domain;
//
//import com.kodilla.rental_frontend.domain.enums.Currency;
//import com.kodilla.rental_frontend.domain.enums.RentalStatus;
//import lombok.*;
//
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//@AllArgsConstructor
//@NoArgsConstructor
//public class Rental {
//    private Car car;
//    private User user;
//    private LocalDate rentDate;
//    private LocalDate returnDate;
//    @Enumerated(EnumType.STRING)
//    private Currency currency;
//    private Double priceRate;
//    private BigDecimal totalValue;
//    private BigDecimal leftToPay;
//    @Enumerated(EnumType.STRING)
//    private RentalStatus rentalStatus;
//    private LocalDate paymentDate;
//}
