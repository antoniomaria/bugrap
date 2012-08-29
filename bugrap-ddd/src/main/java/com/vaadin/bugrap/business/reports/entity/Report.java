package com.vaadin.bugrap.business.reports.entity;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.bugrap.business.users.entity.Reporter;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name=Report.unassignedReports,query="SELECT r FROM Report r WHERE r.project = :proj AND r.version IS NULL"),
    @NamedQuery(name=Report.forVersion,query="SELECT r FROM Report r WHERE r.project = :proj AND r.version = :ver"),
    @NamedQuery(name=Report.findUser,query="SELECT r FROM Reporter r WHERE r.name = :user AND r.password = :password"),
    @NamedQuery(name=Report.latest,query="SELECT r FROM Report r ORDER BY r.timestamp DESC"),
    @NamedQuery(name=Report.countWithStatus,query="SELECT COUNT(r) FROM Report r WHERE r.version=:pv AND r.status=:status")
})
public class Report extends AbstractEntity {
        static final String PREFIX ="com.vaadin.bugrap.business.reports.entity.Report.";
        public static final String unassignedReports = PREFIX + "unassignedReports";
        public static final String forVersion = PREFIX + "forVersion";
        public static final String findUser = PREFIX + "findUser";
        public static final String latest = PREFIX + "latest";
        public static final String countWithStatus = PREFIX + "countWithStatus";

        @Column(name = "type")
	private ReportType type;
	@Enumerated
	@Column(name = "status")
	private ReportStatus status;
	@Enumerated
	@Column(name = "resolution")
	private ReportResolution resolution;

	// @Column(name = "SUMMARY", columnDefinition = "LONGVARCHAR")
	@Column(name = "summary", columnDefinition = "VARCHAR(5000)")
	private String summary;

	// @Column(name = "DESCRIPTION", columnDefinition = "LONGVARCHAR")
	@Column(name = "description", columnDefinition = "VARCHAR(5000)")
	private String description;
	@ManyToOne
	private Project project;
	@ManyToOne
	private ProjectVersion version;
	@ManyToOne
	private ProjectVersion occursIn;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
                this.project.addReport(this);
	}

	public ProjectVersion getVersion() {
		return version;
	}

	public void setVersion(ProjectVersion version) {
		this.version = version;
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

	public ProjectVersion getOccursIn() {
		return occursIn;
	}

	public void setOccursIn(ProjectVersion occursIn) {
		this.occursIn = occursIn;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

        public boolean knowsProjectVersion(ProjectVersion otherVersion) {
            if(otherVersion == null){
                throw new IllegalArgumentException("ProjectVersion is null");
            }
            if(this.version == null){
                return false;
            }
            return (this.version.getId() == otherVersion.getId());
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
		if (status == ReportStatus.OPEN)
			return status;
		else
			return resolution;
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
		if (x.equals("Needs more information"))
			return ReportResolution.NEEDMOREINFO;
		final String s = ((String) x).toUpperCase().replaceAll(" ", "")
				.replaceAll("'", "");
		return ReportResolution.valueOf(ReportResolution.class, s);
	}

	@Transient
	public String getReported() {
		long sec = (new GregorianCalendar().getTimeInMillis() - timestamp
				.getTime()) / 1000;
		// return minutes:
		if (sec < 3600) // less than one hour
			return Integer.toString((int) (sec / 60)) + " mins ago";
		// return hours:
		else if (sec < (60 * 60 * 24 * 2)) // less than two days
			return Integer.toString((int) (sec / (60 * 60))) + " hours ago";
		// return days:
		else if (sec < (60 * 60 * 24 * 10)) // less than 10 days
			return Integer.toString((int) (sec / (60 * 60 * 24))) + " days ago";
		// return weeks:
		else if (sec < (60 * 60 * 24 * 35)) // less than 35 days
			return Integer.toString((int) (sec / (60 * 60 * 24 * 7)))
					+ " weeks ago";
		// return months:
		else
			return Integer.toString((int) (sec / (60 * 60 * 24 * 30)))
					+ " months ago";
	}


}
