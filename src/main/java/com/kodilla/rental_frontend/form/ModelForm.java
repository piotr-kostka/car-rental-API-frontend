package com.kodilla.rental_frontend.form;

import com.kodilla.rental_frontend.domain.Manufacturer;
import com.kodilla.rental_frontend.domain.Model;
import com.kodilla.rental_frontend.domain.enums.BodyType;
import com.kodilla.rental_frontend.domain.enums.FuelType;
import com.kodilla.rental_frontend.domain.enums.TransmissionType;
import com.kodilla.rental_frontend.service.ManufacturerService;
import com.kodilla.rental_frontend.service.ModelService;
import com.kodilla.rental_frontend.view.ModelView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.io.IOException;

public class ModelForm extends FormLayout {
    private TextField modelId = new TextField();
    private ComboBox<Manufacturer> manufacturer = new ComboBox<>("Manufacturer");
    private TextField name = new TextField("Name");
    private TextField engineSize = new TextField("Engine size");
    private ComboBox<BodyType> bodyType = new ComboBox<>("Body type");
    private TextField productionYear = new TextField("Production year");
    private TextField color = new TextField("Color");
    private TextField seatsQuantity = new TextField("Seats quantity");
    private TextField doorQuantity = new TextField("Doors quantity");
    private ComboBox<FuelType> fuelType = new ComboBox<>("Fuel type");
    private ComboBox<TransmissionType> transmissionType = new ComboBox<>("Transmission type");

    private Button addModel = new Button("Add model");
    private Button saveChanges = new Button("Save changes");

    private final Binder<Model> binder = new Binder<>(Model.class);
    private final ModelView modelView;
    private final ModelService modelService = ModelService.getInstance();

    public ModelForm(ModelView modelView) {
        ComboBox.ItemFilter<Manufacturer> filter = (manufacturer, filterString) -> (manufacturer.getName()).contains(filterString.toLowerCase());
        manufacturer.setItems(filter, ManufacturerService.getInstance().getManufacturers());
        manufacturer.setItemLabelGenerator(Manufacturer::getName);

        bodyType.setItems(BodyType.values());
        fuelType.setItems(FuelType.values());
        transmissionType.setItems(TransmissionType.values());

        HorizontalLayout buttons = new HorizontalLayout(addModel, saveChanges);
        addModel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(manufacturer, name, engineSize, bodyType, productionYear, color, seatsQuantity, doorQuantity, fuelType, transmissionType, buttons);
        binder.bindInstanceFields(this);
        this.modelView = modelView;

        addModel.addClickListener(event -> {
            try {
                addModel();
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

    private void addModel() throws IOException {
        Model model = binder.getBean();
        modelService.createModel(model);
        modelView.refresh();
        setModel(null);
    }

    private void saveChanges() throws IOException {
        Model model = binder.getBean();
        modelService.updateModel(model);
        modelView.refresh();
        setModel(null);
    }

    public void setModel(Model model) {
        binder.setBean(model);
        setVisible(model != null);
    }
}
