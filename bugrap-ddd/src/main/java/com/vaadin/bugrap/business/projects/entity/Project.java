package com.vaadin.bugrap.business.projects.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.vaadin.bugrap.business.AbstractEntity;
import com.vaadin.bugrap.business.users.entity.Reporter;

@Entity
public class Project extends AbstractEntity {

    private String name;

    @ManyToOne
    private Reporter manager;

    @OneToMany
    private Set<Reporter> participants;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "project")
    private List<ProjectVersion> projectVersions;

    public Project() {
        participants = new HashSet<Reporter>();
        projectVersions = new ArrayList<ProjectVersion>();
    }

    public ProjectVersion addProjectVersion(String versionName) {
        ProjectVersion version = new ProjectVersion();
        version.setVersion(versionName);
        version.setProject(this);

        projectVersions.add(version);

        return version;
    }

    public List<ProjectVersion> getProjectVersions() {
        return Collections.unmodifiableList(projectVersions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Reporter getManager() {
        return manager;
    }

    public void setManager(Reporter manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return name;
    }

    public Set<Reporter> getParticipants() {
        return participants;
    }

    public void addParticipant(Reporter participant) {
        participants.add(participant);
    }

    public void removeParticipant(Reporter participant) {
        participants.remove(participant);
    }
}
