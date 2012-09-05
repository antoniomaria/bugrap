package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.business.reports.entity.Report;

public class OpenReportWindowEvent {

    private final Report report;

    public OpenReportWindowEvent(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }
}
