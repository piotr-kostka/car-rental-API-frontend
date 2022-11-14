package com.kodilla.rental_frontend.service;

import com.kodilla.rental_frontend.config.BackEndConfig;
import com.kodilla.rental_frontend.domain.Car;

import com.kodilla.rental_frontend.domain.Model;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class CarService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static CarService carService;


    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService();
        }
        return carService;
    }


    private URI getUrl() {
        return UriComponentsBuilder.fromHttpUrl(BackEndConfig.getCars())
                .build()
                .encode()
                .toUri();
    }

    public List<Car> getCars() {
        URI url = getUrl();
        try {
            Car[] response = restTemplate.getForObject(url, Car[].class);
            return Arrays.asList(ofNullable(response).orElse(new Car[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Car getCar(long carId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getCars() + "/" + carId)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(url, Car.class);
        } catch (RestClientException e) {
            return new Car();
        }
    }

    public List<Car> getCarsByManufacturer(long manufacturerId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getCars() + "/manufacturer/" + manufacturerId)
                .build()
                .encode()
                .toUri();
        try {
            Car[] response = restTemplate.getForObject(url, Car[].class);
            return Arrays.asList(ofNullable(response).orElse(new Car[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<Car> getCarsByModel(long modelId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getCars() + "/model/" + modelId)
                .build()
                .encode()
                .toUri();
        try {
            Car[] response = restTemplate.getForObject(url, Car[].class);
            return Arrays.asList(ofNullable(response).orElse(new Car[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<Car> getCarsByPrice(BigDecimal price) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getCars() + "/price/" + price)
                .build()
                .encode()
                .toUri();
        try {
            Car[] response = restTemplate.getForObject(url, Car[].class);
            return Arrays.asList(ofNullable(response).orElse(new Car[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<Car> getAvailableCars() {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getCars() + "/available/")
                .build()
                .encode()
                .toUri();
        try {
            Car[] response = restTemplate.getForObject(url, Car[].class);
            return Arrays.asList(ofNullable(response).orElse(new Car[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<Car> findLicenseNumber(String licenseNumber) {
        return getCars().stream().filter(m -> m.getLicenseNumber().toLowerCase().contains(licenseNumber)).collect(Collectors.toList());
    }

    public Car createCar(Car car) throws IOException {
        try {
            return restTemplate.postForObject(getUrl(), car, Car.class);
        } catch (RestClientException e) {
            return new Car();
        }
    }

    public void updateCar(Car car) throws IOException {
        try {
            restTemplate.put(getUrl(), car);
        } catch (RestClientException e) {
        }
    }

    public void deleteCar(long carId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getCars() + "/" + carId)
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
        }
    }
}
