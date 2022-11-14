package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.Car;
import com.kodilla.rental_frontend.form.CarForm;
import com.kodilla.rental_frontend.service.CarService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("cars")
public class CarView extends VerticalLayout {
    private CarService carService = CarService.getInstance();
    private Grid<Car> grid = new Grid<>(Car.class);
    private TextField filter = new TextField();
    private CarForm form = new CarForm(this);
    private Button addNewCar = new Button("Add new car");

    public CarView() {
        filter.setPlaceholder("Filter by car");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> updateCar());

        grid.setColumns("model", "licenseNumber", "price", "carStatus");

        addNewCar.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCar(new Car());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewCar);

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

    private void updateCar() {
        grid.setItems(carService.findLicenseNumber(filter.getValue()));
    }

}
