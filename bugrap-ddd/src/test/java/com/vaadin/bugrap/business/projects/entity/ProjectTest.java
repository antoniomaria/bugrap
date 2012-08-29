package com.vaadin.bugrap.business.projects.entity;

import com.vaadin.bugrap.business.reports.entity.Report;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;

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
        List<Report> reportsForVersion = this.cut.getReportsForVersion(searchCriteria);
        assertThat(reportsForVersion.size(),is(1));
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
