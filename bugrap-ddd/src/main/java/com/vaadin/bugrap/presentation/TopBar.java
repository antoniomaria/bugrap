package com.vaadin.bugrap.presentation;

import java.util.List;

import com.vaadin.bugrap.business.projects.entity.Project;
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

	private ComboBox projectSelector;

	private Button userButton;
	private Button logoutButton;

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
		bottomLayout = new HorizontalLayout();
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
}
