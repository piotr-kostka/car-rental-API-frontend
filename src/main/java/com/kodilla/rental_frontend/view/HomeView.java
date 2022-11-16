package com.kodilla.rental_frontend.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "home", layout = MainLayout.class)
@PageTitle("Home | Rent APP")
public class HomeView extends VerticalLayout {

    public HomeView() {
        H1 title = new H1("Car Rental Application");
        H4 text = new H4("This is a simple car rental application - final project in the Kodilla Java Developer Course");
        H6 author = new H6("Author details:");
        H6 name = new H6("Piotr Kostka");
        H6 mail = new H6("piotrek.kostka@hotmail.com");
        Anchor github = new Anchor("https://github.com/piotr-kostka", "https://github.com/piotr-kostka");
        H6 phone = new H6("+48 691-267-695");

        VerticalLayout header = new VerticalLayout(title, text, author, name, mail, phone, github);
        header.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(header);
    }
}
