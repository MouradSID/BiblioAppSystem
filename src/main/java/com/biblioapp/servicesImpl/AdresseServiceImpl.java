package com.biblioapp.servicesImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioapp.Dao.AdresseDao;
import com.biblioapp.model.Adresse;
import com.biblioapp.services.AdresseService;



@Service("adresseService")
@Transactional
public class AdresseServiceImpl implements AdresseService {

	@Autowired
	private AdresseDao adresseDao;

	public List<Adresse> findAll() {
		List<Adresse> adresses = new ArrayList<Adresse>();
		adresseDao.findAll().forEach(adresses::add);
		return adresses;
	}

	public Adresse findById(long id) {
		return adresseDao.findById(id).get();
	}

	public Adresse save(Adresse adresse) {
		return adresseDao.save(adresse);
	}

	public Adresse update(Adresse adresse) {
		return adresseDao.save(adresse);
	}

	public void deleteById(long id) {
		adresseDao.deleteById(id);
	}

	public boolean isExist(Adresse adresse) {
		return adresse.getId() > 0 && findById(adresse.getId()) != null;

	}

	public void deleteAll() {
		
	}

	public AdresseDao getAdresseDao() {
		return adresseDao;
	}

	public void setAdresseDao(AdresseDao adresseDao) {
		this.adresseDao = adresseDao;
	}
}

