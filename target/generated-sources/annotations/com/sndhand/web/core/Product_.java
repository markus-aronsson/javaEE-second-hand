package com.sndhand.web.core;

import com.sndhand.web.core.Product.Categories;
import com.sndhand.web.core.utils.AbstractEntity_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.4.2.v20130514-rNA", date="2013-10-21T14:08:08")
@StaticMetamodel(Product.class)
public class Product_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Product, Categories> category;
    public static volatile SingularAttribute<Product, Double> price;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> imageURI;

}