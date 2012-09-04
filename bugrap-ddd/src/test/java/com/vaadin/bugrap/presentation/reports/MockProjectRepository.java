package com.vaadin.bugrap.presentation.reports;

import com.vaadin.bugrap.business.projects.boundary.ProjectRepository;
import com.vaadin.bugrap.business.projects.entity.Project;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Specializes;

/**
 *
 * @author adam-bien.com
 */
public class MockProjectRepository extends ProjectRepository implements Serializable{

    @Override
    public List<Project> getProjects() {
        final Project project = new Project();
        project.setName("duke");
        return new ArrayList<Project>(){{
            add(project);
        }};
    }
    
}
