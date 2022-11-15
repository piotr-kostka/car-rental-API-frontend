package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.Model;
import com.kodilla.rental_frontend.domain.User;
import com.kodilla.rental_frontend.form.ModelForm;
import com.kodilla.rental_frontend.service.ModelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("models")
public class ModelView extends VerticalLayout {

    private ModelService modelService = ModelService.getInstance();
    private Grid<Model> grid = new Grid<>(Model.class);
    private TextField filter = new TextField();
    private ModelForm form = new ModelForm(this);
    private Button addNewModel = new Button("Add new model");
    private Button rentalsButton = new Button("Rentals");
    private Button usersButton = new Button("Users");
    private Button carsButton = new Button("Cars");
    private Button manufacturersButton = new Button("Manufacturers");

    public ModelView() {
        filter.setPlaceholder("Filter by model");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> updateModel());

        rentalsButton.addClickListener(e ->
                rentalsButton.getUI().ifPresent(ui ->
                        ui.navigate("rentals"))
        );
        usersButton.addClickListener(e ->
                usersButton.getUI().ifPresent(ui ->
                        ui.navigate("users"))
        );
        carsButton.addClickListener(e ->
                carsButton.getUI().ifPresent(ui ->
                        ui.navigate("cars"))
        );
        manufacturersButton.addClickListener(e ->
                manufacturersButton.getUI().ifPresent(ui ->
                        ui.navigate("manufacturers"))
        );

        grid.setColumns("manufacturer", "name", "engineSize", "bodyType", "productionYear", "color", "seatsQuantity", "doorQuantity", "fuelType", "transmissionType");

        addNewModel.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setModel(new Model());
        });
        HorizontalLayout routes = new HorizontalLayout(rentalsButton, usersButton, carsButton, manufacturersButton);
        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewModel);

        HorizontalLayout userContent = new HorizontalLayout(grid, form);
        userContent.setSizeFull();
        grid.setSizeFull();

        add(routes, toolbar, userContent);
        form.setModel(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setModel(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(modelService.getModels());
    }

    private void updateModel() {
        grid.setItems(modelService.findByName(filter.getValue()));
    }

}
