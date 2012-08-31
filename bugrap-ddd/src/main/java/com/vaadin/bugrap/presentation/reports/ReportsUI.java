package com.vaadin.bugrap.presentation.reports;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.bugrap.business.projects.boundary.ProjectRepository;
import com.vaadin.bugrap.presentation.reports.events.ProjectChangedEvent;
import com.vaadin.bugrap.presentation.reports.events.ReportBugEvent;
import com.vaadin.cdi.VaadinContext.VaadinUIScoped;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.server.WrappedRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@VaadinUI(mapping = "reports")
@VaadinUIScoped
public class ReportsUI extends UI {

    private VerticalLayout layout;

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private TopBar topBar;

    @Inject
    private ReportsListing reportListing;

    @Inject
    private ReportEditor reportEditor;

    private VerticalSplitPanel splitPanel;

    @Override
    protected void init(WrappedRequest request) {
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

        setContent(layout);

        topBar.populateProjects(projectRepository.getProjects());
    }

    protected void onReportBug(@Observes ReportBugEvent reportBugEvent) {
        // Show only the editor component
        splitPanel.setSplitPosition(100, true);
    }

    protected void onProjectChanged(
            @Observes ProjectChangedEvent projectChangedEvent) {
    }
}
