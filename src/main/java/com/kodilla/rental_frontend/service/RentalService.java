package com.kodilla.rental_frontend.service;

import com.kodilla.rental_frontend.config.BackEndConfig;
import com.kodilla.rental_frontend.domain.Car;
import com.kodilla.rental_frontend.domain.Rental;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class RentalService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static RentalService rentalService;


    public static RentalService getInstance() {
        if (rentalService == null) {
            rentalService = new RentalService();
        }
        return rentalService;
    }

    private URI getUrl() {
        return UriComponentsBuilder.fromHttpUrl(BackEndConfig.getRentals())
                .build()
                .encode()
                .toUri();
    }

    public List<Rental> getRentals() {
        URI url = getUrl();
        try {
            Rental[] response = restTemplate.getForObject(url, Rental[].class);
            return Arrays.asList(ofNullable(response).orElse(new Rental[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Rental getRental(long rentalId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getRentals() + "/" + rentalId)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(url, Rental.class);
        } catch (RestClientException e) {
            return new Rental();
        }
    }

    public List<Rental> getUserRentals(long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getRentals() + "/user/" + userId)
                .build()
                .encode()
                .toUri();
        try {
            Rental[] response = restTemplate.getForObject(url, Rental[].class);
            return Arrays.asList(ofNullable(response).orElse(new Rental[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }
    public List<Rental> findByUserLastName(String lastName) {
        return getRentals().stream().filter(m -> m.getUser().getLastName().toLowerCase().contains(lastName)).collect(Collectors.toList());
    }

    public List<Rental> findByRentalId(long rentalId) {
        return getRentals().stream().filter(m -> m.getRentalId() == rentalId).collect(Collectors.toList());
    }

    public Rental createRental(Rental rental) throws IOException {
        try {
            return restTemplate.postForObject(getUrl(), rental, Rental.class);
        } catch (RestClientException e) {
            return new Rental();
        }
    }

    public void returnCar(long rentalId, Rental rental) throws IOException {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getRentals() + "/return/" + rentalId)
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.put(url, rental);
        } catch (RestClientException e) {
        }
    }

    public void makePayment(long rentalId, Rental rental) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getRentals() + "/pay/" + rentalId)
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.put(url, rental);
        } catch (RestClientException e) {
        }
    }
}
