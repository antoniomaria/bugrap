package com.vaadin.bugrap.business;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;

/**
 *
 * @author adam-bien.com
 */
@DataSourceDefinition(
    className="org.apache.derby.jdbc.ClientDataSource",
    serverName="localhost",
    name="java:global/jdbc/bugrap",
    databaseName="bugrap;create=true",
    portNumber=1527,
    user="bugrap",
    password="bugrap"
)
@Singleton
public class DataSourceConfiguration {
    
}
