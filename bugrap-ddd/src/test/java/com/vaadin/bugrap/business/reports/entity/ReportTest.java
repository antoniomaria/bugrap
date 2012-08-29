package com.vaadin.bugrap.business.reports.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.bugrap.business.projects.entity.ProjectVersion;

/**
 * 
 * @author adam-bien.com
 */
public class ReportTest {

    Report cut;

    @Before
    public void init() {
        this.cut = new Report();
    }

    @Test
    public void knowsProjectVersion() {
        ProjectVersion origin = new ProjectVersion();
        origin.setId(42);
        this.cut.setVersion(origin);
        assertTrue(this.cut.knowsProjectVersion(origin));
    }

    @Test
    public void doesNotKnowProjectVersion() {
        ProjectVersion origin = new ProjectVersion();
        origin.setId(42);
        this.cut.setVersion(origin);
        ProjectVersion unknown = new ProjectVersion();
        unknown.setId(-1);
        assertFalse(this.cut.knowsProjectVersion(unknown));
    }

    @Test(expected = IllegalArgumentException.class)
    public void knowsProjectVersionWithNullParameter() {
        this.cut.knowsProjectVersion(null);
    }

    @Test
    public void summaryLike() {
        String summary = "vaadin";
        this.cut.setSummary(summary);
        assertTrue(this.cut.summaryOrDescriptionLike("aA"));
    }

    @Test
    public void summaryNotLike() {
        String summary = "vaadin";
        this.cut.setSummary(summary);
        assertFalse(this.cut.summaryOrDescriptionLike("bb"));
    }

    @Test
    public void descriptionLike() {
        String description = "vaadin";
        this.cut.setDescription(description);
        assertTrue(this.cut.summaryOrDescriptionLike("aA"));
    }

    @Test
    public void descriptionNotLike() {
        String description = "vaadin";
        this.cut.setDescription(description);
        assertFalse(this.cut.summaryOrDescriptionLike("bb"));
    }

    @Test
    public void searchEntryIsNull() {
        assertFalse(this.cut.summaryOrDescriptionLike(null));
    }
}
