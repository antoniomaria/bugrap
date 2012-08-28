package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.presentation.reports.ReportStatusOption;

public class ReportStatusChangeEvent {

	private final ReportStatusOption selectedStatus;

	public ReportStatusChangeEvent(ReportStatusOption selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public ReportStatusOption getSelectedStatus() {
		return selectedStatus;
	}

}
