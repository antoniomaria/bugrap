package com.vaadin.bugrap.business.projects.boundary;

import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.reports.entity.Report;
import com.vaadin.bugrap.business.reports.entity.ReportType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("integration");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }
    
    @Before
    public void initialize(){
        cut = new ProjectRepository();
        cut.em = em;
    }

    @Test
    public void createEmptyProject(){
        this.cut.store(new Project());
    }
    
    @Test
    public void createProjectWithReport(){
        Report r = new Report();
        r.setDescription("empty report");
        r.setSummary("A summary");
        r.setType(ReportType.BUG);
        Project p = new Project();
        p.setName("Vaadin 42");
        p.addReport(r);
        this.cut.store(p);
    }
}
