package com.vaadin.bugrap.presentation.projects;

import javax.inject.Inject;

import com.vaadin.bugrap.business.projects.boundary.ProjectRepository;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.cdi.VaadinUIScoped;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.WrappedRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@VaadinUI(mapping = "projects")
@VaadinUIScoped
public class ProjectsUI extends UI {

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectTable projects;

    private TextField projectName;
    private TextField versionName;

    private VerticalSplitPanel layout;

    @Inject
    private VersionTable versions;

    private final Button.ClickListener createProjectListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            createNewProject();
        }
    };

    private final Button.ClickListener createVersionListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            if (projects.isProjectSelected()) {
                createNewVersionForProject(projects.getSelectedProject());
            }
        }
    };

    private final ValueChangeListener projectChangeListener = new ValueChangeListener() {

        @Override
        public void valueChange(ValueChangeEvent event) {
            if (projects.isProjectSelected()) {
                refreshVersionsForSelectedProject();
                layout.setSplitPosition(50);
            } else {
                layout.setSplitPosition(100);
            }
        }
    };

    @Override
    protected void init(WrappedRequest request) {
        setSizeFull();

        layout = new VerticalSplitPanel();
        layout.setSizeFull();
        layout.setSplitPosition(100);

        setContent(layout);

        VerticalLayout topContent = new VerticalLayout();
        topContent.setSizeFull();

        projectName = new TextField();
        projectName.setInputPrompt("Name for new project...");

        Button createProject = new Button("Create project",
                createProjectListener);

        projects.addValueChangeListener(projectChangeListener);

        topContent.addComponent(projectName);
        topContent.addComponent(createProject);
        topContent.addComponent(projects);

        topContent.setExpandRatio(projects, 1);

        VerticalLayout bottomContent = new VerticalLayout();
        bottomContent.setSizeFull();

        Button createVersion = new Button("Create version",
                createVersionListener);

        versionName = new TextField();
        versionName.setInputPrompt("Name for version...");

        bottomContent.addComponent(versionName);
        bottomContent.addComponent(createVersion);
        bottomContent.addComponent(versions);
        bottomContent.setExpandRatio(versions, 1);

        layout.setFirstComponent(topContent);
        layout.setSecondComponent(bottomContent);

        refreshProjectTable();
    }

    protected void refreshVersionsForSelectedProject() {
        Project project = projects.getSelectedProject();

        if (project != null) {
            versions.populateVersions(projectRepository.getVersions(project));
        }
    }

    protected void createNewVersionForProject(Project project) {
        ProjectVersion version = new ProjectVersion();
        version.setProject(project);
        version.setVersion(versionName.getValue().toString());

        version = projectRepository.store(version);

        if (version.getId() > 0) {
            Notification.show("Version saved");
        } else {
            Notification.show("Version creation failed", Type.ERROR_MESSAGE);
        }

        refreshVersionTable(project);
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

    private void refreshVersionTable(Project project) {
        versions.populateVersions(projectRepository.getVersions(project));
    }
}
