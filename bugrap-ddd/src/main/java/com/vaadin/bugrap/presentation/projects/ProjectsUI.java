package com.vaadin.bugrap.presentation.projects;

import javax.inject.Inject;

import com.vaadin.bugrap.business.projects.boundary.ProjectRepository;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.cdi.VaadinContext.VaadinUIScoped;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.server.WrappedRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@VaadinUI(mapping = "projects")
@VaadinUIScoped
public class ProjectsUI extends UI {

    @Inject
    private ProjectRepository projectRepository;

    private Button createProject;

    @Inject
    private ProjectTable projects;

    private TextField projectName;

    private final Button.ClickListener createProjectListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            createNewProject();
        }
    };

    @Override
    protected void init(WrappedRequest request) {
        setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        setContent(layout);

        projectName = new TextField();
        projectName.setInputPrompt("Name for new project...");

        createProject = new Button("Create project", createProjectListener);

        layout.addComponent(projectName);
        layout.addComponent(createProject);
        layout.addComponent(projects);

        layout.setExpandRatio(projects, 1);

        refreshProjectTable();
    }

    protected void createNewProject() {
        Project project = new Project();
        project.setName(projectName.getValue().toString());

        project = projectRepository.store(project);

        if (project.getId() > 0) {
            Notification.show("Project saved");
        } else {
            Notification.show("Project creation failed", Type.ERROR_MESSAGE);
        }

        refreshProjectTable();
    }

    private void refreshProjectTable() {
        projects.populateProjects(projectRepository.getProjects());
    }
}
