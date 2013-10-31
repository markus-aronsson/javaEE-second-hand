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
 * Backing bean for page where the 
 * user with a store can add a 
 * product to it
 * 
 */
@Named("addProduct")
@ConversationScoped
public class AddProductBB implements Serializable {
    
    private Product.Categories category;
    private String  imageURI;
    private Integer prodID;
    private Integer shopID;
    
    @NotNull(message = "This field may not be empty") @Size(min = 4, max = 20, message = "Product name must be 4-20 characters")
    private String name;
    
    @NotNull(message = "This field may not be empty") @Size(min = 0, max = 100000, message = "Price must be 0 - 100000")
    private String price;
    
    @Inject
    private ContainerBean container;
    
    @Inject
    private Conversation conversation;
    
    public String add() {
        Shop            s = container.getContainer().getShops().find(shopID);
        Product.Builder b = new Product.Builder(name, Double.valueOf(price));
        
        if (category != null) {
            b.category(category);
        }
        if (imageURI != null) {
            b.imageURI(imageURI);
        }
        
        s.getProducts().add(b.build());
        
        container.getContainer().getShops().update(s);
        destroy();
        
        return "PRODUCT_ADD_SUCCESS";
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
        
        add();
        return "";
    }
    
    public void setSelected(String sid) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        
        this.shopID = Integer.valueOf(sid);
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
