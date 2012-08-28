package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.presentation.reports.ReportStatusOptions;

public class ReportStatusChangeEvent {

	private final ReportStatusOptions selectedStatus;

	public ReportStatusChangeEvent(ReportStatusOptions selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public ReportStatusOptions getSelectedStatus() {
		return selectedStatus;
	}

}
