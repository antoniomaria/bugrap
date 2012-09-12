package com.vaadin.bugrap.presentation.login;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletException;

import com.vaadin.cdi.VaadinUIScoped;
import com.vaadin.cdi.VaadinView;
import com.vaadin.cdi.component.JaasTools;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@VaadinView("login")
@VaadinUIScoped
public class LoginView extends CustomComponent implements View {

    @Inject
    private JaasTools jaasTools;

    private TextField username;
    private PasswordField password;

    private final Button.ClickListener loginButtonListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            performLogin();
        }
    };

    @PostConstruct
    public void init() {
        setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        username = new TextField();
        username.setInputPrompt("User name");

        password = new PasswordField();
        password.setInputPrompt("Password");

        layout.addComponent(username);
        layout.addComponent(password);

        Button login = new Button("Login", loginButtonListener);

        layout.addComponent(login);

        setCompositionRoot(layout);
    }

    protected void performLogin() {
        try {
            jaasTools.login(username.getValue(), password.getValue());
            Notification.show("Logged in!");
        } catch (ServletException e) {
            e.printStackTrace();
            Notification.show("Failed to login");
        }

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
    }
}
