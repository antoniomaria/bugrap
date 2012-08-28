package com.vaadin.bugrap.presentation;

import java.util.List;

import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.users.entity.Reporter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TopBar extends CustomComponent {

	private final VerticalLayout layout;

	private final HorizontalLayout topLayout;
	private final HorizontalLayout bottomLayout;

	private final ComboBox projectSelector;

	private final Button userButton;
	private final Button logoutButton;

	private Button reportBug;
	private Button requestFeature;
	private Button manageProject;

	private TextField searchArea;

	private final ValueChangeListener projectChangeListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {

		}
	};

	public TopBar() {
		layout = new VerticalLayout();

		topLayout = new HorizontalLayout();
		topLayout.setWidth(100, Unit.PERCENTAGE);

		bottomLayout = new HorizontalLayout();
		bottomLayout.setWidth(100, Unit.PERCENTAGE);

		projectSelector = new ComboBox();
		projectSelector.setWidth(100, Unit.PIXELS);

		userButton = new Button();
		logoutButton = new Button("Logout");

		topLayout.addComponent(projectSelector);

		topLayout.addComponent(userButton);
		topLayout.addComponent(logoutButton);
		topLayout.setWidth(100, Unit.PIXELS);
		topLayout.setExpandRatio(projectSelector, 1);

		layout.addComponent(topLayout);
		layout.addComponent(bottomLayout);

		setCompositionRoot(layout);
	}

	public void populateProjects(List<Project> projects) {
		projectSelector.removeListener(projectChangeListener);

		BeanItemContainer<Project> projectsContainer = new BeanItemContainer<Project>(
				Project.class);
		projectsContainer.addAll(projects);

		projectSelector.setContainerDataSource(projectsContainer);
		projectSelector
				.setItemCaptionPropertyId(Project.PROJECT_NAME_CAPTION_PROPERTY);

		projectSelector.addListener(projectChangeListener);
	}

	public void populateCurrentUser(Reporter user) {
		userButton.setCaption(user.getName());
	}
}
