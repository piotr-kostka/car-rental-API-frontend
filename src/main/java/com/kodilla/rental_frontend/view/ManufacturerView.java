package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.Manufacturer;
import com.kodilla.rental_frontend.form.ManufacturerForm;
import com.kodilla.rental_frontend.service.ManufacturerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("manufacturers")
public class ManufacturerView extends VerticalLayout {

    private ManufacturerService manufacturerService = ManufacturerService.getInstance();
    private Grid<Manufacturer> grid = new Grid<>(Manufacturer.class);
    private TextField filter = new TextField();
    private ManufacturerForm form = new ManufacturerForm(this);
    private Button addNewManufacturer = new Button("Add new manufacturer");

    public ManufacturerView() {
        filter.setPlaceholder("Filter by manufacturer");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        grid.setColumns("name");

        addNewManufacturer.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setManufacturer(new Manufacturer());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewManufacturer);

        HorizontalLayout manufacturerContent = new HorizontalLayout(grid, form);
        manufacturerContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, manufacturerContent);
        form.setManufacturer(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setManufacturer(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(manufacturerService.getManufacturers());
    }

    private void update() {
        grid.setItems(manufacturerService.findManufacturer(filter.getValue()));
    }
}
