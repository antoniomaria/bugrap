package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.presentation.reports.ReportAssigneeOption;

public class ReportAssigneeChangeEvent {

    private final ReportAssigneeOption assigneeOption;

    public ReportAssigneeChangeEvent(ReportAssigneeOption assigneeOption) {
        this.assigneeOption = assigneeOption;
    }

    public ReportAssigneeOption getAssigneeOption() {
        return assigneeOption;
    }

}
