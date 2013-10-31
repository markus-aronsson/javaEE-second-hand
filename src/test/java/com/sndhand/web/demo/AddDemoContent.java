package com.sndhand.web.demo;

import com.sndhand.web.core.Comment;
import com.sndhand.web.core.Product;
import com.sndhand.web.core.Shop;
import com.sndhand.web.core.dao.catalogues.ShopCatalogue;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * Setting up the database for a demonstration
 * 
 */
public class AddDemoContent {
    
    private static final String PU = "project_pu";
    private static ShopCatalogue sc;
    
    public AddDemoContent() {}
    
    @BeforeClass
    public static void setUpClass() {
        sc = new ShopCatalogue(PU);
    }
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}

    @Test
    public void testAddDemoContent() {
        System.out.println("Adding DEMO content to real PU");
        
        // I am at Chalmers
        int    MAX_ELEMENTS = 2;
        double myLatitude   = 57.6874;
        double myLongitude  = 11.9798;
        
        //Add some products
         Product p1 = new Product.Builder("Shoe", 5.0)
                .category(Product.Categories.Clothing)
                .build();
        Product p2 = new Product.Builder("Sofa" , 9.0)
                .category(Product.Categories.Furniture)
                .build();
        Product p3 = new Product.Builder("Troll" , 2.0)
                .category(Product.Categories.KnickKnack)
                .build();
        
        List<Product> prod1 = new ArrayList<Product>();
        prod1.add(p1);
        prod1.add(p2);
        prod1.add(p3);
    
        //Add the shops. All shops have the same products.
        Shop s1 = new Shop.Builder("Chalmers Loppis", 57.6873, 11.9799)
                .description("HAVE PRODUCTS This is a yearly loppis at Chalmers. Trollolol. " +
                             "This shop should be at top if sorting on location when at chalmers.")
                .tradition(Shop.Tradition.Secular)
                .products(prod1)
                .build();
        
        byte by = 5;
        byte bx = 2;
        
        s1.addComment(new Comment.Builder("Greg", "Really enjoyed this last year!").rating(by).build());
        s1.addComment(new Comment.Builder("Stephanie", "Didn't find anything useful!! :(").rating(bx).build());
        
        Shop s2 = new Shop.Builder("FarAway", 60.0, 15.0)
                .description("This is a shop FarFarAway from Chalmers with a high rating.")
                .avgrating(4.5)
                .build();
        
        Shop s3 = new Shop.Builder("Ebbes Bellevue", 57.730423, 12.022409)
                .description("Ebbes Hörna på Korpralsgatan 1 är en stor fullsortimentsbutik. Öppettider: vardagar kl 10-18, lördag-söndag kl 10-15")
                .tradition(Shop.Tradition.Christian)
                .organizationType(Shop.OrganizationType.NonProfit)
                .build();
        
        
        //Add some beneficiaries.
        List<Shop.Beneficiary> b1 = new ArrayList<Shop.Beneficiary>();
        b1.add(Shop.Beneficiary.DrugAlcoholAbuse);
        b1.add(Shop.Beneficiary.NeedingPoorHelp);
        b1.add(Shop.Beneficiary.SoupKitchens);
 
        Shop s4 = new Shop.Builder("Plastac Retro", 57.699035, 11.947882)
                .description("Plastic Retro, the place for fans of kitschy, campy, retro entertainment like plastic pop/dance/synth/disco-music, glamorous primetime soap operas, classic movie stars, trashy slasher flicks, sexy guys and old video games. Kitsch is not a bad word!")
                .organizationType(Shop.OrganizationType.PrivateCompany)
                .tradition(Shop.Tradition.Secular)
                .beneficiaries(b1)
                .build();
        
        sc.add(s1);
        sc.add(s2);
        sc.add(s3);
        sc.add(s4);
    }
}
