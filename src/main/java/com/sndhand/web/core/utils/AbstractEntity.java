package com.sndhand.web.core.utils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * An abstract entity with
 * an auto-generated id.
 * 
 */
@MappedSuperclass
public abstract class AbstractEntity implements IEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(nullable = false)
    protected int id;
    
    public AbstractEntity() {
    }
    
    @Override
    public Integer getId() {
        return id;
    }
    
    @Override
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**************************************************************************/
    /*                             Builder Pattern                            */
    /**************************************************************************/
    
    public static abstract class Builder<T extends AbstractEntity.Builder, K extends AbstractEntity> {
        public abstract K build();
    }
}
