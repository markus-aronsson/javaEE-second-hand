package com.sndhand.web.serviceBased;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * A wrapper for the primitive values
 * 
 * This class was constructed by the teacher of the course, during which 
 * this application was developed.
 * 
 */

@XmlRootElement(name = "primitive")
@XmlAccessorType(XmlAccessType.PROPERTY)
public final class PrimitiveWrapper<T> {
    
    private T value; 
    
    protected PrimitiveWrapper(){
    }
    
    PrimitiveWrapper(T value){
        this.value = value;
    }
    
    @XmlElement()
    public T getValue(){
        return value;
    }
    
}

