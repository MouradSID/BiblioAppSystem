package com.biblioapp.Dao;

import org.springframework.data.repository.CrudRepository;

import com.biblioapp.model.Emprunt;


public interface EmpruntDao extends CrudRepository <Emprunt, Long> {


}
