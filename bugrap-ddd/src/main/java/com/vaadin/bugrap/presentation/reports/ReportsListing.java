package com.vaadin.bugrap.presentation.reports;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.bugrap.presentation.reports.events.ProjectVersionChangedEvent;
import com.vaadin.bugrap.presentation.reports.events.ReportAssigneeChangeEvent;
import com.vaadin.bugrap.presentation.reports.events.ReportStatusChangeEvent;
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

    private ReportsTable reportsTable;

    @Inject
    private javax.enterprise.event.Event<ProjectVersionChangedEvent> versionChangeEvent;

    @Inject
    private javax.enterprise.event.Event<ReportStatusChangeEvent> statusChangeEvent;

    @Inject
    private javax.enterprise.event.Event<ReportAssigneeChangeEvent> assigneeChangeEvent;

    private final ValueChangeListener versionChangeListener = new ValueChangeListener() {

        @Override
        public void valueChange(ValueChangeEvent event) {
            versionChangeEvent.fire(new ProjectVersionChangedEvent(
                    getSelectedVersion()));
        }
    };

    private final ValueChangeListener assigneeChangeListener = new ValueChangeListener() {

        @Override
        public void valueChange(ValueChangeEvent event) {
            assigneeChangeEvent.fire(new ReportAssigneeChangeEvent(
                    getSelectedAssignee()));
        }
    };

    private final ValueChangeListener statusChangeListener = new ValueChangeListener() {

        @Override
        public void valueChange(ValueChangeEvent event) {
            statusChangeEvent.fire(new ReportStatusChangeEvent(
                    getSelectedStatus()));

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

        versionSelector = new ComboBox("Reports for");
        versionSelector.setImmediate(true);

        versionLayout.addComponent(versionSelector);

        assignees = generateAssigneeOptionGroup();
        assignees.addListener(assigneeChangeListener);

        status = generateStatusOptionGroup();
        status.addListener(statusChangeListener);

        filterLayout.addComponent(assignees);
        filterLayout.addComponent(status);

        layout.addComponent(versionLayout);
        layout.addComponent(filterLayout);

        reportsTable = new ReportsTable();

        layout.addComponent(reportsTable);
        layout.setExpandRatio(reportsTable, 1);

        setCompositionRoot(layout);
    }

    protected ReportStatusOption getSelectedStatus() {
        return (ReportStatusOption) status.getValue();
    }

    protected ReportAssigneeOption getSelectedAssignee() {
        return (ReportAssigneeOption) assignees.getValue();
    }

    protected ProjectVersion getSelectedVersion() {
        return (ProjectVersion) versionSelector.getValue();
    }

    private OptionGroup generateAssigneeOptionGroup() {
        OptionGroup assignees = new OptionGroup("Assignee");
        assignees.setNullSelectionAllowed(false);

        for (ReportAssigneeOption option : ReportAssigneeOption.values()) {
            assignees.addItem(option);
            assignees.setItemCaption(option, option.getCaption());
        }

        assignees.select(ReportAssigneeOption.ONLY_ME);
        assignees.setImmediate(true);

        return assignees;
    }

    private OptionGroup generateStatusOptionGroup() {
        OptionGroup status = new OptionGroup("Status");
        status.setNullSelectionAllowed(false);

        for (ReportStatusOption option : ReportStatusOption.values()) {
            status.addItem(option);
            status.setItemCaption(option, option.getCaption());
        }

        status.select(ReportStatusOption.OPEN);
        status.setImmediate(true);

        return status;
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
