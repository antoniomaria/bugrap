package com.vaadin.bugrap.business.projects.boundary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.bugrap.business.reports.entity.Report;
import com.vaadin.bugrap.business.reports.entity.ReportType;

/**
 * 
 * @author adam-bien.com
 */
public class ProjectRepositoryIT {

    private static EntityManager em;
    private static EntityTransaction tx;

    private ProjectRepository cut;

    @BeforeClass
    public static void initalizeEm() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("integration");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @Before
    public void initialize() {
        cut = new ProjectRepository();
        cut.em = em;
    }

    @Test
    public void createEmptyProject() {
        this.cut.store(new Project());
    }

    @Test
    public void createProjectWithReport() {
        Report r = new Report();
        r.setDescription("empty report");
        r.setSummary("A summary");
        r.setType(ReportType.BUG);
        Project p = new Project();
        p.setName("Vaadin 42");
        p.addReport(r);
        this.cut.store(p);
    }

    @Test
    public void createProjectWithVersion() {
        ProjectVersion version = new ProjectVersion();
        version.setVersion("1.0 alpha");
        Project p = new Project();
        p.setName("Vaadin 53");
        p.addProjectVersion(version);
        this.cut.store(p);

        Assert.assertEquals(p, version.getProject());
    }
}
