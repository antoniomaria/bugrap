package com.vaadin.bugrap.presentation.reports.events;

import com.vaadin.bugrap.presentation.reports.ReportAssigneeOptions;

public class ReportAssigneeChangeEvent {

	private final ReportAssigneeOptions assigneeOption;

	public ReportAssigneeChangeEvent(ReportAssigneeOptions assigneeOption) {
		this.assigneeOption = assigneeOption;
	}

	public ReportAssigneeOptions getAssigneeOption() {
		return assigneeOption;
	}

}
