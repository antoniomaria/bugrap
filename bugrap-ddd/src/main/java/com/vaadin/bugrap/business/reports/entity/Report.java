package com.vaadin.bugrap.business.reports.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.bugrap.business.users.entity.Reporter;

@Entity
public class Report extends AbstractEntity {

    @Enumerated
    private ReportType type;

    @Enumerated
    private ReportStatus status;

    @Enumerated
    private ReportResolution resolution;

    @Column(columnDefinition = "VARCHAR(5000)")
    private String summary;

    @Column(columnDefinition = "VARCHAR(5000)")
    private String description;

    @ManyToOne
    private ProjectVersion projectVersion;

    @Enumerated
    private ReportPriority priority;

    @ManyToOne
    private Reporter assigned;

    @ManyToOne
    private Reporter author;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportPriority getPriority() {
        return priority;
    }

    public void setPriority(ReportPriority priority) {
        this.priority = priority;
    }

    public Reporter getAssigned() {
        return assigned;
    }

    public void setAssigned(Reporter assigned) {
        this.assigned = assigned;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportResolution getResolution() {
        return resolution;
    }

    public void setResolution(ReportResolution resolution) {
        this.resolution = resolution;
    }

    public ProjectVersion getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(ProjectVersion occursIn) {
        this.projectVersion = occursIn;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @PrePersist
    void updateDates() {
        if (timestamp == null) {
            timestamp = new Date();
        }
    }

    public Reporter getAuthor() {
        return author;
    }

    public void setAuthor(Reporter author) {
        this.author = author;
    }

    @Transient
    public Object getRealStatus() {
        if (status == ReportStatus.OPEN) {
            return status;
        } else {
            return resolution;
        }
    }

    @Transient
    public void setRealStatus(Object o) {

        if (o instanceof ReportStatus) {
            status = ReportStatus.OPEN;
            resolution = null;
        } else {
            status = ReportStatus.CLOSED;
            resolution = (ReportResolution) o;
        }
    }

    @Transient
    public static ReportResolution getReportResolution(Object x) {
        if (x.equals("Needs more information")) {
            return ReportResolution.NEEDMOREINFO;
        }
        final String s = ((String) x).toUpperCase().replaceAll(" ", "")
                .replaceAll("'", "");
        return ReportResolution.valueOf(ReportResolution.class, s);
    }

    public boolean summaryOrDescriptionLike(String searchEntry) {
        if (this.summary != null && this.stringLike(this.summary, searchEntry)) {
            return true;
        }
        if (this.description != null
                && this.stringLike(this.description, searchEntry)) {
            return true;
        }
        return false;
    }

    public boolean stringLike(String origin, String searchEntry) {
        return origin.toLowerCase().contains(searchEntry.toLowerCase());
    }
}