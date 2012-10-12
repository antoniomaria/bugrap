package com.vaadin.bugrap.presentation.reports;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.bugrap.business.projects.ProjectBoundary;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.presentation.reports.events.ProjectChangedEvent;
import com.vaadin.bugrap.presentation.reports.events.ReportBugEvent;
import com.vaadin.cdi.VaadinView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@VaadinView(value = "reports")
public class ReportsView extends CustomComponent implements View {

    private VerticalLayout layout;

    @Inject
    ProjectBoundary projectRepository;

    @Inject
    TopBar topBar;

    @Inject
    ReportsListing reportListing;

    @Inject
    ReportEditor reportEditor;

    VerticalSplitPanel splitPanel;

    @PostConstruct
    public void init() {
        setSizeFull();

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        splitPanel = new VerticalSplitPanel();
        splitPanel.setSizeFull();

        layout.addComponent(topBar);
        layout.addComponent(splitPanel);

        layout.setExpandRatio(splitPanel, 1);

        splitPanel.setFirstComponent(reportListing);
        splitPanel.setSecondComponent(reportEditor);

        // Show only the reports table
        splitPanel.setSplitPosition(100);

        topBar.populateProjects(projectRepository.getProjects());

        setCompositionRoot(layout);
    }

    protected void onReportBug(@Observes ReportBugEvent reportBugEvent) {
        // Show only the editor component
        splitPanel.setSplitPosition(50);
        reportEditor.initializeForNewReport();
    }

    protected void onProjectChanged(
            @Observes ProjectChangedEvent projectChangedEvent) {
        if (projectChangedEvent.isProjectSelected()) {

            Project project = projectChangedEvent.getSelectedProject();
            reportListing.populateProjectVersions(project.getProjectVersions());
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
