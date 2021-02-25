package com.biblioapp.Dao;

import org.springframework.data.repository.CrudRepository;

import com.biblioapp.model.Emprunteur;

public interface EmprunteurDao extends CrudRepository <Emprunteur, Long> {

}
