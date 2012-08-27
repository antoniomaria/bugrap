package com.vaadin.bugrap.business.reports.entity;

public enum ReportStatus {
	OPEN("Open"), CLOSED("Closed");

	private String name;

	ReportStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
