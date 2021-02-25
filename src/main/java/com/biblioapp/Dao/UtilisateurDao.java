package com.biblioapp.Dao;



import org.springframework.data.repository.CrudRepository;

import com.biblioapp.model.Utilisateur;

public interface UtilisateurDao extends CrudRepository <Utilisateur, Long> {


}
