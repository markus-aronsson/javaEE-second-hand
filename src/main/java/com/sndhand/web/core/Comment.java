package com.sndhand.web.core;

import com.sndhand.web.core.utils.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 * 
 * Class representing a comment made by a 
 * certain user for a certain shop
 * 
 */
@Entity
public class Comment extends AbstractEntity implements Serializable {
    private String author;
    private String text;
    private byte   rating;
    
    public enum Categories {Product, User, Other, Empty};
    
    protected Comment() {}
    
    protected Comment(Builder builder) {
        this.author = builder.author;
        this.text   = builder.text;
        this.rating = builder.rating;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getText() {
        return text;
    }
    
    public Byte getRating() {
        return rating;
    }
    
    /**************************************************************************/
    /*                             Builder Pattern                            */
    /**************************************************************************/
    
    public static class Builder<T> extends AbstractEntity.Builder<Builder, Comment> {
        private String author;
        private String text;
        private byte   rating = 0;
        
        public Builder(String author, String text) {
            this.author = author;
            this.text   = text;
        }
        
        public Builder rating(byte rating) {
            this.rating = rating; return this;
        }
        
        @Override
        public Comment build() {
            return new Comment(this);
        }
    }
}
