package com.sndhand.web.core;

import com.sndhand.web.core.utils.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 *  A user who has registered
 *  and been given a user name and 
 *  password.
 * 
 */
@Entity
@Table(name="users")
public class User extends AbstractEntity implements Serializable {
    
    private String username;
    private String password;
    
    protected User() {}
    
    protected User(Builder builder) {
        this.username  = builder.username;
        this.password  = builder.password;
       
    }
    
    public String getName() {
        return username;
    }
    
    public String getPswd() {
        return password;
    }
    
    public void setName(String name) {
        this.username = name;
    }
    
    /**************************************************************************/
    /*                              User specifics                            */
    /**************************************************************************/
    
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (other instanceof User) {
            return username.equals(((User) other).getName());
        } else {
            return false;
        }
    }
    
    /**************************************************************************/
    /*                             Builder Pattern                            */
    /**************************************************************************/
    
    public static class Builder {
        private String username;
        private String password;
        
        public Builder(String name, String pswd) {
            this.username = name;
            this.password  = pswd;
        }

        public User build() {
            return new User(this);
        }
    }
}
