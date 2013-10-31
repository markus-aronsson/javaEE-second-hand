package com.sndhand.web.db;

import com.sndhand.web.componentBased.bb.ShopsBB;
import com.sndhand.web.core.Comment;
import com.sndhand.web.core.Shop;
import com.sndhand.web.core.dao.AbstractDAO;
import com.sndhand.web.core.dao.catalogues.ShopCatalogue;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * 
 *  JUnit tests for the shop catalogue
 * 
 */
public class ShopCatalogueTest {
    
    private static final String TEST_PU = "test_project_pu";
    private static ShopCatalogue sc;
    private static CommentCatalogue cc;
    
    public ShopCatalogueTest() {
    }
    
    private static class CommentCatalogue extends AbstractDAO<Comment> {
        public CommentCatalogue(String puname) {
            super(Comment.class, puname);
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
        sc = new ShopCatalogue(TEST_PU);
        cc = new CommentCatalogue(TEST_PU);
    }
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}

    @Ignore
    @Test
    public void testAddCountRemove() {
        System.out.println("Testing add");

        Shop s1 = new Shop.Builder("s1", 3,4).build();
        Shop s2 = new Shop.Builder("s2", 5,6).build();
        
        sc.add(s1);
        
        assertTrue(sc.getCount() == 1);
        
        sc.add(s2);
        
        assertTrue(sc.getCount() == 2);
      
        sc.remove(s1.getId());
        sc.remove(s2.getId());
        
        assertTrue(sc.getCount() == 0);
    }
    
    @Ignore
    @Test
    public void testGetRange(){
        System.out.println("Testing getRange");
        
        Shop s1 = new Shop.Builder("s3", 3,4).build();
        Shop s2 = new Shop.Builder("s4", 5,6).build();
        Shop s3 = new Shop.Builder("s5", 7,8).build();
        Shop s4 = new Shop.Builder("s6", 9,0).build();
        
        sc.add(s1);
        sc.add(s2);
        sc.add(s3);
        sc.add(s4);
        
        List<Shop> shops = sc.getRange(2, 2);
        assertTrue(shops.size() == 2);
        assertTrue(shops.get(0).getName().equals(s3.getName()));
        assertTrue(shops.get(1).getName().equals(s4.getName()));
        
        sc.remove(s1.getId());
        sc.remove(s2.getId());
        sc.remove(s3.getId());
        sc.remove(s4.getId());
    }
    
   
    @Test
    public void testFindAndUpdate() {
        System.out.println("Testing find and update");
        
        Shop s1 = new Shop.Builder("Shop1", 3,4).build();
        
        sc.add(s1);
        
        Shop s = sc.find(s1.getId());
        assertTrue(s.getName().equals(s1.getName()));
        
        s.setName("NewShop1");
        sc.update(s);
        
        assertTrue(sc.find(s1.getId()).getName().equals("NewShop1"));
        
        sc.remove(s.getId());
    }
    
    @Test
    public void testGetAll() {
        System.out.println("Testing getAll");
        
        Shop s1 = new Shop.Builder("Shop1", 3,4).build();
        Shop s2 = new Shop.Builder("Shop2", 7,5).build();
        
        sc.add(s1);
        sc.add(s2);

        assertTrue(sc.getAll().size() == 2);
        
        sc.remove(s1.getId());
        sc.remove(s2.getId());
    }
    
    @Test
    public void testGetSortedFilteredRange() {
        System.out.println("Testing getSortedFilteredRange");
        
        // I am at Chalmers
        int MAX_ELEMENTS = 2;
        double myLatitude = 57.6874;
        double myLongitude = 11.9798;
        
        Shop s1 = new Shop.Builder("ChalmersLoppis", 57.6874, 11.9798).avgrating(1.0).build();
        Shop s2 = new Shop.Builder("FarAway", 60.0, 15.0).avgrating(4.5).build();
                
        sc.add(s1);
        sc.add(s2);
        
        // Fetch sorted by location
        List<Shop> ss = sc.getSortedFilteredRange(MAX_ELEMENTS, 0.0, 
                100.0, myLatitude, myLongitude, ShopsBB.SortingOptions.Location);
        
        // Nothing should be filtered yet
        assertEquals(ss.size(), 2);
        
        // Test sort by location. First object ChalmersLoppis
        assertEquals(ss.get(0).getName(), "ChalmersLoppis");
        
        // Fetch sorted by rating (nothing should be filtered still)
        List<Shop> ss2 = sc.getSortedFilteredRange(MAX_ELEMENTS, 0.0, 
                100.0, myLatitude, myLongitude, ShopsBB.SortingOptions.Rating);
        assertEquals(ss2.size(), 2);
        
        // Test that FarAway with greatest rating is fetched first.
        assertEquals(ss2.get(0).getName(), "FarAway");
        
        // Filter by distance
        List<Shop> ss3 = sc.getSortedFilteredRange(MAX_ELEMENTS, 0.0, 0.01, 
                myLatitude, myLongitude, ShopsBB.SortingOptions.Rating);
        
        // Only ChalmersLoppis visible
        assertEquals(ss3.size(), 1);
        assertEquals(ss3.get(0).getName(), "ChalmersLoppis");
        
        // Filter by range
        List<Shop> ss4 = sc.getSortedFilteredRange(MAX_ELEMENTS, 4.0, 100.0, 
                myLatitude, myLongitude, ShopsBB.SortingOptions.Rating);
        
        // Only FarAway visible
        assertEquals(ss4.size(), 1);
        assertEquals(ss4.get(0).getName(), "FarAway");
        
        sc.remove(s1.getId());
        sc.remove(s2.getId());
    }
    
    @Test
    public void testAddComment() {
        Shop s = new Shop.Builder("Test", 10, 10).build();
        assertEquals(s.getAvgrating(), 0.0, 0.0001);
        Byte b = 3;
        Comment c = new Comment.Builder("Author", "Text").rating(b).build();
        s.addComment(c);
        assertEquals(s.getAvgrating(), 3.0, 0.001);
    }
    
    @Test
    public void testCascadingComments() {
        // Build and add shop to ShopCatalogue (persist)
        Shop s1 = new Shop.Builder("Shop1", 3,4).build();
        sc.add(s1);
        
        // Add comment to shop-instance
        Comment c1 = new Comment.Builder("MonkeyWithTypeWriter", "123").rating((byte)1).build();
        s1.addComment(c1);
        
        // Persist shop again with update (persist shop with comment).
        sc.update(s1);
        
        // Add a second comment as above
        Comment c2 = new Comment.Builder("ChimpWithTypeWriter", "kjsd73").rating((byte)5).build();
        s1.addComment(c2);
        sc.update(s1);
        
        // Fetch from persisted a shop instance
        Shop s11 = sc.find(s1.getId());
        
        // Should be two different object instances
        assertTrue((Object)s11 != (Object)s1);
        
        // Check that fetched shop has the two comments
        assertEquals(s11.getComments().size(), 2);
        assertEquals(s11.getAvgrating(), 3.0, 0.00001);
        
        // Removal of the shop should remove the comments
        sc.remove(s1.getId());
        
        // Can't fetch a removed shop
        assertNull("Shop with id " + s1.getId() + "still exists", sc.find(s1.getId()));
        
        // Can't fetch cascade-deleted comments
        assertNull("Comment with id " + s11.getComments().get(0).getId() + " still exist",
                cc.find(s11.getComments().get(0).getId()));
        assertNull("Comment with id " + s11.getComments().get(1).getId() + " still exist",
                cc.find(s11.getComments().get(1).getId()));
    }
    
    @Test
    public void testAddWeirdCharsComment() {        
        // Add strange comment
        Shop s1 = new Shop.Builder("Shop1", 3,4).build();
        String msg = "&()€s s  kdsöööäö. Very nice!";
        Comment c1 = new Comment.Builder("Öyvind Torrés", msg).rating((byte)5).build();
        s1.addComment(c1);
        
        // Persist shop+comment at once
        sc.add(s1);

        // Fetch shop and get comment
        Shop s11 = sc.find(s1.getId());
        Comment c11 = s11.getComments().get(0);
        
        // See that message is stored and fetched correctly
        assertEquals("Stored msg not equal. But '" + c11.getText() + "'", c11.getText(), msg);
        
        // Clean up
        sc.remove(s1.getId());
    }
}
