package com.vaadin.bugrap.presentation.reports;

import com.vaadin.bugrap.presentation.reports.events.ReportBugEvent;
import com.vaadin.cdi.VaadinContext;
import com.vaadin.server.WrappedRequest;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

/**
 *
 * @author adam-bien.com
 */
@VaadinContext.VaadinUIScoped
@RunWith(Arquillian.class)
public class ReportsUIIT {
    
    @Inject
    ReportsUI cut;
    
    @Inject
    TopBar topBar;

    @Inject
    ReportsListing reportListing;

    @Inject
    ReportEditor reportEditor;
    
    @Inject 
    private Event<ReportBugEvent> reportBugEvent;


    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "reports.jar").
                addClasses(ReportsUIIT.class,MockProjectRepository.class,ReportsUI.class,
                TopBar.class,ReportsListing.class,ReportEditor.class).
                addAsManifestResource(
                new ByteArrayAsset("<beans/>".getBytes()),
                ArchivePaths.create("beans.xml"));
    }
    
    @Before
    public void init(){
        WrappedRequest request = mock(WrappedRequest.class);
        cut.init(request);
    }
    
    @Test
    public void newBug(){
        ReportBugEvent rbe = new ReportBugEvent();
        reportBugEvent.fire(rbe);
    }

}
