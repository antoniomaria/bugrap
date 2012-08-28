package com.vaadin.bugrap.presentation.reports;

import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

public class ReportsListing extends CustomComponent {

	private ComboBox versionSelector;

	private OptionGroup assignees;
	private OptionGroup status;

	private VerticalLayout layout;

	private HorizontalLayout versionLayout;
	private HorizontalLayout filterLayout;

	private final ValueChangeListener versionChangeListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
		}
	};

	@PostConstruct
	public void init() {
		setSizeFull();

		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);

		versionLayout = new HorizontalLayout();
		versionLayout.setWidth(100, Unit.PERCENTAGE);

		filterLayout = new HorizontalLayout();
		filterLayout.setWidth(100, Unit.PERCENTAGE);

		versionSelector = new ComboBox("Reports for");

		versionLayout.addComponent(versionSelector);

		layout.addComponent(versionLayout);
		layout.addComponent(filterLayout);

		setCompositionRoot(layout);
	}

	/**
	 * Populates available versions for currently selected project
	 * 
	 * @param projectVersions
	 */
	public void populateProjectVersions(List<ProjectVersion> projectVersions) {
		versionSelector.removeListener(versionChangeListener);

		BeanItemContainer<ProjectVersion> versionContainer = new BeanItemContainer<ProjectVersion>(
				ProjectVersion.class);
		versionContainer.addAll(projectVersions);

		versionSelector.setContainerDataSource(versionContainer);
		versionSelector
				.setItemCaptionPropertyId(ProjectVersion.PROJECT_VERSION_CAPTION_PROPERTY);

		versionSelector.addListener(versionChangeListener);
	}

}