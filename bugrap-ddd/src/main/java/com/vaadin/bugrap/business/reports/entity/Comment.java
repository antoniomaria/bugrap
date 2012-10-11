package com.vaadin.bugrap.business.reports.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.users.entity.Reporter;

@Entity
public class Comment extends AbstractEntity {

    @ManyToOne
    private Reporter author;

    private String comment;

    @Enumerated
    private CommentType type;

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
}
