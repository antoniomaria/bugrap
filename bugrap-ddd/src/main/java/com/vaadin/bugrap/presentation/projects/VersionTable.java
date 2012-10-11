package com.vaadin.bugrap.presentation.projects;

import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.bugrap.business.projects.entity.ProjectVersion;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

public class VersionTable extends CustomComponent {

    private Table table;

    @PostConstruct
    public void init() {
        setSizeFull();

        table = new Table();
        table.setSizeFull();

        setCompositionRoot(table);
    }

    public void populateVersions(List<ProjectVersion> versions) {
        BeanItemContainer<ProjectVersion> versionsContainer = new BeanItemContainer<ProjectVersion>(
                ProjectVersion.class);
        versionsContainer.addAll(versions);

        table.setContainerDataSource(versionsContainer);
    }

}
