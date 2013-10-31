package com.sndhand.web.db;

import com.sndhand.web.core.User;
import com.sndhand.web.core.dao.catalogues.UserCatalogue;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 *  JUnit tests for the user catalogue
 * 
 */
public class UserCatalogueTest {
    
    private static final String TEST_PU = "test_project_pu";
    private static UserCatalogue uc1;
    private static UserCatalogue uc2;
    
    public UserCatalogueTest() { }
    
    @BeforeClass
    public static void setUpClass() {
        uc1 = new UserCatalogue(TEST_PU);
        uc2 = new UserCatalogue(TEST_PU);
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
        System.out.println("Testing add, getCount, and remove");
        
        User u1 = new User.Builder("Owner1", "pass").build();
        
        uc1.add(u1);
        
        assertTrue(uc1.getCount() == 1);
        
        User u2 = new User.Builder("Owner2", "pass2").build();
      
        uc1.add(u2);
      
        assertTrue(uc1.getCount() == 2);
       
        uc1.remove(u1.getId());
        uc1.remove(u2.getId());
       
        assertTrue(uc1.getCount() == 0);
    }
    
    @Ignore
    @Test
    public void testGetRange(){
        System.out.println("Testing getRange");
        
        User u1 = new User.Builder("Owner1", "pass").build();
        User u2 = new User.Builder("Owner2", "pass2").build();
        User u3 = new User.Builder("Owner3", "pass3").build();
        User u4 = new User.Builder("Owner4", "pass4").build();
        
        uc1.add(u1);
        uc1.add(u2);
        uc1.add(u3);
        uc1.add(u4);
        
        List<User> owners = uc1.getRange(2, 2);
        assertTrue(owners.size() == 2);
        assertTrue(owners.get(0).getName().equals(u3.getName()));
        assertTrue(owners.get(1).getName().equals(u4.getName()));
        
        uc1.remove(u1.getId());
        uc1.remove(u2.getId());
        uc1.remove(u3.getId());
        uc1.remove(u4.getId());
    }
    
    @Ignore
    @Test
    public void testFindAndUpdate() {
        System.out.println("Testing find and update");
        
        User u = new User.Builder("Owner1", "pass").build();
        
        uc1.add(u);
        
        User o = uc1.find(u.getId());
        assertTrue(o.getName().equals(u.getName()));
        
        o.setName("NewOwner1");
        uc1.update(o);
        
        assertTrue(uc1.find(u.getId()).getName().equals("NewOwner1"));
        
        uc1.remove(u.getId());
    }
    
    @Ignore
    @Test
    public void testGetAll() {
        System.out.println("Testing getAll");
        
        User u1 = new User.Builder("Owner1", "pass").build();
        User u2 = new User.Builder("Owner2", "pass2").build();
        
        uc1.add(u1);
        uc1.add(u2);

        assertTrue(uc1.getAll().size() == 2);
        
        uc1.remove(u1.getId());
        uc1.remove(u2.getId());
    }
}
