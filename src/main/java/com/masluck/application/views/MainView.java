package com.masluck.application.views;

import com.masluck.application.entities.User;
import com.masluck.application.repositories.UserRepository;
import com.masluck.application.views.components.UserEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final UserRepository repo;
    final Grid<User> grid;

    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewBtn = new Button("Add new");
    private final HorizontalLayout layout = new HorizontalLayout(filter, addNewBtn);
    private final UserEditor userEditor;

    public MainView(UserRepository repo, UserEditor userEditor) {
        this.repo = repo;
        this.grid = new Grid<>(User.class);
        this.userEditor = userEditor;
        add(layout, grid, userEditor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listUser(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            userEditor.editUser(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> userEditor.editUser(new User()));

        // Listen changes made by the editor, refresh data from backend
        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            listUser(filter.getValue());
        });

        listUser("");
    }

    private void listUser(String name) {
        if (name.isEmpty()) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByName(name));
        }
    }

}