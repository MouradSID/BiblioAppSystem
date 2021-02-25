package com.biblioapp.servicesImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioapp.Dao.BibliothecaireDao;
import com.biblioapp.model.Bibliothecaire;
import com.biblioapp.services.BibliothecaireService;



@Service("bibliothecaireService")
@Transactional
public class BibliothecaireServiceImpl implements BibliothecaireService {

	@Autowired
	private BibliothecaireDao bibliothecaireDao;

	public List<Bibliothecaire> findAll() {
		List<Bibliothecaire> bibliothecaires = new ArrayList<Bibliothecaire>();
		bibliothecaireDao.findAll().forEach(bibliothecaires::add);
		return bibliothecaires;
	}

	public Bibliothecaire findById(long id) {
		return bibliothecaireDao.findById(id).get();
	}

	public Bibliothecaire save(Bibliothecaire bibliothecaire) {
		return bibliothecaireDao.save(bibliothecaire);
	}

	public Bibliothecaire update(Bibliothecaire bibliothecaire) {
		return bibliothecaireDao.save(bibliothecaire);
	}

	public void deleteById(long id) {
		bibliothecaireDao.deleteById(id);
	}

	public boolean isExist(Bibliothecaire bibliothecaire) {
		return bibliothecaire.getId() > 0 && findById(bibliothecaire.getId()) != null;

	}

	public void deleteAll() {
		
	}

	public BibliothecaireDao getBibliothecaireDao() {
		return bibliothecaireDao;
	}

	public void setBibliothecaireDao(BibliothecaireDao bibliothecaireDao) {
		this.bibliothecaireDao = bibliothecaireDao;
	}
}

