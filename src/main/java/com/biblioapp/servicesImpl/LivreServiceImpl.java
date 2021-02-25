package com.biblioapp.servicesImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioapp.Dao.LivreDao;
import com.biblioapp.model.Livre;
import com.biblioapp.services.LivreService;



@Service("livreService")
@Transactional
public class LivreServiceImpl implements LivreService {

	@Autowired
	private LivreDao livreDao;

	public List<Livre> findAll() {
		List<Livre> livres = new ArrayList<Livre>();
		livreDao.findAll().forEach(livres::add);
		return livres;
	}

	public Livre findById(long id) {
		return livreDao.findById(id).get();
	}

	public Livre save(Livre livre) {
		return livreDao.save(livre);
	}

	public Livre update(Livre livre) {
		return livreDao.save(livre);
	}

	public void deleteById(long id) {
		livreDao.deleteById(id);
	}

	public boolean isExist(Livre livre) {
		return livre.getId() > 0 && findById(livre.getId()) != null;
	}

	public void deleteAll() {
		
	}

	public LivreDao getLivreDao() {
		return livreDao;
	}

	public void setLivreDao(LivreDao livreDao) {
		this.livreDao = livreDao;
	}
}

