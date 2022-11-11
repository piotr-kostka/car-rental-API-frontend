package com.kodilla.rental_frontend.service;

import com.kodilla.rental_frontend.config.BackEndConfig;
import com.kodilla.rental_frontend.domain.Manufacturer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static ManufacturerService manufacturerService;


    public static ManufacturerService getInstance() {
        if (manufacturerService == null) {
            manufacturerService = new ManufacturerService();
        }
        return manufacturerService;
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getUsers())
                .build()
                .encode()
                .toUri();
        return url;
    }

    public List<Manufacturer> getManufacturers() {
        URI url = getUrl();
        try {
            Manufacturer[] response = restTemplate.getForObject(url, Manufacturer[].class);
            return Arrays.asList(ofNullable(response).orElse(new Manufacturer[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Manufacturer getManufacturer(long manufacturerId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getUsers() + "/" + manufacturerId)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(url, Manufacturer.class);
        } catch (RestClientException e) {
            return new Manufacturer();
        }
    }

    public List<Manufacturer> findManufacturer(String manufacturer) {
        return getManufacturers().stream().filter(m -> m.getName().contains(manufacturer)).collect(Collectors.toList());
    }

    public Manufacturer createManufacturer(Manufacturer manufacturer) throws IOException {
        try {
            return restTemplate.postForObject(getUrl(), manufacturer, Manufacturer.class);
        } catch (RestClientException e) {
            return new Manufacturer();
        }
    }

    public void updateManufacturer(Manufacturer manufacturer) throws IOException {
        try {
            restTemplate.put(getUrl(), manufacturer);
        } catch (RestClientException e) {
        }
    }

    public void deleteManufacturer(long manufacturerId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getUsers() + "/" + manufacturerId)
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
        }
    }
}
