package com.masluck.application.views.components;

import com.masluck.application.entities.User;
import com.masluck.application.repositories.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;


@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout implements KeyNotifier {

    private final UserRepository repo;

    private User user;

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField email = new TextField("Email");
    TextField age = new TextField("Age");
    TextField password = new TextField("Password");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<User> binder = new Binder<>(User.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public UserEditor(UserRepository repo) {
        this.repo = repo;

        add(name, surname, email, age, password, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editUser(user));
        setVisible(false);
    }
    void delete() {
        repo.delete(user);
        changeHandler.onChange();
    }

    void save() {
        repo.save(user);
        changeHandler.onChange();
    }

    public final void editUser(User oldUser) {
        if (oldUser == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = oldUser.getId() != null;
        if (persisted) {

            user = repo.findById(oldUser.getId()).get();
        }
        else {
            user = oldUser;
        }
        cancel.setVisible(persisted);

        binder.setBean(user);

        setVisible(true);

        name.focus();
    }
}
