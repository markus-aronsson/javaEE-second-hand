package com.sndhand.web.serviceBased;

import com.sndhand.web.componentBased.ContainerBean;
import com.sndhand.web.core.Product;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 * The product catalogue - restful resource
 * 
 */
@Path("products/{shopId}")
@RequestScoped
public class ProductCatalogueResource {
    
    @Inject
    ContainerBean container;
    
    @Context
    private UriInfo uriInfo;
    
    /**
     * Return the product catalogue for the
     * shop with the given id
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Response getCatalogue(@PathParam("shopId") int shopId) {
        GenericEntity<List<ProductProxy>> ge = 
                new GenericEntity<List<ProductProxy>>(new ArrayList<ProductProxy>()) {};

        for (Product p : container.getContainer().getShops().find(shopId).getProducts()) {
            ge.getEntity().add(new ProductProxy(p));
        }
        
        return Response.ok().build();
    }
    
    /**
     * Return the product with the given id for the
     * shop with the given id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Integer id, @PathParam("shopId") Integer shopId) {        
        Product p = container.getContainer().getShops().find(shopId).findProduct(id);
        if (p != null) {
            return Response.ok(new ProductProxy(p)).build();
        } else {
            return Response.noContent().build();
        }
    }
    
    /**
     * Return the products between the specified
     * range for the given shop
     */
    @GET
    @Path("range")
    @Produces({MediaType.APPLICATION_XML})
    public Response getRange(
            @QueryParam("fst") Integer fst,
            @QueryParam("max") Integer max, 
            @PathParam("shopId") int shopId) {
        List<ProductProxy> ps = new ArrayList<ProductProxy>();
        
        for (Product p : container.getContainer().getShops().find(shopId).getProductRange(fst, max)) {
            ps.add(new ProductProxy(p));
        }
                
        GenericEntity<List<ProductProxy>> ge = new GenericEntity<List<ProductProxy>>(ps) {};
        return Response.ok(ge).build();
    }
    
    /**
     * Return the number of products for
     * the given shop
     */
    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_XML})
    public Response getCount(@PathParam("shopId") int shopId) {
        PrimitiveWrapper pw = new PrimitiveWrapper(container.getContainer().getShops().find(shopId).getProducts().size());
        return Response.ok(pw).build();
    }
}
