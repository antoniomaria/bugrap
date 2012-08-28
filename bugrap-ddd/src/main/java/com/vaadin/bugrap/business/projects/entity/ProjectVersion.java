package com.vaadin.bugrap.business.projects.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vaadin.bugrap.business.AbstractEntity;

@Entity
@NamedQuery(name = ProjectVersion.projectVersion, query = "SELECT pv FROM ProjectVersion pv WHERE pv.project=:proj")
public class ProjectVersion extends AbstractEntity {

	private static final String PREFIX = "com.vaadin.bugrap.business.projects.entity.ProjectVersion.";
	public static final String projectVersion = PREFIX + "projectVersion";

	private String version;

	public static String PROJECT_VERSION_CAPTION_PROPERTY = "version";

	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	private boolean closed;

	@ManyToOne
	private Project project;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return version;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean equals(ProjectVersion projectVersion) {
		return getId() == projectVersion.getId();
	}
}
