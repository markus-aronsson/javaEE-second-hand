package com.sndhand.web.core;

import com.sndhand.web.core.utils.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 *  A group that a user can belong to.
 *  Needed for authentication.
 * 
 */
@Entity
@Table(name="user_groups")
public class Group extends AbstractEntity implements Serializable {
    
    private String username;
    private String groupname;

    public void setGroupName(String gn) {
       this.groupname = gn;
    }
   
    public String getGroupName() {
       return groupname;
    } 
    
    public void setUserName(String user) {
       this.username = user;
    }
    
    public String getUserName() {
       return username;
    } 
}
