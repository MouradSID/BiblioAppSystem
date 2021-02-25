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

import com.biblioapp.model.Emprunt;
import com.biblioapp.services.EmpruntService;


@RestController
public class EmpruntController {

	@Autowired
	EmpruntService empruntService;  //Service which will do all data retrieval/manipulation work

	//-------------------Retrieve All Emprunts--------------------------------------------------------
	@RequestMapping(
			value = "/emprunts", 
			method = RequestMethod.GET
			)
	public ResponseEntity<List<Emprunt>> listAllEmprunts() {
		List<Emprunt> emprunts = empruntService.findAll();
		return new ResponseEntity<List<Emprunt>>(emprunts, HttpStatus.OK);
	}


	//-------------------Retrieve Single Emprunt--------------------------------------------------------

	@RequestMapping(
			value = "/emprunts/{id}", 
			method = RequestMethod.GET, 
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody ResponseEntity<Emprunt> getEmprunt(@PathVariable("id") long id) {
		System.out.println("Fetching Emprunt with id " + id);
		Emprunt emprunt = empruntService.findById(id);
		if (emprunt == null) {
			System.out.println("Emprunt with id " + id + " not found");
			return new ResponseEntity<Emprunt>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Emprunt>(emprunt, HttpStatus.OK);
	}


	//-------------------Create a Emprunt--------------------------------------------------------

	@RequestMapping(
			value = "/emprunts", 
			method = RequestMethod.POST
			)
	public ResponseEntity<Void> createEmprunt(@RequestBody Emprunt emprunt, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Emprunt " + emprunt.getUtilisateur().getNom());

		if (empruntService.isExist(emprunt)) {
			System.out.println("A Emprunt with name " + emprunt.getUtilisateur().getNom() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		empruntService.save(emprunt);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/emprunts/{id}").buildAndExpand(emprunt.getId()).toUri());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}


	//------------------- Update a Emprunt --------------------------------------------------------

	@RequestMapping(
			value = "/emprunts/{id}", 
			method = RequestMethod.PUT
			)
	public ResponseEntity<Emprunt> updateEmprunt(@PathVariable("id") long id, @RequestBody Emprunt emprunt) {

		System.out.println("Updating Emprunt " + id);

		Emprunt currentEmprunt = empruntService.findById(id);

		if (currentEmprunt == null) {
			System.out.println("Emprunt with id " + id + " not found");
			return new ResponseEntity<Emprunt>(HttpStatus.NOT_FOUND);
		}
		
		
		emprunt.setId(id);
		empruntService.update(emprunt);
		return new ResponseEntity<Emprunt>(emprunt, HttpStatus.OK);
	}

	//------------------- Delete a Emprunt --------------------------------------------------------

	@RequestMapping(
			value = "/emprunts/{id}", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Emprunt> deleteEmprunt(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Emprunt with id " + id);

		Emprunt emprunt = empruntService.findById(id);
		if (emprunt == null) {
			System.out.println("Unable to delete. Emprunt with id " + id + " not found");
			return new ResponseEntity<Emprunt>(HttpStatus.NOT_FOUND);
		}

		empruntService.deleteById(id);
		return new ResponseEntity<Emprunt>(HttpStatus.NO_CONTENT);
	}


	//------------------- Delete All Emprunts --------------------------------------------------------
	@RequestMapping(
			value = "/emprunts/", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Void> deleteAllEmprunts() {
		System.out.println("Deleting All Emprunts");

		empruntService.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
