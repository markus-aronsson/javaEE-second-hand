package com.sndhand.web.core.dao.catalogues;

import com.sndhand.web.core.User;
import com.sndhand.web.core.dao.AbstractDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * A catalogue for users
 * 
 */
public class UserCatalogue extends AbstractDAO<User> {
    
    public UserCatalogue(String puName) {
        super(User.class, puName);
    }
    
    /**
     * Return a user given a username - 
     * works since the usernames are specified to be
     * unique
     */
    public List<User> getUserByName(String usr) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT c FROM User c WHERE c.username = :name").setParameter("name", usr);
        List<User> l = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return l;
    }
}
