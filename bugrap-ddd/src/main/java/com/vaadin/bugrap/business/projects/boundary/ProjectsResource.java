package com.vaadin.bugrap.business.projects.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.vaadin.bugrap.business.projects.entity.Project;

/**
 * 
 * @author adam-bien.com
 */
@Path("projects")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Stateless
public class ProjectsResource {

    @PersistenceContext
    EntityManager em;

    @POST
    public String createProject(Project project) {
        Project managed = em.merge(project);
        em.flush();
        em.refresh(managed);
        return String.valueOf(managed.getId());
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Project> all() {
        CriteriaQuery<Project> query = this.em.getCriteriaBuilder()
                .createQuery(Project.class);
        return this.em.createQuery(query).getResultList();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Project findWithId(@PathParam("id")
    long id) {
        return this.em.find(Project.class, id);
    }

}
