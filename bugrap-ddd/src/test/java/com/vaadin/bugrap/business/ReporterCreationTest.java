package com.vaadin.bugrap.business;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.vaadin.bugrap.business.reporter.ReporterBoundary;
import com.vaadin.bugrap.business.users.entity.Reporter;

@RunWith(Arquillian.class)
public class ReporterCreationTest {

    @EJB
    ReporterBoundary reporterBoundary;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ReporterBoundary.class).addClass(Reporter.class)
                .addClass(AbstractEntity.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    public void testReportCreation() {
        Assert.assertFalse(reporterBoundary.reporterExists("peter"));
        reporterBoundary.createNewReporter("peter");
        Assert.assertTrue(reporterBoundary.reporterExists("peter"));
    }
}
