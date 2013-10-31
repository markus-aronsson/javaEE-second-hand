
package com.sndhand.web.serviceBased;

import com.sndhand.web.componentBased.ContainerBean;
import com.sndhand.web.componentBased.bb.ShopsBB;
import com.sndhand.web.core.Shop;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 *  A shop - restful resource
 * 
 */
@Path("/shops")
@RequestScoped
public class ShopResource {
    
    @Inject
    private ContainerBean c;
    
    @Context
    private UriInfo uriInfo;
    
    /**
     * Return all the shops within the specified area
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Response getShops(@QueryParam("lat") Float lat, @QueryParam("long") Float lng) {
        GenericEntity<List<ShopProxy>> ge = 
                new GenericEntity<List<ShopProxy>>(new ArrayList<ShopProxy>()) {};
    
        for (Shop s : c.getContainer().getShops().getSortedFilteredRange(5, 0, 100, lat, lng, ShopsBB.SortingOptions.Rating)) {
            ge.getEntity().add(new ShopProxy(s));
        }
        
        return Response.ok(ge).build();
    }
    
    /**
     * Return the shop with the given id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Integer id) {
        ShopProxy p = new ShopProxy((Shop) c.getContainer().getShops().find(id));
        if (p != null) {
            return Response.ok(p).build();
        } else {
           return Response.noContent().build();
        }
    }
}


