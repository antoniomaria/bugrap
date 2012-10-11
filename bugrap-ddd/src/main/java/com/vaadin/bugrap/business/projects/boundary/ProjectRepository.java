package com.vaadin.bugrap.business.projects.boundary;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaQuery;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.bugrap.business.reports.entity.Report;
import com.vaadin.bugrap.business.users.entity.Reporter;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ProjectRepository {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    /**
     * Get all projects.
     * 
     * @return Returns all projects
     */
    public List<Project> getProjects() {
        return this.findAll(Project.class);
    }

    /**
     * Get project by id
     * 
     * @param id
     *            The id of the project
     * @return Returns the project with the corresponding id or NULL if not
     *         found
     */
    public Project getProject(long id) {
        return this.em.find(Project.class, id);
    }

    /**
     * Get project version by id
     * 
     * @param id
     *            The id of the version
     * 
     * @return Returns the project version with the corresponding id or NULL if
     *         not found
     */
    public ProjectVersion getVersion(long id) {
        return this.em.find(ProjectVersion.class, id);
    }

    /**
     * Get a report by its id
     * 
     * @param id
     *            The id of the report
     * @return The report which id has been given or NULL if not found
     */
    public Report getReport(long id) {
        return this.em.find(Report.class, id);
    }

    /**
     * Get a report bu his id
     * 
     * @param id
     *            The id of the reporter
     * @return
     */
    public Reporter getReporter(long id) {
        return this.em.find(Reporter.class, id);
    }

    /**
     * Saves any entity to the database and commits the transaction
     * 
     * @param entity
     *            The entity to save
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <A extends AbstractEntity> A store(A entity) {
        return this.em.merge(entity);
    }

    /**
     * Get all users
     * 
     * @return Returns a list of all users
     */
    public List<Reporter> getReporters() {
        return findAll(Reporter.class);
    }

    /**
     * Get all project versions
     * 
     * @return Returns a list of all project versions
     */
    public List<ProjectVersion> getVersions() {
        return findAll(ProjectVersion.class);
    }

    <T extends AbstractEntity> List<T> findAll(Class<T> clazz) {
        CriteriaQuery<T> query = this.em.getCriteriaBuilder()
                .createQuery(clazz);
        query.from(clazz);
        return this.em.createQuery(query).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save() {
    }
}
