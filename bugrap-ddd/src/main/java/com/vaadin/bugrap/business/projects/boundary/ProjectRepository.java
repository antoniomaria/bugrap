package com.vaadin.bugrap.business.projects.boundary;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.projects.control.PasswordGenerator;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.bugrap.business.reports.entity.Comment;
import com.vaadin.bugrap.business.reports.entity.Report;
import com.vaadin.bugrap.business.reports.entity.ReportPriority;
import com.vaadin.bugrap.business.reports.entity.ReportResolution;
import com.vaadin.bugrap.business.reports.entity.ReportStatus;
import com.vaadin.bugrap.business.reports.entity.ReportType;
import com.vaadin.bugrap.business.users.entity.Reporter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaQuery;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ProjectRepository {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    /**
     * Get all reports for a project which has not been assigned to a version
     *
     * @param project The project to search for
     * @return Returns a list of reports which have not been assigned to a
     * version
     */
    public List<Report> getUnassignedReports(Project project) {
        return this.em.createNamedQuery(Report.unassignedReports).
                setParameter("proj", project).
                getResultList();
    }

    /**
     * Get all reports for a version
     *
     * @param version The project version to search for
     * @return Returns a list of reports which belong to a version
     */
    public List<Report> getReportsForVersion(ProjectVersion version) {
        return em.createNamedQuery(Report.forVersion).
                setParameter("proj", version.getProject()).
                setParameter("ver", version).
                getResultList();
    }

    /**
     * Get a user by username and password
     *
     * @param username The username of the user
     * @param password The password of the user
     * @return Returns a user if the username and password match, else NULL
     */
    public Reporter getUser(String username, String password) {
        return (Reporter) em.createNamedQuery(Report.findUser).
                setParameter("user", username).
                setParameter("password", PasswordGenerator.encrypt(password)).
                getSingleResult();
    }

    /**
     * Get all projects.
     *
     * @return Returns all projects
     */
    public List<Project> getProjects() {
        return this.findAll(Project.class);
    }

    /**
     * Get the projects which have not been closed
     *
     * @return A list of projects which has not been closed
     */
    public List<Project> getActiveProjects() {
        return this.em.createNamedQuery(Project.activeProjects).
                setParameter("closed", false).
                getResultList();
    }

    /**
     * Get project by id
     *
     * @param id The id of the project
     * @return Returns the project with the corresponding id or NULL if not
     * found
     */
    public Project getProject(long id) {
        return this.em.find(Project.class, id);
    }

    /**
     * Get project version by id
     *
     * @param id The id of the version
     *
     * @return Returns the project version with the corresponding id or NULL if
     * not found
     */
    public ProjectVersion getVersion(long id) {
        return this.em.find(ProjectVersion.class, id);
    }

    /**
     * Get a report by its id
     *
     * @param id The id of the report
     * @return The report which id has been given or NULL if not found
     */
    public Report getReport(long id) {
        return this.em.find(Report.class, id);
    }

    /**
     * Get a report bu his id
     *
     * @param id The id of the reporter
     * @return
     */
    public Reporter getReporter(long id) {
        return this.em.find(Reporter.class, id);
    }

    /**
     * Saves any entity to the database and commits the transaction
     *
     * @param entity The entity to save
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <A extends AbstractEntity> A store(A entity) {
        return this.em.merge(entity);
    }

    /**
     * Get a reporter by either email or username.
     *
     * @param username The username or NULL if searching by email
     * @param email The email or NULL if search by username
     * @return A reporter which matches the criteria or NULL if not found
     */
    public Reporter getReporterByNameOrEmail(String username,
            String email) {
        return (Reporter) this.em.createNamedQuery(Reporter.reporterByNameOrEmail).setParameter("email", email).setParameter("name", username).getSingleResult();
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
        CriteriaQuery<T> query = this.em.getCriteriaBuilder().createQuery(clazz);
        return this.em.createQuery(query).getResultList();
    }

    /**
     * Get versions for a project
     *
     * @param project The project to get versions for
     * @return
     */
    public List<ProjectVersion> getVersions(Project project) {
        return this.em.createNamedQuery(ProjectVersion.projectVersion).
                setParameter("proj", project).
                getResultList();
    }

    public List<Comment> getComments(Report report) {
        return this.em.createNamedQuery(Comment.commentsForReport).
                setParameter("rep", report).
                getResultList();
    }

    /**
     * Gets the n latest reports
     *
     * @param amount The amount of reports to get
     * @return Returns the n latest reports
     */
    public List<Report> getLatestReports(int amount) {
        return this.em.createNamedQuery(Report.latest).
                setMaxResults(amount).
                getResultList();
    }

    public List<Report> getLatestReports(Project project,
            ProjectVersion version, int amount) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder query = new StringBuilder("SELECT r FROM Report r WHERE ");
        if (project != null) {
            params.put("proj", project);
            query.append("r.project=:proj ");
        } else {
            query.append("r.project IS NULL ");
        }
        if (version != null) {
            params.put("pv", version);
            query.append("AND r.version=:pv ");
        } else {
            query.append("AND r.version IS NULL ");
        }

        query.append("ORDER BY r.timestamp DESC");
//TODO: migration to criteria necessary
//        List<Report> result = this.em.list(query.toString(),
//                params, amount);
            return Collections.EMPTY_LIST;
    }

    /**
     * Counts reports in a certain version and status
     *
     * @param version The project version the reports are in
     * @param status The status the reports have
     * @return The number of reports matching that criteria
     */
    public long countReports(ProjectVersion version, ReportStatus status) {
        return (Long)this.em.createNamedQuery(Report.countWithStatus).
        setParameter("pv", version).
        setParameter("status", status).getSingleResult();
        
     }

    /**
     * Counts reports in a certain project, version and status
     *
     * @param version The project version the reports are in
     * @param status The status the reports have
     * @return The number of reports matching that criteria
     */
    public long countReports(Project project, ProjectVersion version,
            ReportStatus status) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder query = new StringBuilder(
                "SELECT COUNT(r) FROM Report r WHERE ");
        if (project != null) {
            params.put("proj", project);
            query.append("r.project=:proj ");
        } else {
            query.append("r.project IS NULL ");
        }
        if (version != null) {
            params.put("pv", version);
            query.append("AND r.version=:pv ");
        } else {
            query.append("AND r.version IS NULL ");
        }
        if (status != null) {
            params.put("status", status);
            query.append("AND r.status=:status");
        } else {
            query.append("AND r.status IS NULL ");
        }
        //TODO: migration to criteria necessary

        return 42;
    }

    /**
     * Get the reports assigned to a specific user
     *
     * @param assignedTo A reporter to who reports are assigned to
     * @return
     */
    public List<Report> getAssignedReports(Project project,
            ProjectVersion version, Reporter assignedTo) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder query = new StringBuilder("SELECT r FROM Report r WHERE ");
        if (project != null) {
            params.put("proj", project);
            query.append("r.project=:proj ");
        } else {
            query.append("r.project IS NULL ");
        }
        if (version != null) {
            params.put("pv", version);
            query.append("AND r.version=:pv ");
        } else {
            query.append("AND r.version IS NULL ");
        }
        if (assignedTo != null) {
            params.put("uid", assignedTo);
            query.append("AND r.assigned=:uid ");
        } else {
            query.append("AND r.assigned IS NULL ");
        }
        //TODO: migration to criteria necessary
        return Collections.EMPTY_LIST;
    }

    /**
     * Search for reports
     *
     * @param searchTerm The search term to find in the report
     * @param project The project the reports should belong to
     * @param status The status of the report
     * @param type The type of the report
     * @param resolution The resolution of the report
     * @param priority The priority of the report
     * @param ignoreProject Should the project criteria be ignored
     * @param ignoreStatus Should the status criteria be ignored
     * @param ignoreType Should the type criteria be ignored
     * @param ignoreResolution Should the resolution criteria be ignored
     * @param ignorePriority Should the priority criteria be ignored
     * @return
     */
    public List<Report> searchReports(String searchTerm,
            Project project, ProjectVersion version, ReportStatus status,
            ReportType type, ReportResolution resolution,
            ReportPriority priority, boolean ignoreProject,
            boolean ignoreProjectVersion, boolean ignoreStatus,
            boolean ignoreType, boolean ignoreResolution, boolean ignorePriority) {

        if (searchTerm == null) {
            searchTerm = "";
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("term", "%" + searchTerm.toLowerCase() + "%");

        StringBuilder query = new StringBuilder(
                "SELECT r FROM Report r WHERE (lower(r.summary) LIKE :term OR lower(r.description) LIKE :term) ");

        // Project restraint
        if (!ignoreProject) {
            query.append(" AND ");
            if (project == null) {
                query.append("r.project IS NULL ");
            } else {
                params.put("proj", project);
                query.append("r.project = :proj ");
            }
        }

        if (!ignoreProjectVersion) {
            query.append(" AND ");
            if (version == null) {
                query.append("r.version IS NULL ");
            } else {
                params.put("ver", version);
                query.append("r.version = :ver ");
            }
        }

        // Status restraint
        if (!ignoreStatus) {
            query.append(" AND ");
            if (status == null) {
                query.append("r.status IS NULL ");
            } else {
                params.put("status", status);
                query.append("r.status = :status ");
            }
        }

        // Type restraint
        if (!ignoreType) {
            query.append(" AND ");
            if (type == null) {
                query.append("r.type IS NULL ");
            } else {
                params.put("type", type);
                query.append("r.type = :type ");
            }
        }

        // Resolution restraint
        if (!ignoreResolution) {
            query.append(" AND ");
            if (resolution == null) {
                query.append("r.resolution IS NULL ");
            } else {
                params.put("res", resolution);
                query.append("r.resolution = :res ");
            }
        }

        // Priority restraint
        if (!ignorePriority) {
            query.append(" AND ");
            if (priority == null) {
                query.append("r.priority IS NULL ");
            } else {
                params.put("priority", priority);
                query.append("r.priority = :priority ");
            }
        }

        try {
            //TODO: migration to criteria necessary
            return Collections.EMPTY_LIST;
    } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save(){}
}
