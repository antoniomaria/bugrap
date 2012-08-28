package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.business.projects.entity.ProjectVersion;

public class ProjectVersionChangedEvent {

	private final ProjectVersion selectedVersion;

	public ProjectVersionChangedEvent(ProjectVersion selectedVersion) {
		this.selectedVersion = selectedVersion;
	}

	public ProjectVersion getSelectedVersion() {
		return selectedVersion;
	}
}
