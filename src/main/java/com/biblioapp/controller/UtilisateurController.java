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

import com.biblioapp.model.Utilisateur;
import com.biblioapp.services.UtilisateurService;


@RestController
public class UtilisateurController {

	@Autowired
	UtilisateurService utilisateurService;  //Service which will do all data retrieval/manipulation work

	//-------------------Retrieve All Utilisateurs--------------------------------------------------------
	@RequestMapping(
			value = "/utilisateurs", 
			method = RequestMethod.GET
			)
	public ResponseEntity<List<Utilisateur>> listAllUtilisateurs() {
		List<Utilisateur> utilisateurs = utilisateurService.findAll();
		return new ResponseEntity<List<Utilisateur>>(utilisateurs, HttpStatus.OK);
	}


	//-------------------Retrieve Single Utilisateur--------------------------------------------------------

	@RequestMapping(
			value = "/utilisateurs/{id}", 
			method = RequestMethod.GET, 
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody ResponseEntity<Utilisateur> getUtilisateur(@PathVariable("id") long id) {
		System.out.println("Fetching Utilisateur with id " + id);
		Utilisateur utilisateur = utilisateurService.findById(id);
		if (utilisateur == null) {
			System.out.println("Utilisateur with id " + id + " not found");
			return new ResponseEntity<Utilisateur>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.OK);
	}


	//-------------------Create a Utilisateur--------------------------------------------------------

	@RequestMapping(
			value = "/utilisateurs", 
			method = RequestMethod.POST
			)
	public ResponseEntity<Void> createUtilisateur(@RequestBody Utilisateur utilisateur, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Utilisateur " + utilisateur.getNom());

		if (utilisateurService.isExist(utilisateur)) {
			System.out.println("A Utilisateur with name " + utilisateur.getNom() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		utilisateurService.save(utilisateur);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/utilisateurs/{id}").buildAndExpand(utilisateur.getId()).toUri());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}


	//------------------- Update a Utilisateur --------------------------------------------------------

	@RequestMapping(
			value = "/utilisateurs/{id}", 
			method = RequestMethod.PUT
			)
	public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable("id") long id, @RequestBody Utilisateur utilisateur) {

		System.out.println("Updating Utilisateur " + id);

		Utilisateur currentUtilisateur = utilisateurService.findById(id);

		if (currentUtilisateur == null) {
			System.out.println("Utilisateur with id " + id + " not found");
			return new ResponseEntity<Utilisateur>(HttpStatus.NOT_FOUND);
		}
		
		utilisateur.setId(id);
		utilisateurService.update(utilisateur);
		return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.OK);
	}

	//------------------- Delete a Utilisateur --------------------------------------------------------

	@RequestMapping(
			value = "/utilisateurs/{id}", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Utilisateur> deleteUtilisateur(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Utilisateur with id " + id);

		Utilisateur utilisateur = utilisateurService.findById(id);
		if (utilisateur == null) {
			System.out.println("Unable to delete. Utilisateur with id " + id + " not found");
			return new ResponseEntity<Utilisateur>(HttpStatus.NOT_FOUND);
		}

		utilisateurService.deleteById(id);
		return new ResponseEntity<Utilisateur>(HttpStatus.NO_CONTENT);
	}


	//------------------- Delete All Utilisateurs --------------------------------------------------------
	@RequestMapping(
			value = "/utilisateurs/", 
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Void> deleteAllUtilisateurs() {
		System.out.println("Deleting All Utilisateurs");

		utilisateurService.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
