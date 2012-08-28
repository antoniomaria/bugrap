package com.vaadin.bugrap.business.projects.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.users.entity.Reporter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@NamedQuery(name = Project.activeProjects, query = "SELECT DISTINCT p FROM ProjectVersion as pv JOIN pv.project as p WHERE pv.closed=:closed")
public class Project extends AbstractEntity {
	private static final String PREFIX = "com.vaadin.bugrap.business.projects.entity.Project.";
	public final static String activeProjects = PREFIX + "activeProjects";
	private String name;

	public static String PROJECT_NAME_CAPTION_PROPERTY = "name";

	@ManyToOne(cascade = CascadeType.MERGE)
	private Reporter manager;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<Reporter> developers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Reporter getManager() {
		return manager;
	}

	public void setManager(Reporter manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return name;
	}

	public List<Reporter> getDevelopers() {
		return developers;
	}

	public void setDevelopers(List<Reporter> developers) {
		this.developers = developers;
	}
}
