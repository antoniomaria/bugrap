package com.vaadin.bugrap.presentation.reports;

import com.vaadin.server.WrappedRequest;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

/**
 *
 * @author adam-bien.com
 */
@RunWith(Arquillian.class)
public class ReportsUITest {
    
    @Inject
    ReportsUI cut;
    
    @Inject
    private TopBar topBar;

    @Inject
    private ReportsListing reportListing;

    @Inject
    private ReportEditor reportEditor;

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "reports.jar").
                addClasses(MockProjectRepository.class,ReportsUI.class,TopBar.class,ReportsListing.class,ReportEditor.class).
                addAsManifestResource(
                new ByteArrayAsset("<beans/>".getBytes()),
                ArchivePaths.create("beans.xml"));
    }
    
    @Test
    public void init(){
        WrappedRequest request = mock(WrappedRequest.class);
        cut.init(request);
    }

}
