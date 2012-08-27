package com.vaadin.bugrap.business.reports.entity;

public enum ReportType {
	BUG, FEATURE;

	@Override
	public String toString() {
		String x = this.name();
		return x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase();
	}
}
