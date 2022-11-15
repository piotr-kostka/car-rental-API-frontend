package com.kodilla.rental_frontend.form;

import com.kodilla.rental_frontend.domain.Car;
import com.kodilla.rental_frontend.domain.Model;
import com.kodilla.rental_frontend.domain.enums.CarStatus;
import com.kodilla.rental_frontend.service.CarService;
import com.kodilla.rental_frontend.service.ModelService;
import com.kodilla.rental_frontend.view.CarsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.io.IOException;

public class CarsForm extends FormLayout {
    private TextField carId = new TextField();
    private ComboBox<Model> model = new ComboBox<>("Model");
    private TextField licenseNumber = new TextField("License number");
    private BigDecimalField price = new BigDecimalField("Price");
    private ComboBox<CarStatus> carStatus = new ComboBox<>("Car status");

    private Button addCar = new Button("Add car");
    private Button saveChanges = new Button("Save changes");

    private Binder<Car> binder = new Binder<>(Car.class);
    private CarsView carView;
    private CarService carService = CarService.getInstance();

    public CarsForm(CarsView carView) {
        ComboBox.ItemFilter<Model> filter = (model, filterString) -> (model.getName()).contains(filterString.toLowerCase());
        model.setItems(filter, ModelService.getInstance().getModels());
        model.setItemLabelGenerator(Model::toString);

        carStatus.setItems(CarStatus.values());

        HorizontalLayout buttons = new HorizontalLayout(addCar, saveChanges);
        addCar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(model, licenseNumber, price, carStatus, buttons);
        binder.bindInstanceFields(this);
        this.carView = carView;
        addCar.addClickListener(event -> {
            try {
                addCar();
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

    private void addCar() throws IOException {
        Car car = binder.getBean();
        carService.createCar(car);
        carView.refresh();
        setCar(null);
    }

    private void saveChanges() throws IOException {
        Car car = binder.getBean();
        carService.updateCar(car);
        carView.refresh();
        setCar(null);
    }

    public void setCar(Car car) {
        binder.setBean(car);

        if (car == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
