package com.kodilla.rental_frontend.form;

import com.kodilla.rental_frontend.domain.Manufacturer;
import com.kodilla.rental_frontend.service.ManufacturerService;
import com.kodilla.rental_frontend.view.ManufacturerView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.io.IOException;

public class ManufacturerForm extends FormLayout {
    private TextField manufacturerName = new TextField("Manufacturer name");

    private Button update = new Button("Save changes");

    private Binder<Manufacturer> binder = new Binder<>(Manufacturer.class);
    private ManufacturerView manufacturerView;
    private ManufacturerService manufacturerService = ManufacturerService.getInstance();

    public ManufacturerForm(ManufacturerView manufacturerView) {
        HorizontalLayout buttons = new HorizontalLayout(update);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(manufacturerName, buttons);
        binder.bind(manufacturerName, "name");
        this.manufacturerView = manufacturerView;

        update.addClickListener(event -> {
            try {
                saveChanges();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveChanges() throws IOException {
        Manufacturer manufacturer = binder.getBean();
        manufacturerService.createManufacturer(manufacturer);
        manufacturerView.refresh();
        setManufacturer(null);
    }

    public void setManufacturer(Manufacturer manufacturer) {
        binder.setBean(manufacturer);

        if (manufacturer == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
