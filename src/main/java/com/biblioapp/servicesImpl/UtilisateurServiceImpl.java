package com.biblioapp.servicesImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioapp.Dao.UtilisateurDao;
import com.biblioapp.model.Utilisateur;
import com.biblioapp.services.UtilisateurService;



@Service("utilisateurService")
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurDao utilisateurDao;

	public List<Utilisateur> findAll() {
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		utilisateurDao.findAll().forEach(utilisateurs::add);
		return utilisateurs;
	}

	public Utilisateur findById(long id) {
		return utilisateurDao.findById(id).get();
	}

	public Utilisateur save(Utilisateur utilisateur) {
		return utilisateurDao.save(utilisateur);
	}

	public Utilisateur update(Utilisateur utilisateur) {
		return utilisateurDao.save(utilisateur);
	}

	public void deleteById(long id) {
		utilisateurDao.deleteById(id);
	}

	public boolean isExist(Utilisateur utilisateur) {
		return utilisateur.getId() > 0 && findById(utilisateur.getId()) != null;
	}

	public void deleteAll() {
		
	}

	public UtilisateurDao getUtilisateurDao() {
		return utilisateurDao;
	}

	public void setUtilisateurDao(UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}
}

