package com.vaadin.bugrap.presentation.reports;

import java.util.List;

import com.vaadin.bugrap.business.reports.entity.Report;
import com.vaadin.cdi.VaadinContext.VaadinUIScoped;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

@VaadinUIScoped
public class ReportsTable extends CustomComponent {

    private final Table table;

    public ReportsTable() {
        setSizeFull();

        table = new Table();
        table.setSizeFull();

        setCompositionRoot(table);
    }

    public void populateReports(List<Report> reports) {

    }
}
