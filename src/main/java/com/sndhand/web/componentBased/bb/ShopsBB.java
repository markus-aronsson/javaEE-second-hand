package com.sndhand.web.componentBased.bb;

import com.sndhand.web.componentBased.ContainerBean;
import com.sndhand.web.core.Shop;
import com.sndhand.web.core.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Backing bean for the page
 * allowing users to manage shops
 * 
 */
@Named("shops")
@SessionScoped
public class ShopsBB implements Serializable {
    
    private final int MAX_PAGE_VIEWS = 5;

    private SortingOptions sortingOption;
    private double myLatitude;
    private double myLongitude;
    private int    distanceFilter;
    private int    ratingFilter;
    
    @Inject
    private ContainerBean container;
       
    public static enum SortingOptions { Rating, Location };
    
    @PostConstruct
    public void post() {
        double[] defaultGeolocation = tryGetRoughLocation();
        
        sortingOption  = SortingOptions.Rating;
        myLatitude     = defaultGeolocation[0];
        myLongitude    = defaultGeolocation[1];
        distanceFilter = 0;
        ratingFilter   = 0;
    }
    
    private double[] tryGetRoughLocation() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
        // Insert ip-to-geolocation lookup here. Now pointing at Chalmers.
        return new double[] {57.6874, 11.9798};
    }
    
    public List<Shop> getFilteredRange() {
        double maxDistance = (100/Math.pow(10, distanceFilter));
        return container.getContainer().getShops().getSortedFilteredRange(
                MAX_PAGE_VIEWS, ratingFilter, maxDistance, myLatitude, myLongitude, sortingOption);
    }

    public String update() {
        return "SHOP_UPDATED";
    }
    
    public String navigate(String target) {
        return target;
    }
    
    public void setDistanceFilter(int x) {
        // 0 and upwards (max 4 is reasonable)
        this.distanceFilter = x;
    }
    
    
    public int getDistanceFilter() {
        return distanceFilter;
    }
    
    
    public void setRatingFilter(int x) {
        // 0 <= x <= 5
        this.ratingFilter = x;
    }
    
    
    public int getRatingFilter() {
        return ratingFilter;
    }
    
    
    public SortingOptions getSortingOption() {
        return sortingOption;
    }
    
    
    public void setSortingOption(SortingOptions s) {
        this.sortingOption = s;
    }
    
    
    public SelectItem[] getSortingOptions() {
        SelectItem[] items = new SelectItem[SortingOptions.values().length];
        int i = 0;
        for(SortingOptions val: SortingOptions.values()) {
            items[i++] = new SelectItem(val, val.toString());
        }
        return items;
    }
    
    public List<Shop> getAll() {
       return container.getContainer().getShops().getAll();
    }
    
    public String shop(String sid, String name) {
        
        Shop s = container.getContainer().getShops().find(Integer.valueOf(sid));
        
        for (User u : container.getContainer().getUsers().getUserByName(name)) {
            if (s.getOwners().contains(u)) {
                return "shop";
            }
        }
        return "error";
    }
}
