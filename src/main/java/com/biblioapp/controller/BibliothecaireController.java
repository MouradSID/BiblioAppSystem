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

import com.biblioapp.model.Bibliothecaire;
import com.biblioapp.services.BibliothecaireService;


@RestController
public class BibliothecaireController {
	
	@Autowired
	BibliothecaireService bibliothecaireService;  //Service which will do all data retrieval/manipulation work

	//-------------------Retrieve All Bibliothecaires--------------------------------------------------------
	@RequestMapping(
		value = "/bibliothecaires", 
		method = RequestMethod.GET
	)
	public ResponseEntity<List<Bibliothecaire>> listAllBibliothecaires() {
		List<Bibliothecaire> bibliothecaires = bibliothecaireService.findAll();
		return new ResponseEntity<List<Bibliothecaire>>(bibliothecaires, HttpStatus.OK);
	}


	//-------------------Retrieve Single Bibliothecaire--------------------------------------------------------

	@RequestMapping(
		value = "/bibliothecaires/{id}", 
		method = RequestMethod.GET, 
		consumes = MediaType.ALL_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public @ResponseBody ResponseEntity<Bibliothecaire> getBibliothecaire(@PathVariable("id") long id) {
		System.out.println("Fetching Bibliothecaire with id " + id);
		Bibliothecaire bibliothecaire = bibliothecaireService.findById(id);
		if (bibliothecaire == null) {
			System.out.println("Bibliothecaire with id " + id + " not found");
			return new ResponseEntity<Bibliothecaire>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Bibliothecaire>(bibliothecaire, HttpStatus.OK);
	}


	//-------------------Create a Bibliothecaire--------------------------------------------------------

	@RequestMapping(
		value = "/bibliothecaires", 
		method = RequestMethod.POST
	)
	public ResponseEntity<Void> createBibliothecaire(@RequestBody Bibliothecaire bibliothecaire, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Bibliothecaire " + bibliothecaire.getUtilisateur().getNom());
		
		if (bibliothecaireService.isExist(bibliothecaire)) {
			System.out.println("A Bibliothecaire with name " + bibliothecaire.getUtilisateur().getNom() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		bibliothecaireService.save(bibliothecaire);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/bibliothecaires/{id}").buildAndExpand(bibliothecaire.getId()).toUri());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}


	//------------------- Update a Bibliothecaire --------------------------------------------------------

	@RequestMapping(
		value = "/bibliothecaires/{id}", 
		method = RequestMethod.PUT
	)
	public ResponseEntity<Bibliothecaire> updateBibliothecaire(@PathVariable("id") long id, @RequestBody Bibliothecaire bibliothecaire) {

		System.out.println("Updating Bibliothecaire " + id);

		Bibliothecaire currentBibliothecaire = bibliothecaireService.findById(id);

		if (currentBibliothecaire == null) {
			System.out.println("Bibliothecaire with id " + id + " not found");
			return new ResponseEntity<Bibliothecaire>(HttpStatus.NOT_FOUND);
		}

		bibliothecaire.setId(id);
		bibliothecaireService.update(bibliothecaire);
		return new ResponseEntity<Bibliothecaire>(bibliothecaire, HttpStatus.OK);
	}

	//------------------- Delete a Bibliothecaire --------------------------------------------------------

	@RequestMapping(
		value = "/bibliothecaires/{id}", 
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Bibliothecaire> deleteBibliothecaire(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Bibliothecaire with id " + id);

		Bibliothecaire bibliothecaire = bibliothecaireService.findById(id);
		if (bibliothecaire == null) {
			System.out.println("Unable to delete. Bibliothecaire with id " + id + " not found");
			return new ResponseEntity<Bibliothecaire>(HttpStatus.NOT_FOUND);
		}

		bibliothecaireService.deleteById(id);
		return new ResponseEntity<Bibliothecaire>(HttpStatus.NO_CONTENT);
	}


	//------------------- Delete All Bibliothecaires --------------------------------------------------------
	@RequestMapping(
		value = "/bibliothecaires/", 
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Void> deleteAllBibliothecaires() {
		System.out.println("Deleting All Bibliothecaires");

		bibliothecaireService.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
