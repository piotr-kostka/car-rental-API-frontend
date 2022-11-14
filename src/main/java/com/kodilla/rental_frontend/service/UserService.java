package com.kodilla.rental_frontend.service;

import com.kodilla.rental_frontend.config.BackEndConfig;
import com.kodilla.rental_frontend.domain.User;
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

public class UserService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static UserService userService;


    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private URI getUrl() {
        return UriComponentsBuilder.fromHttpUrl(BackEndConfig.getUsers())
                .build()
                .encode()
                .toUri();
    }

    public List<User> getUsers() {
        URI url = getUrl();
        try {
            User[] response = restTemplate.getForObject(url, User[].class);
            return Arrays.asList(ofNullable(response).orElse(new User[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public User getUser(long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getUsers() + "/" + userId)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(url, User.class);
        } catch (RestClientException e) {
            return new User();
        }
    }

    public List<User> findByLastName(String lastName) {
        return getUsers().stream().filter(user -> user.getLastName().toLowerCase().contains(lastName)).collect(Collectors.toList());
    }

    public List<User> findByFirstName(String firstName) {
        return getUsers().stream().filter(user -> user.getFirstName().toLowerCase().contains(firstName)).collect(Collectors.toList());
    }

    public User createUser(User user) throws IOException {
        try {
            return restTemplate.postForObject(getUrl(), user, User.class);
        } catch (RestClientException e) {
            return new User();
        }
    }

    public void updateUser(User user) throws IOException {
        try {
            restTemplate.put(getUrl(), user);
        } catch (RestClientException e) {
        }
    }

    public void deleteUser(long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getUsers() + "/" + userId)
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
        }
    }
}
