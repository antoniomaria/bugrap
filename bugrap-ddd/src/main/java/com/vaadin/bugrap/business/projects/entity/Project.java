package com.vaadin.bugrap.business.projects.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.reports.entity.Report;
import com.vaadin.bugrap.business.users.entity.Reporter;

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

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "project")
    private final Set<Report> reports;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "project")
    private final List<ProjectVersion> projectVersions;

    public Project() {
        this.reports = new HashSet<Report>();
        this.projectVersions = new ArrayList<ProjectVersion>();
    }

    public void addReport(Report report) {
        if (!reports.contains(report)) {
            reports.add(report);
            report.setProject(this);
        }
    }

    public void addProjectVersion(ProjectVersion projectVersion) {
        if (!projectVersions.contains(projectVersion)) {
            projectVersions.add(projectVersion);
            projectVersion.setProject(this);
        }
    }

    public List<ProjectVersion> getProjectVersions() {
        return Collections.unmodifiableList(projectVersions);
    }

    public Set<Report> getReportsForVersion(ProjectVersion version) {
        Set<Report> matchingReports = new HashSet<Report>();
        for (Report report : this.reports) {
            if (report.knowsProjectVersion(version)) {
                matchingReports.add(report);
            }
        }
        return matchingReports;
    }

    public Set<Report> getAllReports() {
        return Collections.unmodifiableSet(reports);
    }

    public List<Report> getReportsWithSummaryOrDescription(String searchEntry) {
        List<Report> matchingReports = new ArrayList<Report>();
        for (Report report : this.reports) {
            if (report.summaryOrDescriptionLike(searchEntry)) {
                matchingReports.add(report);
            }
        }
        return matchingReports;
    }

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
