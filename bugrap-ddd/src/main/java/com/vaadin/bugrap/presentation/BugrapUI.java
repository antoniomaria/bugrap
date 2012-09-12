package com.vaadin.bugrap.presentation;

import javax.inject.Inject;

import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.cdi.VaadinUIScoped;
import com.vaadin.cdi.component.JaasTools;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.SimpleViewDisplay;
import com.vaadin.server.WrappedRequest;
import com.vaadin.ui.UI;

@VaadinUI
@VaadinUIScoped
public class BugrapUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    @Inject
    private JaasTools jaasTools;

    private Navigator navigator;

    @Override
    protected void init(WrappedRequest request) {
        SimpleViewDisplay viewDisplay = new SimpleViewDisplay();
        setContent(viewDisplay);

        navigator = new Navigator(getPage(), viewDisplay);
        navigator.addProvider(viewProvider);

        if (jaasTools.isUserSignedIn()) {
            navigator.navigateTo("reports");
        } else {
            navigator.navigateTo("login");
        }
    }
}
