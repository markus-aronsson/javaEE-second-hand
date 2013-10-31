package com.sndhand.web.componentBased.bb;

import com.sndhand.web.componentBased.ContainerBean;
import com.sndhand.web.core.Product;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * Backing bean for page that allows a user with a shop to manage products
 * for that shop
 *
 */
@Named("products")
@SessionScoped
public class ProductsBB implements Serializable {;

    private int id;
    
    @Inject
    private ContainerBean container;
    
    public List<Product> getAll() {
        return container.getContainer().getShops().find(id).getProducts();
    }
    
    public void setSelected(String id) {
        int sid = Integer.valueOf(id);
        
        if (container.getContainer().getShops().find(sid) != null) {
            this.id = sid;
        } else {
            // Shop doesn't exist -> empty products page
        }
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = Integer.valueOf(id);
    }
    
    public String add() {
        return "add";
    }
    
    public String edit() {
        return "edit";
    }
    
    public String remove() {
        return "remove";
    }
    
    public String exit() {
        return "exit";
    }
}
