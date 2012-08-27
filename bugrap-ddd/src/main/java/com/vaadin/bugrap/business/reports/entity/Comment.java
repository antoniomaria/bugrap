package com.vaadin.bugrap.business.reports.entity;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.users.entity.Reporter;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="",query="SELECT c FROM Comment c WHERE c.report=:rep")
public class Comment extends AbstractEntity {

        public static final String PREFIX ="com.vaadin.bugrap.business.reports.entity.Comment.";
        public static final String commentsForReport = PREFIX + "commentsForReport";

	@ManyToOne
	private Reporter author;

	// @Column(name = "COMMENT", columnDefinition = "LONGVARCHAR")
	@Column(name = "COMMENT", columnDefinition = "VARCHAR(5000)")
	private String comment;

	@Enumerated
	private CommentType type;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ATTACHMENT", columnDefinition = "BYTEA")
	@Lob
	private byte[] attachment;

	private String attachmentName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Report report;

	public Reporter getAuthor() {
		return author;
	}

	public void setAuthor(Reporter author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public CommentType getType() {
		return type;
	}

	public void setType(CommentType type) {
		this.type = type;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	@PrePersist
	void updateDates() {
		if (timestamp == null) {
			timestamp = new Date();
		}
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
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
