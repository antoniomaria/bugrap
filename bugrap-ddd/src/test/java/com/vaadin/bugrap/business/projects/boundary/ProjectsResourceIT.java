package com.vaadin.bugrap.business.projects.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.users.entity.Reporter;

/**
 * 
 * @author adam-bien.com
 */

@Ignore
public class ProjectsResourceIT {
    private static String RESOURCE_URI = "http://localhost:8080/bugrap-ddd-1.0-SNAPSHOT/resources/projects";
    private Client client;
    private WebResource target;

    @Before
    public void init() {
        this.client = Client.create();
        this.target = this.client.resource(RESOURCE_URI);
    }

    @Test
    public void createProject() {
        Reporter reporter = new Reporter();
        reporter.setAdmin(true);
        reporter.setEmail("admin@vaadin.com");
        reporter.setName("vaadin");
        reporter.setPassword("chief");
        Project project = new Project();
        project.setName("vaadin " + new Date());
        project.setManager(reporter);

        List<Reporter> developers = new ArrayList<Reporter>() {
            {
                add(getReporter("first"));
                add(getReporter("second"));
                add(getReporter("third"));
            }
        };
        project.setDevelopers(developers);
        ClientResponse response = this.target.accept(MediaType.TEXT_PLAIN)
                .entity(project, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);
        assertThat(response.getStatus(), is(200));
        String id = response.getEntity(String.class);
        System.out.println("Created id: " + id);
        Project recentlyCreated = this.target.path(id)
                .accept(MediaType.APPLICATION_XML).get(Project.class);
        assertNotNull(recentlyCreated);

    }

    Reporter getReporter(String name) {
        Reporter reporter = new Reporter();
        reporter.setAdmin(true);
        reporter.setEmail(name + "@vaadin.com");
        reporter.setName(name);
        reporter.setPassword("pwd" + name);
        return reporter;
    }

}
