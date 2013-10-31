package com.sndhand.web.core;

import com.sndhand.web.core.utils.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 * 
 * A product that can be added
 * to a shop.
 * 
 */
@Entity
public class Product extends AbstractEntity implements Serializable {
    private String     name;
    private Double     price;
    private Categories category;
    private String     imageURI;
    
    public enum Categories {Clothing, Furniture, Book, KnickKnack, Empty};

    protected Product() {}
    
    protected Product(Builder builder) {        
        this.name     = builder.name;
        this.price    = builder.price;
        this.category = builder.category;
        this.imageURI = builder.imageURI;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setCategory(Categories category) {
        this.category = category;
    }
    
    public Categories getCategory() {
        return category;
    }
    
    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
    
    public String getImageURI() {
        return imageURI;
    }
    
    /**************************************************************************/
    /*                             Builder Pattern                            */
    /**************************************************************************/
    
    public static class Builder extends AbstractEntity.Builder<Builder, Product> {
        private String     name;
        private Double     price;
        private Categories category = Categories.Empty;
        private String     imageURI = "../resources/img/tea.jpg";
        
        public Builder(String name, Double price) {
            this.name     = name;
            this.price    = price;
        }
        
        public Builder category(Categories category) {
            this.category = category; return this;
        }
        
        public Builder imageURI(String uri) {
            this.imageURI = uri; return this;
        }
        
        @Override
        public Product build() {
            return new Product(this);
        }
    }
}
