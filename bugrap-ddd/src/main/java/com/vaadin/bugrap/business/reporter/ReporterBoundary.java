package com.vaadin.bugrap.business.reporter;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.vaadin.bugrap.business.users.entity.Reporter;

@Stateful
@SessionScoped
public class ReporterBoundary {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public Reporter getReporter(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Reporter> criteriaQuery = criteriaBuilder
                .createQuery(Reporter.class);
        Root<Reporter> from = criteriaQuery.from(Reporter.class);
        Predicate usernameCondition = criteriaBuilder.equal(
                from.get(Reporter.PROPERTY_USERNAME), username);

        criteriaQuery.where(usernameCondition);

        return em.createQuery(criteriaQuery).getSingleResult();
    }

    public boolean reporterExists(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Reporter> from = cq.from(Reporter.class);

        Predicate usernameCondition = cb.equal(
                from.get(Reporter.PROPERTY_USERNAME), username);

        cq.select(cb.count(from)).where(usernameCondition);

        return em.createQuery(cq).getSingleResult() == 1;
    }

    public Reporter createNewReporter(String username) {
        Reporter reporter = new Reporter();
        reporter.setUsername(username);

        return em.merge(reporter);
    }
}
