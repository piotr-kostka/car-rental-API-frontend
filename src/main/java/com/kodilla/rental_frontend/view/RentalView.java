package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.Rental;
import com.kodilla.rental_frontend.form.RentalForm;
import com.kodilla.rental_frontend.service.RentalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "rentals", layout = MainLayout.class)
@PageTitle("Rentals | Rent APP")
public class RentalView extends VerticalLayout {

    private final RentalService rentalService = RentalService.getInstance();
    private final Grid<Rental> grid = new Grid<>(Rental.class);
    private final NumberField filter = new NumberField();
    private final TextField userFilter = new TextField();
    private final RentalForm form = new RentalForm(this);

    public RentalView() {
        filter.setPlaceholder("Filter by rental number");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> updateRentalById());

        userFilter.setPlaceholder("Filter by user");
        userFilter.setClearButtonVisible(true);
        userFilter.setValueChangeMode(ValueChangeMode.EAGER);
        userFilter.addValueChangeListener(e -> updateByUser());

        Button allRentals = new Button("Show all rentals");
        allRentals.addClickListener(e -> refresh());

        grid.setColumns("rentalId", "rentalStatus", "user", "car", "rentDate", "priceRate", "returnDate", "totalValue", "currency", "leftToPay", "paymentDate");

        Button addNewRental = new Button("Add new rental");
        addNewRental.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setRental(new Rental());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filter, userFilter, allRentals, addNewRental);
        HorizontalLayout userContent = new HorizontalLayout(grid, form);
        userContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, userContent);
        form.setRental(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setRental(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(rentalService.getRentals());
    }

    private void updateRentalById() {
        grid.setItems(rentalService.findByRentalId(filter.getValue().longValue()));
    }

    private void updateByUser() {
        grid.setItems(rentalService.findByUser(userFilter.getValue()));
    }
}