package com.sndhand.web.core;

import com.sndhand.web.core.utils.AbstractEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * 
 *  A shop, with descriptions,
 *  owners, products, and comments
 * 
 */
@Entity
public class Shop extends AbstractEntity implements Serializable {
    
    private String name;
    private String description;
    private String location;
    private double latitude;
    private double longitude;
    private double avgrating;
    private Tradition         tradition;
    private OrganizationType  organizationType;
    private List<Beneficiary> beneficiaries;
    
    @ManyToMany
    private List<User> owners;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
    
    public enum OrganizationType {NonProfit, PrivateCompany, None};
    
    public enum Beneficiary {Missonary, SheltersHousing, DrugAlcoholAbuse, SoupKitchens, NeedingPoorHelp, None};
    
    public enum Tradition {Christian, Secular, None};
    
    protected Shop() {}
    
    protected Shop(Builder builder) {
        this.name             = builder.name;
        this.latitude         = builder.latitude;
        this.longitude        = builder.longitude;
        this.description      = builder.description;
        this.location         = builder.location;
        this.avgrating        = builder.avgrating;
        this.organizationType = builder.organizationType;
        this.tradition        = builder.tradition;
        this.beneficiaries    = builder.beneficiaries;
        this.owners           = builder.owners;
        this.products         = builder.products;
        this.comments         = builder.comments;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }

    public List<User> getOwners() {
        return owners;
    }
    
    public List<Product> getProducts() {
        return products;
    }
        
    public List<Comment> getComments() {
        return comments;
    }
    
    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public Tradition getTradition() {
        return tradition;
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }
    
    public double getAvgrating() {
        return avgrating;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   
    /**************************************************************************/
    /*                             Util. Functions                            */
    /**************************************************************************/
    
    public void addOwner(User usr) {
        this.owners.add(usr);
    }
    
    public void addComment(Comment comment) {
        // Update accumulative average rating.
        this.avgrating = (comments.size()*this.avgrating+comment.getRating()) / (comments.size()+1);
        // Add comment to list of comments.
        this.comments.add(comment);
    }
    
    public Product findProduct(Integer id) {
        for (Product p : getProducts()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        
        return null;
    }
    
    public void removeProduct(Integer id) {
        products.remove(findProduct(id));
    }
    
    public List<Product> getProductRange(int first, int max) {
        if (products.isEmpty()) {
            return new ArrayList<Product>();
        }
        
        int last = first + max;
        if (last > products.size()) {
            last = products.size();
        }
        return products.subList(first, last);
    }
    
    public String getCoordinates() {
        return "(" + String.valueOf(latitude) + ", " + String.valueOf(longitude) + ")";
    }
    
    /**************************************************************************/
    /*                             Builder Pattern                            */
    /**************************************************************************/
    
    public static class Builder extends AbstractEntity.Builder<Builder, Shop> {
        private String name;
        private String description = "A Store!";
        private String location    = "Somewhere";
        private double latitude;
        private double longitude;
        private double avgrating = 0; // Default 0 rating (changes with user comments).
        
        private Tradition         tradition        = Tradition.None;
        private OrganizationType  organizationType = OrganizationType.None;
        private List<Beneficiary> beneficiaries    = new ArrayList<Beneficiary>();
        
        private List<User>    owners   = new ArrayList<User>();
        private List<Product> products = new ArrayList<Product>();
        private List<Comment> comments = new ArrayList<Comment>();
        
        public Builder(String name, double latitude, double longitude) {
            this.name     = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }
        
        public Builder description(String description) {
            this.description = description;  return this;
        }
        
        public Builder location(String location) {
            this.location = location; return this;
        }
        
        public Builder owners(List<User> owners) {
            this.owners = owners; return this;
        }
        
        public Builder products(List<Product> products) {
            this.products = products; return this;
        }
                
        public Builder comments(List<Comment> comments) {
            this.comments = comments; return this;
        }
                
        public Builder avgrating(double avgRating) {
            this.avgrating = avgRating; return this;
        }
        
        public Builder tradition(Tradition tradition) {
            this.tradition = tradition; return this;
        }
        
        public Builder organizationType(OrganizationType organizationType) {
            this.organizationType = organizationType; return this;
        }
        
        public Builder beneficiaries(List<Beneficiary> beneficiaries) {
            this.beneficiaries = beneficiaries; return this;
        }

        @Override
        public Shop build() {
            return new Shop(this);
        }
    }
}
