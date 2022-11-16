package com.kodilla.rental_frontend.form;

import com.kodilla.rental_frontend.domain.Car;
import com.kodilla.rental_frontend.domain.Rental;
import com.kodilla.rental_frontend.domain.User;
import com.kodilla.rental_frontend.domain.enums.Currency;
import com.kodilla.rental_frontend.service.CarService;
import com.kodilla.rental_frontend.service.RentalService;
import com.kodilla.rental_frontend.service.UserService;
import com.kodilla.rental_frontend.view.RentalView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.io.IOException;

public class RentalForm extends FormLayout {
    private TextField rentalId = new TextField();
    private ComboBox<User> user = new ComboBox<>("User");
    private ComboBox<Car> car = new ComboBox<>("Car");
    private ComboBox<Currency> currency = new ComboBox<>("Currency");

    private Button save = new Button("Rent a car");
    private Button returnCar = new Button("Return Car");
    private Button makePayment = new Button("Pay");

    private final Binder<Rental> binder = new Binder<>(Rental.class);
    private final RentalView rentalView;
    private final RentalService rentalService = RentalService.getInstance();

    public RentalForm(RentalView rentalView) {
        ComboBox.ItemFilter<Car> filterCar = (car, filterString) -> (car.toString()).contains(filterString.toLowerCase());
        car.setItems(filterCar, CarService.getInstance().getAvailableCars());
        car.setItemLabelGenerator(Car::toString);

        ComboBox.ItemFilter<User> filterUser = (user, filterString) -> (user.toString()).contains(filterString.toLowerCase());
        user.setItems(filterUser, UserService.getInstance().getUsers());
        user.setItemLabelGenerator(User::toString);

        currency.setItems(Currency.values());

        HorizontalLayout buttons = new HorizontalLayout(save, returnCar, makePayment);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(user, car, currency, buttons);
        binder.bindInstanceFields(this);
        this.rentalView = rentalView;

        save.addClickListener(event -> {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        returnCar.addClickListener(event -> {
            try {
                returnCar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        makePayment.addClickListener(event -> {
            try {
                makePayment();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void save() throws IOException {
        Rental rental = binder.getBean();
        rentalService.createRental(rental);
        rentalView.refresh();
        setRental(null);
    }

    private void returnCar() throws IOException {
        Rental rental = binder.getBean();
        if(rental.getReturnDate() == null && rental.getPaymentDate() == null ) {
            rentalService.returnCar(rental.getRentalId());
        }
        rentalView.refresh();
        setRental(null);
    }

    private void makePayment() throws IOException {
        Rental rental = binder.getBean();
        if(rental.getRentDate() != null && rental.getPaymentDate() == null) {
            rentalService.makePayment(rental.getRentalId());
        }
        rentalView.refresh();
        setRental(null);
    }

    public void setRental(Rental rental) {
        binder.setBean(rental);
        setVisible(rental != null);
    }
}
