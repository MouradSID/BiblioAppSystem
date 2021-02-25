package com.biblioapp.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioapp.Dao.EmprunteurDao;
import com.biblioapp.model.Emprunteur;
import com.biblioapp.services.EmprunteurService;



@Service
public class EmprunteurServiceImpl implements EmprunteurService  {

	@Autowired
	private EmprunteurDao emprunteurDao;

	public List<Emprunteur> findAll() {
		List<Emprunteur> emprunteurs = new ArrayList<Emprunteur>();
		emprunteurDao.findAll().forEach(emprunteurs::add);
		return emprunteurs;
	}

	public Emprunteur findById(long id) {
		return emprunteurDao.findById(id).get();
	}

	public Emprunteur save(Emprunteur emprunteur) {
		return emprunteurDao.save(emprunteur);
	}

	public Emprunteur update(Emprunteur emprunteur) {
		return emprunteurDao.save(emprunteur);
	}

	public void deleteById(long id) {
		emprunteurDao.deleteById(id);
	}

	public boolean isExist(Emprunteur emprunteur) {
		return emprunteur.getId() > 0 && findById(emprunteur.getId()) != null;
	}

	public void deleteAll() {
		
	}

	public EmprunteurDao getEmprunteurDao() {
		return emprunteurDao;
	}

	public void setEmprunteurDao(EmprunteurDao emprunteurDao) {
		this.emprunteurDao = emprunteurDao;
	}

}
