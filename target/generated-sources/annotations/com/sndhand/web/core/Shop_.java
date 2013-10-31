package com.sndhand.web.core;

import com.sndhand.web.core.Comment;
import com.sndhand.web.core.Product;
import com.sndhand.web.core.Shop.OrganizationType;
import com.sndhand.web.core.Shop.Tradition;
import com.sndhand.web.core.User;
import com.sndhand.web.core.utils.AbstractEntity_;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.4.2.v20130514-rNA", date="2013-10-21T14:08:08")
@StaticMetamodel(Shop.class)
public class Shop_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Shop, String> location;
    public static volatile SingularAttribute<Shop, Double> avgrating;
    public static volatile SingularAttribute<Shop, String> description;
    public static volatile SingularAttribute<Shop, String> name;
    public static volatile SingularAttribute<Shop, Tradition> tradition;
    public static volatile SingularAttribute<Shop, Double> longitude;
    public static volatile SingularAttribute<Shop, List> beneficiaries;
    public static volatile SingularAttribute<Shop, Double> latitude;
    public static volatile ListAttribute<Shop, User> owners;
    public static volatile ListAttribute<Shop, Product> products;
    public static volatile ListAttribute<Shop, Comment> comments;
    public static volatile SingularAttribute<Shop, OrganizationType> organizationType;

}