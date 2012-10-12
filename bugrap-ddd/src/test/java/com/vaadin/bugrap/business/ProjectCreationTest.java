package com.vaadin.bugrap.business;

import java.util.List;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.vaadin.bugrap.business.projects.ProjectBoundary;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.bugrap.business.users.entity.Reporter;

@RunWith(Arquillian.class)
public class ProjectCreationTest {

    @EJB
    ProjectBoundary projectBoundary;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ProjectBoundary.class).addClass(Project.class)
                .addClass(AbstractEntity.class).addClass(ProjectVersion.class)
                .addClass(Reporter.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    public void testProjectCreation() {
        Assert.assertEquals(0, projectBoundary.getProjects().size());

        Project project = projectBoundary.createProject();

        Assert.assertTrue(project.isPersistent());

        Project other = projectBoundary.findById(project.getId());

        Assert.assertSame(project, other);

        Assert.assertEquals(0, projectBoundary.getProjects().size());

        projectBoundary.save();

        List<Project> projects = projectBoundary.getProjects();

        Assert.assertSame(project, projects.get(0));

        Assert.assertTrue(project.isPersistent());

        Assert.assertEquals(1, projectBoundary.getProjects().size());
    }

    @Test
    public void testProjectCreationAborted() {
        Assert.assertEquals(0, projectBoundary.getProjects().size());

        Project project = projectBoundary.createProject();

        Assert.assertTrue(project.isPersistent());

        Assert.assertEquals(0, projectBoundary.getProjects().size());

        projectBoundary.removeProject(project);

        Assert.assertEquals(0, projectBoundary.getProjects().size());
    }
}
