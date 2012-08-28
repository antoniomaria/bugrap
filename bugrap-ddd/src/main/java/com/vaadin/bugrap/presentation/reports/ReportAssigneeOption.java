package com.vaadin.bugrap.presentation.reports;

public enum ReportAssigneeOption {
	ONLY_ME("Only me"), EVERYONE("Everyone");

	private String caption;

	private ReportAssigneeOption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
}
