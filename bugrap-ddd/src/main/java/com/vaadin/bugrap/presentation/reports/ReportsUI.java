package com.vaadin.bugrap.presentation.reports;

import javax.inject.Inject;

import com.vaadin.bugrap.presentation.TopBar;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@VaadinUI(mapping = "reports")
public class ReportsUI extends UI {

	private VerticalLayout layout;

	@Inject
	private TopBar topBar;

	@Override
	protected void init(WrappedRequest request) {
		setSizeFull();

		layout = new VerticalLayout();
		layout.setSizeFull();

		layout.addComponent(topBar);

		setContent(layout);
	}

}
