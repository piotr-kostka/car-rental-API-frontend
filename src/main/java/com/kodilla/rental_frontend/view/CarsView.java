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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cars", layout = MainLayout.class)
@PageTitle("Cars | Rent APP")
public class CarsView extends VerticalLayout {

    private final CarService carService = CarService.getInstance();
    private final Grid<Car> grid = new Grid<>(Car.class);
    private final TextField name = new TextField();
    private final TextField filter = new TextField();
    private final BigDecimalField priceFilter = new BigDecimalField();
    private final CarsForm form = new CarsForm(this);

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

        Button allCars = new Button("Show all cars");
        allCars.addClickListener(e -> grid.setItems(carService.getCars()));
        Button availableCars = new Button("Show available cars");
        availableCars.addClickListener(e -> grid.setItems(carService.getAvailableCars()));

        grid.setColumns("model", "licenseNumber", "price", "carStatus");

        Button addNewCar = new Button("Add new car");
        addNewCar.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCar(new Car());
        });

        HorizontalLayout toolbar = new HorizontalLayout(name, filter,priceFilter, allCars, availableCars, addNewCar);
        HorizontalLayout userContent = new HorizontalLayout(grid, form);
        userContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, userContent);
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
