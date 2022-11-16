package com.kodilla.rental_frontend.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H3 logo = new H3("Rent APP");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink userLink = new RouterLink("Users", UserView.class);
        userLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink rentalLink = new RouterLink("Rentals", RentalView.class);
        rentalLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink carLink = new RouterLink("Cars", CarsView.class);
        carLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink modelLink = new RouterLink("Models", ModelView.class);
        modelLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink manufacturerLink = new RouterLink("Manufacturers", ManufacturerView.class);
        manufacturerLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(rentalLink, userLink, carLink, modelLink, manufacturerLink));
    }
}
