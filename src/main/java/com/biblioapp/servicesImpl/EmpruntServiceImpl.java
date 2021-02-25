package com.biblioapp.servicesImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioapp.Dao.EmpruntDao;
import com.biblioapp.model.Emprunt;
import com.biblioapp.services.EmpruntService;



@Service("empruntService")
@Transactional
public class EmpruntServiceImpl implements EmpruntService {

	@Autowired
	private EmpruntDao empruntDao;

	public List<Emprunt> findAll() {
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		empruntDao.findAll().forEach(emprunts::add);
		return emprunts;
	}

	public Emprunt findById(long id) {
		return empruntDao.findById(id).get();
	}

	public Emprunt save(Emprunt emprunt) {
		return empruntDao.save(emprunt);
	}

	public Emprunt update(Emprunt emprunt) {
		return empruntDao.save(emprunt);
	}

	public void deleteById(long id) {
		empruntDao.deleteById(id);
	}

	public boolean isExist(Emprunt emprunt) {
		return emprunt.getId() > 0 && findById(emprunt.getId()) != null;
	}

	public void deleteAll() {
		
	}

	public EmpruntDao getEmpruntDao() {
		return empruntDao;
	}

	public void setEmpruntDao(EmpruntDao empruntDao) {
		this.empruntDao = empruntDao;
	}
}

