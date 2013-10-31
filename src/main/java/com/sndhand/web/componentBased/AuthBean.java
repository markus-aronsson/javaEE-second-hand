package com.sndhand.web.componentBased;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * A managed bean for authentication.
 * The JDBC-realm is used.
 * 
 */
@Named("authenticate")
@ManagedBean
@SessionScoped
public class AuthBean implements Serializable {
    
    private String name;
    private String pswd;
    
    @Inject
    private ContainerBean c;
    
    /*
     * Return the name of the logged in user
     */
    public String getName() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        return request.getUserPrincipal().getName();
    }

    /*
     * Set the name of the logged in user
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Return the password of the logged in user
     */
    public String getPswd() {
        return pswd;
    }

    /*
     * Set the password of the logged in user
     */
    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
    
    /*
     * Log out the currently logged in user
     */
    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
         
        try {
            request.logout();
        } catch (ServletException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to logout user!", e);
    
        }
    }
}
