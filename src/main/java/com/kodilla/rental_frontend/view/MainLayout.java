package com.kodilla.rental_frontend.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        RouterLink homeLink = new RouterLink("Home", HomeView.class);
        RouterLink userLink = new RouterLink("Users", UserView.class);
        RouterLink rentalLink = new RouterLink("Rentals", RentalView.class);
        RouterLink carLink = new RouterLink("Cars", CarsView.class);
        RouterLink modelLink = new RouterLink("Models", ModelView.class);
        RouterLink manufacturerLink = new RouterLink("Manufacturers", ManufacturerView.class);

        addToDrawer(new VerticalLayout(homeLink, rentalLink, userLink, carLink, modelLink, manufacturerLink));
    }
}
