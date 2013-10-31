package com.sndhand.web.core.dao.catalogues;

import com.sndhand.web.core.Group;
import com.sndhand.web.core.dao.AbstractDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * A catalogue for the groups that a 
 * user can be in
 * 
 */
public class GroupCatalogue extends AbstractDAO<Group> {
    
    public GroupCatalogue(String puName) {
        super(Group.class, puName);
    }
    
    /**
     *  Return the group given a certain user - 
     *  works since all the usernames are specified
     *  to be unique
     */
    public List<Group> getGroupByUser(String usr) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT c FROM Group c WHERE c.username = :name").setParameter("name", usr);
        List<Group> l = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return l;
    }
}


