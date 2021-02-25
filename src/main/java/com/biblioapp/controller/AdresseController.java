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

import com.biblioapp.model.Adresse;
import com.biblioapp.services.AdresseService;


@RestController
public class AdresseController {

	@Autowired
	AdresseService adresseService;  //Service which will do all data retrieval/manipulation work

	//-------------------Retrieve All Adresses--------------------------------------------------------
	@RequestMapping(
			value = "/adresses", 
			method = RequestMethod.GET
			)
	public ResponseEntity<List<Adresse>> listAllAdresses() {
		List<Adresse> adresses = adresseService.findAll();
		return new ResponseEntity<List<Adresse>>(adresses, HttpStatus.OK);
	}


	//-------------------Retrieve Single Adresse--------------------------------------------------------

	@RequestMapping(
			value = "/adresses/{id}", 
			method = RequestMethod.GET, 
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody ResponseEntity<Adresse> getAdresse(@PathVariable("id") long id) {
		System.out.println("Fetching Adresse with id " + id);
		Adresse adresse = adresseService.findById(id);
		if (adresse == null) {
			System.out.println("Adresse with id " + id + " not found");
			return new ResponseEntity<Adresse>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Adresse>(adresse, HttpStatus.OK);
	}


	//-------------------Create a Adresse--------------------------------------------------------

	@RequestMapping(
			value = "/adresses", 
			method = RequestMethod.POST
			)
	public ResponseEntity<Void> createAdresse(@RequestBody Adresse adresse, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Adresse " + adresse.toString());

		if (adresseService.isExist(adresse)) {
			System.out.println("A Adresse with name" + adresse.toString() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		adresseService.save(adresse);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/adresses/{id}").buildAndExpand(adresse.getId()).toUri());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}


	//------------------- Update a Adresse --------------------------------------------------------

	@RequestMapping(
			value = "/adresses/{id}", 
			method = RequestMethod.PUT
			)
	public ResponseEntity<Adresse> updateAdresse(@PathVariable("id") long id, @RequestBody Adresse adresse) {

		System.out.println("Updating Adresse " + id);

		Adresse currentAdresse = adresseService.findById(id);

		if (currentAdresse == null) {
			System.out.println("Adresse with id " + id + " not found");
			return new ResponseEntity<Adresse>(HttpStatus.NOT_FOUND);
		}
		
		adresse.setId(id);
		adresseService.update(adresse);
		return new ResponseEntity<Adresse>(adresse, HttpStatus.OK);
	}

	//------------------- Delete a Adresse --------------------------------------------------------

	@RequestMapping(
			value = "/adresses/{id}", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Adresse> deleteAdresse(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Adresse with id " + id);

		Adresse adresse = adresseService.findById(id);
		if (adresse == null) {
			System.out.println("Unable to delete. Adresse with id " + id + " not found");
			return new ResponseEntity<Adresse>(HttpStatus.NOT_FOUND);
		}

		adresseService.deleteById(id);
		return new ResponseEntity<Adresse>(HttpStatus.NO_CONTENT);
	}


	//------------------- Delete All Adresses --------------------------------------------------------
	@RequestMapping(
			value = "/adresses/", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Void> deleteAllAdresses() {
		System.out.println("Deleting All Adresses");

		adresseService.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
