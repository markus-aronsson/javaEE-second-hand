package com.sndhand.web.serviceBased;

import com.sndhand.web.core.Product;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *  Product proxy (a wrapper)
 * 
 */
@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProductProxy {
    private Product product;

    protected ProductProxy() { }
   
    public ProductProxy(Product product) { 
        this.product = product; 
    }
    
    @XmlAttribute(required = true)
    public Integer getId() {
        return product.getId();
    }
    
    @XmlAttribute(required = true)
    public String getName() {
        return product.getName();
    }

    @XmlAttribute(required = true)
    public double getPrice() {
        return product.getPrice();
    }
    
    @XmlAttribute(required = true)
    public String getImageURI() {
        return product.getImageURI();
    }
}
