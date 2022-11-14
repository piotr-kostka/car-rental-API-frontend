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

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<User> binder = new Binder<User>(User.class);
    private UserView userView;
    private UserService userService = UserService.getInstance();

    public UserForm(UserView userView) {
        userId.setEnabled(false);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, pesel, address, mail, password, creditCardNo, buttons);
        binder.bindInstanceFields(this);
        this.userView = userView;
        save.addClickListener(event -> {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delete.addClickListener(event -> delete());
    }

    private void save() throws IOException {
        User user = binder.getBean();
        userService.createUser(user);
        userView.refresh();
        setUser(null);
    }

    private void delete() {
        User user = binder.getBean();
        userService.deleteUser(user.getUserId());
        userView.refresh();
        setUser(null);
    }

    public void setUser(User user) {
        binder.setBean(user);

        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
