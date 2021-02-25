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

import com.biblioapp.model.Emprunteur;
import com.biblioapp.services.EmprunteurService;


@RestController
public class EmprunteurController {

	@Autowired
	EmprunteurService emprunteurService;  //Service which will do all data retrieval/manipulation work

	//-------------------Retrieve All Emprunteurs--------------------------------------------------------
	@RequestMapping(
			value = "/emprunteurs", 
			method = RequestMethod.GET
			)
	public ResponseEntity<List<Emprunteur>> listAllEmprunteurs() {
		List<Emprunteur> emprunteurs = emprunteurService.findAll();
		return new ResponseEntity<List<Emprunteur>>(emprunteurs, HttpStatus.OK);
	}


	//-------------------Retrieve Single Emprunteur--------------------------------------------------------

	@RequestMapping(
			value = "/emprunteurs/{id}", 
			method = RequestMethod.GET, 
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody ResponseEntity<Emprunteur> getEmprunteur(@PathVariable("id") long id) {
		System.out.println("Fetching Emprunteur with id " + id);
		Emprunteur emprunteur = emprunteurService.findById(id);
		if (emprunteur == null) {
			System.out.println("Emprunteur with id " + id + " not found");
			return new ResponseEntity<Emprunteur>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Emprunteur>(emprunteur, HttpStatus.OK);
	}


	//-------------------Create a Emprunteur--------------------------------------------------------

	@RequestMapping(
			value = "/emprunteurs", 
			method = RequestMethod.POST
			)
	public ResponseEntity<Void> createEmprunteur(@RequestBody Emprunteur emprunteur, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Emprunteur " + emprunteur.getUtilisateur().getNom());

		if (emprunteurService.isExist(emprunteur)) {
			System.out.println("A Emprunteur with name " + emprunteur.getUtilisateur().getNom() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		emprunteurService.save(emprunteur);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/emprunteurs/{id}").buildAndExpand(emprunteur.getId()).toUri());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}


	//------------------- Update a Emprunteur --------------------------------------------------------

	@RequestMapping(
			value = "/emprunteurs/{id}", 
			method = RequestMethod.PUT
			)
	public ResponseEntity<Emprunteur> updateEmprunteur(@PathVariable("id") long id, @RequestBody Emprunteur emprunteur) {

		System.out.println("Updating Emprunteur " + id);

		Emprunteur currentEmprunteur = emprunteurService.findById(id);

		if (currentEmprunteur == null) {
			System.out.println("Emprunteur with id " + id + " not found");
			return new ResponseEntity<Emprunteur>(HttpStatus.NOT_FOUND);
		}
		
		
		emprunteur.setId(id);
		emprunteurService.update(emprunteur);
		return new ResponseEntity<Emprunteur>(emprunteur, HttpStatus.OK);
	}

	//------------------- Delete a Emprunteur --------------------------------------------------------

	@RequestMapping(
			value = "/emprunteurs/{id}", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Emprunteur> deleteEmprunteur(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Emprunteur with id " + id);

		Emprunteur emprunteur = emprunteurService.findById(id);
		if (emprunteur == null) {
			System.out.println("Unable to delete. Emprunteur with id " + id + " not found");
			return new ResponseEntity<Emprunteur>(HttpStatus.NOT_FOUND);
		}

		emprunteurService.deleteById(id);
		return new ResponseEntity<Emprunteur>(HttpStatus.NO_CONTENT);
	}


	//------------------- Delete All Emprunteurs --------------------------------------------------------
	@RequestMapping(
			value = "/emprunteurs/", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Void> deleteAllEmprunteurs() {
		System.out.println("Deleting All Emprunteurs");

		emprunteurService.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
