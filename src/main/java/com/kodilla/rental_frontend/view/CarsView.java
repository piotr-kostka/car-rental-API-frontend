package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.Car;
import com.kodilla.rental_frontend.form.CarsForm;
import com.kodilla.rental_frontend.service.CarService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("cars")
public class CarsView extends VerticalLayout {
    private CarService carService = CarService.getInstance();
    private Grid<Car> grid = new Grid<>(Car.class);
    private TextField name = new TextField();
    private TextField filter = new TextField();
    private BigDecimalField priceFilter = new BigDecimalField();

    private CarsForm form = new CarsForm(this);
    private Button addNewCar = new Button("Add new car");
    private Button allCars = new Button("Show all cars");
    private Button availableCars = new Button("Show available cars");
    private Button rentalsButton = new Button("Rentals");
    private Button usersButton = new Button("Users");
    private Button modelsButton = new Button("Models");
    private Button manufacturersButton = new Button("Manufacturers");

    public CarsView() {
        name.setPlaceholder("Filter by name");
        name.setClearButtonVisible(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(e -> updateByName());

        filter.setPlaceholder("Filter by license number");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> updateByLicenseNumber());

        priceFilter.setPlaceholder("Filter by price lower than");
        priceFilter.setClearButtonVisible(true);
        priceFilter.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilter.addValueChangeListener(e -> updateByPrice());

        allCars.addClickListener(e -> grid.setItems(carService.getCars()));
        availableCars.addClickListener(e -> grid.setItems(carService.getAvailableCars()));

        rentalsButton.addClickListener(e ->
                rentalsButton.getUI().ifPresent(ui ->
                        ui.navigate("rentals"))
        );
        usersButton.addClickListener(e ->
                usersButton.getUI().ifPresent(ui ->
                        ui.navigate("users"))
        );
        modelsButton.addClickListener(e ->
                modelsButton.getUI().ifPresent(ui ->
                        ui.navigate("models"))
        );
        manufacturersButton.addClickListener(e ->
                manufacturersButton.getUI().ifPresent(ui ->
                        ui.navigate("manufacturers"))
        );

        grid.setColumns("model", "licenseNumber", "price", "carStatus");

        addNewCar.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCar(new Car());
        });
        HorizontalLayout routes = new HorizontalLayout(rentalsButton, usersButton, modelsButton, manufacturersButton);
        HorizontalLayout toolbar = new HorizontalLayout(name, filter,priceFilter, allCars, availableCars, addNewCar);

        HorizontalLayout userContent = new HorizontalLayout(grid, form);
        userContent.setSizeFull();
        grid.setSizeFull();

        add(routes, toolbar, userContent);
        form.setCar(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setCar(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(carService.getCars());
    }

    private void updateByName() {
        grid.setItems(carService.findByModelName(name.getValue()));
    }

    private void updateByLicenseNumber() {
        grid.setItems(carService.findLicenseNumber(filter.getValue()));
    }

    private void updateByPrice() {
        grid.setItems(carService.getCarsByPrice(priceFilter.getValue()));
    }
}
