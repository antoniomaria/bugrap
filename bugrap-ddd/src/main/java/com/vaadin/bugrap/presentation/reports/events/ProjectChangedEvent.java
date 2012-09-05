package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.business.projects.entity.Project;

public class ProjectChangedEvent {

    private final Project project;

    public ProjectChangedEvent(Project selectedProject) {
        this.project = selectedProject;
    }

    public Project getSelectedProject() {
        return project;
    }

    public boolean isProjectSelected() {
        return project != null;
    }
}
