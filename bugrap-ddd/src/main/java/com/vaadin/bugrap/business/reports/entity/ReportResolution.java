package com.vaadin.bugrap.business.reports.entity;

public enum ReportResolution {
	FIXED("Fixed"), INVALID("Invalid"), WONTFIX("Won't fix"), CANTFIX(
			"Can't fix"), DUPLICATE("Duplicate"), WORKSFORME("Works for me"), NEEDMOREINFO(
			"Need more Info");

	private String name;

	private ReportResolution(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
