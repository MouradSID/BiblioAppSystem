package com.biblioapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.biblioapp.model.Livre;
import com.biblioapp.services.LivreService;


@RestController
public class LivreController {

	@Autowired
	LivreService livreService;  //Service which will do all data retrieval/manipulation work

	//-------------------Retrieve All Livres--------------------------------------------------------
	@RequestMapping(
			value = "/livres", 
			method = RequestMethod.GET
			)
	public ResponseEntity<List<Livre>> listAllLivres() {
		List<Livre> livres = livreService.findAll();
		return new ResponseEntity<List<Livre>>(livres, HttpStatus.OK);
	}


	//-------------------Retrieve Single Livre--------------------------------------------------------

	@RequestMapping(
			value = "/livres/{id}", 
			method = RequestMethod.GET, 
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody ResponseEntity<Livre> getLivre(@PathVariable("id") long id) {
		System.out.println("Fetching Livre with id " + id);
		Livre livre = livreService.findById(id);
		if (livre == null) {
			System.out.println("Livre with id " + id + " not found");
			return new ResponseEntity<Livre>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Livre>(livre, HttpStatus.OK);
	}


	//-------------------Create a Livre--------------------------------------------------------

	@RequestMapping(
			value = "/livres", 
			method = RequestMethod.POST
			)
	public ResponseEntity<Void> createLivre(@RequestBody Livre livre, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Livre " + livre.getTitre());

		if (livreService.isExist(livre)) {
			System.out.println("A Livre with name " + livre.getTitre() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		livreService.save(livre);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/livres/{id}").buildAndExpand(livre.getId()).toUri());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}


	//------------------- Update a Livre --------------------------------------------------------

	@RequestMapping(
			value = "/livres/{id}", 
			method = RequestMethod.PUT
			)
	public ResponseEntity<Livre> updateLivre(@PathVariable("id") long id, @RequestBody Livre livre) {

		System.out.println("Updating Livre " + id);

		Livre currentLivre = livreService.findById(id);

		if (currentLivre == null) {
			System.out.println("Livre with id " + id + " not found");
			return new ResponseEntity<Livre>(HttpStatus.NOT_FOUND);
		}
		
		livre.setId(id);
		livreService.update(livre);
		return new ResponseEntity<Livre>(livre, HttpStatus.OK);
	}

	//------------------- Delete a Livre --------------------------------------------------------

	@RequestMapping(
			value = "/livres/{id}", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Livre> deleteLivre(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Livre with id " + id);

		Livre livre = livreService.findById(id);
		if (livre == null) {
			System.out.println("Unable to delete. Livre with id " + id + " not found");
			return new ResponseEntity<Livre>(HttpStatus.NOT_FOUND);
		}

		livreService.deleteById(id);
		return new ResponseEntity<Livre>(HttpStatus.NO_CONTENT);
	}


	//------------------- Delete All Livres --------------------------------------------------------
	@RequestMapping(
			value = "/livres/", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Void> deleteAllLivres() {
		System.out.println("Deleting All Livres");

		livreService.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
