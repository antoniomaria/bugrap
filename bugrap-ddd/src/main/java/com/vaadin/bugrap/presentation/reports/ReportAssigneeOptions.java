package com.vaadin.bugrap.presentation.reports;

public enum ReportAssigneeOptions {
	ONLY_ME("Only me"), EVERYONE("Everyone");

	private String caption;

	private ReportAssigneeOptions(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
}
