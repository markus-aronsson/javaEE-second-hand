package com.sndhand.web.componentBased;

import com.sndhand.web.core.utils.Container;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

/**
 * Application scoped wrapper for the container.
 * EJB - beans for the catalogues would have
 * been to prefer...
 * 
 */

@ApplicationScoped
public class ContainerBean {
     
    private final Container c;
    private final String PROJECT_PU = "project_pu";
    
    public ContainerBean() {
        c = new Container(PROJECT_PU);
    }
    
    public ContainerBean(String puName) {
        c = new Container(puName);
    }
   
    @Produces
    public Container getContainer() {
        return c;
    }
    
    public String getPU() {
        return PROJECT_PU;
    }
}
