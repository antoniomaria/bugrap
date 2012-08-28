package com.vaadin.bugrap.presentation.reports;

public enum ReportStatusOption {
	OPEN("Open"), ALL_KINDS("All kinds"), CUSTOM("Custom");

	private String caption;

	private ReportStatusOption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
}
