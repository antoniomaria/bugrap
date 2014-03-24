package com.vaadin.bugrap.presentation.login;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@CDIView("login")
public class LoginView extends CustomComponent implements View {

    private TextField username;
    private PasswordField password;

    @Inject
    private javax.enterprise.event.Event<LoginEvent> loginEvent;

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
        layout.setMargin(true);
        layout.setSpacing(true);

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
        loginEvent.fire(new LoginEvent(username.getValue(), password.getValue()));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
    }
}
