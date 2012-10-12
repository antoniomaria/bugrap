package com.vaadin.bugrap.business.projects;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.vaadin.bugrap.business.projects.entity.Project;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ProjectBoundary {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public List<Project> getProjects() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder
                .createQuery(Project.class);

        criteriaQuery.from(Project.class);

        return em.createQuery(criteriaQuery).getResultList();
    }

    public Project findById(long id) {
        return em.find(Project.class, id);
    }

    public Project createProject() {
        Project project = new Project();
        em.persist(project);

        return project;
    }

    public void removeProject(Project project) {
        em.remove(em.getReference(Project.class, project.getId()));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save() {

    }
}
