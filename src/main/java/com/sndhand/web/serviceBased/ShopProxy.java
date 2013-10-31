package com.sndhand.web.serviceBased;

import com.sndhand.web.core.Shop;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Shop proxy (a wrapper)
 * 
 */
@XmlRootElement(name="shop")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ShopProxy {
    
    private Shop shop;
    
    protected ShopProxy() {
        
    }
    
    public ShopProxy(Shop shop) {
        this.shop = shop;
    }
    @XmlAttribute(required = true)
    public Integer getId() {
        return shop.getId();
    }
    
    @XmlAttribute(required = true)
    public String getName() {
        return shop.getName();
    }

    @XmlAttribute(required = true)
    public double getLat() {
        return (float) shop.getLatitude();
    }
    
    @XmlAttribute(required = true)
    public double getLong() {
        return (float) shop.getLongitude();
    }
    
    @XmlAttribute(required = true)
    public String getDescription() {
        return shop.getDescription();
    }
    
    @XmlAttribute
    public double getRating() {
        return shop.getAvgrating();
    }
}
