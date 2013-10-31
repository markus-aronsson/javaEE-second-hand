package com.sndhand.web.core.dao;

import com.sndhand.web.core.utils.AbstractEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * 
 * A data access object for the database
 * 
 * This class was partially constructed by the teacher 
 * of the course, during which this application was developed.
 * 
 */
public abstract class AbstractDAO<K extends AbstractEntity> implements IDAO<K, Integer> {
   
    /** The table to access **/
    private   Class<K>             c;
    protected EntityManagerFactory emf;

    public AbstractDAO() {  
    }

    protected AbstractDAO(Class<K> c, String puName) {
        this.c   = c;
        this.emf = Persistence.createEntityManagerFactory(puName);
    }

    /**
     * Add an object to the database
     */
    @Override
    public void add(K t) {
        EntityManager     em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    /**
     * Remove an object from the database
     */
    @Override
    public void remove(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        K t = em.getReference(c, id);
        em.remove(t);
        em.getTransaction().commit();
        em.close();
    }
    
    /**
     * Update an object that is in the database
     */
    @Override
    public void update(K t) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Find an object in the database with the given id
     */
    @Override
    public K find(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        K k = em.find(c, id);
        em.getTransaction().commit();
        em.close();
        return k;
    }

    /**
     * Get all objects for the table in the database
     * (This is only used by entities which table name is the
     * same as the class)
     */
    @Override
    public List<K> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT c FROM " + c.getSimpleName() + " c");
        List<K> l = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return l;
    }

    /**
     * Get a certain range of objects from the database
     * (This is only used by entities which table name is the
     * same as the class)
     */
    @Override
    public List<K> getRange(int first, int nItems) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT c FROM " + c.getSimpleName() + " c");
        List<K> l = query.setFirstResult(first).setMaxResults(nItems).getResultList();
        em.getTransaction().commit();
        em.close();
        return l;
    }
    
    /**
     * Get the number of objects in the table
     * (This is only used by entities which table name is the
     * same as the class)
     */
    @Override
    public int getCount() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String s = "SELECT count(x) FROM " + c.getSimpleName() + " x";
        Query q = em.createQuery (s);
        Number result = (Number) q.getSingleResult ();
        em.getTransaction().commit();
        em.close();
        return result.intValue();         
    }
}