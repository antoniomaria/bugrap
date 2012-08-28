package com.vaadin.bugrap.presentation.reports;

public enum ReportStatusOptions {
	OPEN("Open"), ALL_KINDS("All kinds"), CUSTOM("Custom");

	private String caption;

	private ReportStatusOptions(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
}
