package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.Rental;
import com.kodilla.rental_frontend.form.RentalForm;
import com.kodilla.rental_frontend.service.RentalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("rentals")
public class RentalView extends VerticalLayout {

    private RentalService rentalService = RentalService.getInstance();
    private Grid<Rental> grid = new Grid<>(Rental.class);
    private TextField filter = new TextField();
    private RentalForm form = new RentalForm(this);
    private Button addNewRental = new Button("Add new rental");

    public RentalView() {
        filter.setPlaceholder("Filter by rental number");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> updateRental());

        grid.setColumns("rentalId", "car", "user", "rentDate", "returnDate", "currency", "priceRate", "totalValue", "leftToPay", "rentalStatus", "paymentDate");

        addNewRental.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setRental(new Rental());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewRental);

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

    private void updateRental() {
        grid.setItems(rentalService.findByRentalId(Long.parseLong(filter.getValue())));
    }
}
