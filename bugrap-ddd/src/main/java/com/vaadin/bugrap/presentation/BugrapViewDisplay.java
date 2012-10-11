package com.vaadin.bugrap.presentation;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Panel;

public class BugrapViewDisplay extends Panel implements ViewDisplay {

    public BugrapViewDisplay() {
        setSizeFull();
    }

    @Override
    public void showView(View view) {
        setContent((ComponentContainer) view);
    }
}
