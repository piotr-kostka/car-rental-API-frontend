package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.User;
import com.kodilla.rental_frontend.form.UserForm;
import com.kodilla.rental_frontend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("users")
public class UserView extends VerticalLayout {

    private UserService userService = UserService.getInstance();
    private Grid<User> grid = new Grid<>(User.class);
    private TextField filterFirstName = new TextField();
    private TextField filterLastName = new TextField();
    private UserForm form = new UserForm(this);
    private Button addNewUser = new Button("Add new user");
    private Button rentalsButton = new Button("Rentals");
    private Button carsButton = new Button("Cars");
    private Button modelsButton = new Button("Models");
    private Button manufacturersButton = new Button("Manufacturers");

    public UserView() {
        filterFirstName.setPlaceholder("Filter by Firstname");
        filterFirstName.setClearButtonVisible(true);
        filterFirstName.setValueChangeMode(ValueChangeMode.EAGER);
        filterFirstName.addValueChangeListener(e -> updateFirstname());

        filterLastName.setPlaceholder("Filter by Lastname");
        filterLastName.setClearButtonVisible(true);
        filterLastName.setValueChangeMode(ValueChangeMode.EAGER);
        filterLastName.addValueChangeListener(e -> updateLastname());

        rentalsButton.addClickListener(e ->
                rentalsButton.getUI().ifPresent(ui ->
                        ui.navigate("rentals"))
        );
        carsButton.addClickListener(e ->
                carsButton.getUI().ifPresent(ui ->
                        ui.navigate("cars"))
        );
        modelsButton.addClickListener(e ->
                modelsButton.getUI().ifPresent(ui ->
                        ui.navigate("models"))
        );
        manufacturersButton.addClickListener(e ->
                manufacturersButton.getUI().ifPresent(ui ->
                        ui.navigate("manufacturers"))
        );


        grid.setColumns("firstName", "lastName", "pesel", "address", "mail", "password", "creditCardNo", "toPay", "signupDate");

        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setUser(new User());
        });
        HorizontalLayout routes = new HorizontalLayout(rentalsButton, carsButton, modelsButton,manufacturersButton);
        HorizontalLayout toolbar = new HorizontalLayout(filterFirstName, filterLastName, addNewUser);

        HorizontalLayout userContent = new HorizontalLayout(grid, form);
        userContent.setSizeFull();
        grid.setSizeFull();

        add(routes, toolbar, userContent);
        form.setUser(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setUser(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(userService.getUsers());
    }

    private void updateFirstname() {
        grid.setItems(userService.findByFirstName(filterFirstName.getValue()));
    }

    private void updateLastname() {
        grid.setItems(userService.findByLastName(filterLastName.getValue()));
    }

}
