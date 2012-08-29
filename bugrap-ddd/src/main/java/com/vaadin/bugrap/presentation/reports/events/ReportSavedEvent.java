package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.business.reports.entity.Report;

public class ReportSavedEvent {

    private final Report report;

    public ReportSavedEvent(Report report) {
        this.report = report;

    }

    public Report getReport() {
        return report;
    }
}
