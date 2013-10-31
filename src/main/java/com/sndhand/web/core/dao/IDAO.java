package com.sndhand.web.core.dao;

import java.util.List;
import javax.ejb.Local;

/**
 * 
 * A generic DAO interface
 * 
 */
@Local
public interface IDAO<K, T> {

    public void add(K p);

    public void remove(T id);

    public void update(K t);

    public K find(T id);
    
    public List<K> getAll();
    
    public List<K> getRange(int first, int nItems);
    
    public int getCount();
}
    