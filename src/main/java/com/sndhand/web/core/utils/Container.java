package com.sndhand.web.core.utils;

import com.sndhand.web.core.dao.catalogues.GroupCatalogue;
import com.sndhand.web.core.dao.catalogues.ShopCatalogue;
import com.sndhand.web.core.dao.catalogues.UserCatalogue;
import java.io.Serializable;

/**
 * A container for all the catalogues of the application. 
 * These are common to all users.
 * 
 */
public class Container implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ShopCatalogue shops;
    private UserCatalogue users;
    private GroupCatalogue groups;
    
    public Container(String puName) {
        shops = new ShopCatalogue(puName);
        users = new UserCatalogue(puName);
        groups = new GroupCatalogue(puName);
    };
    
    public ShopCatalogue getShops() {
        return shops;
    }
    
    public UserCatalogue getUsers() {
        return users;
    }
    
    public GroupCatalogue getGroups() {
        return groups;
    }

}
