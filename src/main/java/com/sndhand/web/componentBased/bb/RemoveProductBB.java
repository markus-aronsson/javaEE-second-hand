package com.sndhand.web.componentBased.bb;

import com.sndhand.web.componentBased.ContainerBean;
import com.sndhand.web.core.Product;
import com.sndhand.web.core.Shop;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Backing bean for page
 * allowing users with a shop
 * to remove products from it
 * 
 */
@Named("removeProduct")
@ConversationScoped
public class RemoveProductBB implements Serializable {
    
    private Product.Categories category;
    private String  imageURI;
    private Integer prodID;
    private Integer shopID;
    
    @NotNull(message = "This field may not be empty") @Size(min = 4, max = 20, message = "Product name must be 4-20 characters")
    private String name;
    
    @NotNull(message = "This field may not be empty") @Size(min = 0, max = 100000, message = "Price must be 0 - 100000")
    private String price;
    
    @Inject
    private Conversation conversation;
    
    @Inject
    private ContainerBean container;
     
    protected String remove() {
        Shop s = container.getContainer().getShops().find(shopID);
        
        s.removeProduct(prodID);
        
        container.getContainer().getShops().update(s);
        destroy();
        
        return "PRODUCT_REMOVE_SUCCESS";
    }
    
    /**************************************************************************/
    /*                          Conversation Handlers                         */
    /**************************************************************************/
    
    @PreDestroy 
    public void destroy() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    public String actOnSelected() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        remove();
        return "";
    }
    
    // ToDo: Select product
    public void setSelected(String id, String sid) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        
        this.prodID = Integer.valueOf(id);
        this.shopID = Integer.valueOf(sid);
        
        Product p = container.getContainer().getShops().find(shopID).findProduct(prodID);
        if (p != null) {
            this.name     = p.getName();
            this.price    = p.getPrice().toString();
            this.category = p.getCategory();
            this.imageURI = p.getImageURI();
        } else {
            // Product doesn't exist, all fields will be null
        }
    }
    
    /**************************************************************************/
    /*                            Getters & Setters                           */
    /**************************************************************************/
    
    public Integer getId() {
        return prodID;
    }

    public void setId(Integer id) {
        this.prodID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Product.Categories getCategory() {
        return category;
    }

    public void setCategory(Product.Categories category) {
        this.category = category;
    }
    
    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
