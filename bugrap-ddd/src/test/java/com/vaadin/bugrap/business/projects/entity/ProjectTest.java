package com.vaadin.bugrap.business.projects.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.bugrap.business.reports.entity.Report;
import java.util.Set;

/**
 * 
 * @author adam-bien.com
 */
public class ProjectTest {

    Project cut;

    @Before
    public void init() {
        this.cut = new Project();
    }

    @Test
    public void reportsForVersion() {
        int expectedId = 4;
        this.cut.addReport(getReport(1));
        this.cut.addReport(getReport(2));
        this.cut.addReport(getReport(3));
        final Report expectedReport = getReport(expectedId);
        this.cut.addReport(expectedReport);
        ProjectVersion searchCriteria = new ProjectVersion();
        searchCriteria.setId(expectedId);
        Set<Report> reportsForVersion = this.cut
                .getReportsForVersion(searchCriteria);
        assertThat(reportsForVersion.size(), is(1));
        assertTrue(reportsForVersion.contains(expectedReport));
    }

    Report getReport(long versionId) {
        Report report = new Report();
        report.setId(versionId);
        ProjectVersion origin = new ProjectVersion();
        origin.setId(versionId);
        report.setVersion(origin);
        return report;
    }
}
