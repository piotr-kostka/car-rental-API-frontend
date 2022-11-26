package com.kodilla.rental_frontend.config;

public class BackEndConfig {

    private static final String BACKEND_ENDPOINT = "http://localhost:8080/v1/";
    private static final String MANUFACTURERS = "manufacturers";
    private static final String MODELS = "models";
    private static final String CARS = "cars";
    private static final String RENTALS = "rentals";
    private static final String USERS = "users";

    public static String getManufacturers() {
        return BACKEND_ENDPOINT + MANUFACTURERS;
    }

    public static String getModels() {
        return BACKEND_ENDPOINT + MODELS;
    }

    public static String getCars() {
        return BACKEND_ENDPOINT + CARS;
    }

    public static String getRentals() {
        return BACKEND_ENDPOINT + RENTALS;
    }

    public static String getUsers() {
        return BACKEND_ENDPOINT + USERS;
    }
}
