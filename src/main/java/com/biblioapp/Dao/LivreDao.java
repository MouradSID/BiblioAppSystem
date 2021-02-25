package com.biblioapp.Dao;

import org.springframework.data.repository.CrudRepository;

import com.biblioapp.model.Livre;

public interface LivreDao extends CrudRepository <Livre, Long> {

}
