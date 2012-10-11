package com.vaadin.bugrap.presentation;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.bugrap.presentation.login.LoginEvent;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.cdi.component.JaasTools;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@VaadinUI
public class BugrapUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        BugrapViewDisplay viewDisplay = new BugrapViewDisplay();
        setContent(viewDisplay);

        navigator = new Navigator(this, (ViewDisplay) viewDisplay);
        navigator.addProvider(viewProvider);

        if (JaasTools.isUserSignedIn()) {
            navigator.navigateTo("reports");
        } else {
            navigator.navigateTo("login");
        }
    }

    protected void onLogin(@Observes LoginEvent loginEvent) {
        if (JaasTools.isUserSignedIn()) {
            navigator.navigateTo("reports");
        }
    }
}
