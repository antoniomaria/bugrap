package com.vaadin.bugrap.business.reports.entity;

import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author adam-bien.com
 */
public class ReportTest {
    
    Report cut;
    
    @Before
    public void init(){
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
    
    @Test(expected=IllegalArgumentException.class)
    public void knowsProjectVersionWithNullParameter() {
        this.cut.knowsProjectVersion(null);
    }
}
