package com.vaadin.bugrap.presentation.projects;

import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

public class ProjectTable extends CustomComponent {

    private Table table;

    @PostConstruct
    public void init() {
        setSizeFull();

        table = new Table();
        table.setImmediate(true);
        table.setSelectable(true);
        table.setSizeFull();

        setCompositionRoot(table);
    }

    public void populateProjects(List<Project> projects) {
        BeanItemContainer<Project> projectContainer = new BeanItemContainer<Project>(
                Project.class);
        projectContainer.addAll(projects);

        table.setContainerDataSource(projectContainer);
        table.setVisibleColumns(new String[] { "name" });
    }

    public Project getSelectedProject() {
        return (Project) table.getValue();
    }

    public boolean isProjectSelected() {
        return table.getValue() != null;
    }

    public void addValueChangeListener(ValueChangeListener valueChangeListener) {
        table.addValueChangeListener(valueChangeListener);
    }
}
