package com.biblioapp.services;

import java.util.List;


public interface GenericService<T> {
	
	T findById(long id);
     
    T save(T object);
     
    T update(T object);
     
    void deleteById(long id);
 
    List<T> findAll(); 
     
    void deleteAll();
     
    public boolean isExist(T user);
}
