package com.sndhand.web.componentBased.bb;

import java.io.Serializable;
import com.sndhand.web.core.Product;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Class for providing the values
 * of the categories that a product
 * can be in
 * 
 */
@Named
@RequestScoped
public class EnumValuesProvider implements Serializable {
    
    public Product.Categories[] getCategories() {
        return Product.Categories.values();
    }
}