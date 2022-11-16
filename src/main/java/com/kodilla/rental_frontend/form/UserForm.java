package com.kodilla.rental_frontend.form;

import com.kodilla.rental_frontend.domain.User;
import com.kodilla.rental_frontend.service.UserService;
import com.kodilla.rental_frontend.view.UserView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.io.IOException;

public class UserForm extends FormLayout {
    private TextField userId = new TextField();
    private TextField firstName = new TextField("Firstname");
    private TextField lastName = new TextField("Lastname");
    private TextField pesel = new TextField("Pesel");
    private TextField address = new TextField("Address");
    private EmailField mail = new EmailField("Mail");
    private TextField password = new TextField("Password");
    private TextField creditCardNo = new TextField("Credit card number");

    private Button addUser = new Button("Add user");
    private Button saveChanges = new Button("Save changes");

    private final Binder<User> binder = new Binder<User>(User.class);
    private final UserView userView;
    private final UserService userService = UserService.getInstance();

    public UserForm(UserView userView) {
        userId.setEnabled(false);
        HorizontalLayout buttons = new HorizontalLayout(addUser, saveChanges);
        addUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(firstName, lastName, pesel, address, mail, password, creditCardNo, buttons);
        binder.bindInstanceFields(this);
        this.userView = userView;

        addUser.addClickListener(event -> {
            try {
                addUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        saveChanges.addClickListener(event -> {
            try {
                saveChanges();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addUser() throws IOException {
        User user = binder.getBean();
        userService.createUser(user);
        userView.refresh();
        setUser(null);
    }

    private void saveChanges() throws IOException {
        User user = binder.getBean();
        userService.updateUser(user);
        userView.refresh();
        setUser(null);
    }

    public void setUser(User user) {
        binder.setBean(user);

        setVisible(user != null);
    }
}
