package com.vaadin.bugrap.presentation.reports;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.bugrap.business.reports.entity.Report;
import com.vaadin.bugrap.business.reports.entity.ReportPriority;
import com.vaadin.bugrap.business.reports.entity.ReportStatus;
import com.vaadin.bugrap.business.reports.entity.ReportType;
import com.vaadin.bugrap.presentation.reports.events.ReportCreationCancelledEvent;
import com.vaadin.bugrap.presentation.reports.events.ReportSavedEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class ReportEditor extends CustomComponent {

    private VerticalLayout layout;

    private HorizontalLayout topArea;
    private HorizontalLayout selectors;

    private FieldGroup editorFields;

    private Panel content;

    @Inject
    private javax.enterprise.event.Event<ReportSavedEvent> reportSavedEvent;

    @Inject
    private javax.enterprise.event.Event<ReportCreationCancelledEvent> reportCancelled;

    private Report report;

    private final Button.ClickListener saveListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            try {
                editorFields.commit();
                reportSavedEvent.fire(new ReportSavedEvent(report));
            } catch (CommitException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    private final Button.ClickListener cancelListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            reportCancelled.fire(new ReportCreationCancelledEvent());
        }
    };

    private final Button.ClickListener revertListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            editorFields.discard();
        }
    };

    @PostConstruct
    public void init() {
        setSizeFull();

        editorFields = new FieldGroup();

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);

        topArea = new HorizontalLayout();
        topArea.setSpacing(true);

        selectors = new HorizontalLayout();
        selectors.setSpacing(true);

        content = new Panel();
        content.setSizeFull();

        layout.addComponent(topArea);
        layout.addComponent(selectors);
        layout.addComponent(content);

        layout.setExpandRatio(content, 1);

        setCompositionRoot(layout);
    }

    public void initializeForNewReport() {
        initializeForReport(new Report());

        Button create = new Button("Create", saveListener);
        Button cancel = new Button("Cancel", cancelListener);

        selectors.addComponent(create);
        selectors.addComponent(cancel);
    }

    public void initializeForExistingReport(Report report) {
        initializeForReport(report);

        Button update = new Button("Update", saveListener);
        Button revert = new Button("Rever", revertListener);

        selectors.addComponent(update);
        selectors.addComponent(revert);
    }

    private void initializeForReport(Report report) {
        this.report = report;

        selectors.removeAllComponents();

        editorFields.setItemDataSource(new BeanItem<Report>(report));

        ComboBox priority = editorFields.buildAndBind("Priority", "priority",
                ComboBox.class);
        ComboBox type = editorFields.buildAndBind("Type", "type",
                ComboBox.class);
        ComboBox status = editorFields.buildAndBind("Status", "status",
                ComboBox.class);
        ComboBox assignedTo = editorFields.buildAndBind("Assigned to",
                "assigned", ComboBox.class);
        ComboBox version = editorFields.buildAndBind("Version", "version",
                ComboBox.class);

        configurePrioritySelector(priority);
        configureTypeSelector(type);
        configureStatusSelector(type);
        configureAssignedToSelector(assignedTo);
        configureVersionSelector(version);

        selectors.addComponent(priority);
        selectors.addComponent(type);
        selectors.addComponent(status);
        selectors.addComponent(assignedTo);
        selectors.addComponent(version);
    }

    private void configurePrioritySelector(ComboBox priority) {
        priority.setNullSelectionAllowed(false);

        for (ReportPriority value : ReportPriority.values()) {
            priority.addItem(value);
            priority.setItemCaption(value, value.name());
        }

        priority.select(ReportPriority.NORMAL);
    }

    private void configureTypeSelector(ComboBox type) {
        type.setNullSelectionAllowed(false);

        for (ReportType value : ReportType.values()) {
            type.addItem(value);
            type.setItemCaption(value, value.toString());
        }

        type.select(ReportType.BUG);
    }

    private void configureStatusSelector(ComboBox status) {
        status.setNullSelectionAllowed(false);

        for (ReportStatus value : ReportStatus.values()) {
            status.addItem(value);
            status.setItemCaption(value, value.toString());
        }

        status.select(ReportStatus.OPEN);
    }

    private void configureAssignedToSelector(ComboBox assignedTo) {

    }

    private void configureVersionSelector(ComboBox version) {

    }
}
