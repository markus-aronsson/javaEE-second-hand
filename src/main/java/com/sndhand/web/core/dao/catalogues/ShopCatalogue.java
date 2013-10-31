package com.sndhand.web.core.dao.catalogues;

import com.sndhand.web.componentBased.bb.ShopsBB;
import com.sndhand.web.core.Shop;
import com.sndhand.web.core.dao.AbstractDAO;
import java.util.List;

/**
 * 
 * A catalogue for the shops
 * 
 */
public class ShopCatalogue extends AbstractDAO<Shop> {
    
    public ShopCatalogue(String puName) {
        super(Shop.class, puName);
    }
    
    /**
     * Return the given range of shops, 
     * also applying a certain filter/sorting
     */
    public List<Shop> getSortedFilteredRange(int maxElements, double minRating, 
                                             double maxDistance, double myLatitude,
                                             double myLongitude,
                                             ShopsBB.SortingOptions sortingOption) {
        String eucDist = "SQRT((s.latitude-:myLat)*(s.latitude-:myLat)+(s.longitude-:myLon)*(s.longitude-:myLon))";
        String orderByString = "ORDER BY s.avgrating DESC";
        switch (sortingOption) {
            case Rating:
                orderByString = "ORDER BY s.avgrating DESC";
                break;
            case Location:
                orderByString = "ORDER BY " + eucDist + "ASC";
                break;
            default:
                break;
        }
        
        return emf.createEntityManager()
                .createQuery("SELECT s " +
                             "FROM Shop s " +
                             "WHERE s.avgrating >= :minRating " +
                             "  AND " + eucDist + " <= :maxDistance " +
                             orderByString, Shop.class)
                .setParameter("minRating", minRating)
                .setParameter("maxDistance", maxDistance)
                .setParameter("myLat", myLatitude)
                .setParameter("myLon", myLongitude)
                .setMaxResults(maxElements)
                .getResultList();
    }
}
