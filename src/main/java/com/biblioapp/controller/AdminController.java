package com.biblioapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioapp.Dao.EmprunteurDao;
import com.biblioapp.model.Adresse;
import com.biblioapp.model.Bibliothecaire;
import com.biblioapp.model.Emprunt;
import com.biblioapp.model.Emprunteur;
import com.biblioapp.model.Livre;
import com.biblioapp.model.Utilisateur;
import com.biblioapp.model.Enum.TypeVoieEnum;
import com.biblioapp.servicesImpl.AdresseServiceImpl;
import com.biblioapp.servicesImpl.BibliothecaireServiceImpl;
import com.biblioapp.servicesImpl.EmpruntServiceImpl;
import com.biblioapp.servicesImpl.EmprunteurServiceImpl;
import com.biblioapp.servicesImpl.LivreServiceImpl;
import com.biblioapp.servicesImpl.UtilisateurServiceImpl;



@Controller
public class AdminController {

	@Autowired
	private AdresseServiceImpl adresseServiceImpl;
	@Autowired
	private BibliothecaireServiceImpl bibliothecaireServiceImpl;
	@Autowired
	private EmpruntServiceImpl empruntServiceImpl;
	@Autowired
	private EmprunteurServiceImpl emprunteurServiceImpl;
	@Autowired
	private LivreServiceImpl livreServiceImpl;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");



	@RequestMapping(
			value = "/admin", 
			method = RequestMethod.GET
			)
	public String home(Model m) {

		// Tableau des emprunteurs
		List<Emprunteur> emprunteurs = emprunteurServiceImpl.findAll();
		m.addAttribute("nb_emprunteurs", emprunteurs);
		m.addAttribute("emprunteurs", emprunteurs);

		// Tableau des bibliothecaires
		List<Bibliothecaire> bibliothecaires = bibliothecaireServiceImpl.findAll();
		m.addAttribute("nb_bibliothecaires", bibliothecaires);
		m.addAttribute("bibliothecaires", bibliothecaires);

		// Récupération de la liste des utilisateurs
		List<Utilisateur> utilisateurs = new ArrayList<>();
		utilisateurServiceImpl.findAll().forEach(utilisateurs::add);

		System.err.println("Nombre d'utilisateurs:" + utilisateurs.size());
		m.addAttribute("utilisateurs", utilisateurs);

		// Liste des adresses
		List<Adresse> adresses = new ArrayList<>();
		adresseServiceImpl.findAll().forEach(adresses::add);
		System.err.println("Nombre d'adresses:" + adresses.size());
		m.addAttribute("adresses", adresses);

		// Liste des livres
		List<Livre> livres = new ArrayList<>();
		livreServiceImpl.findAll().forEach(livres::add);
		System.err.println("Nombre d'livres:" + livres.size());
		m.addAttribute("ls", livres);

		// Liste des Emprunts
		List<Emprunt> emprunts = new ArrayList<>();
		empruntServiceImpl.findAll().forEach(emprunts::add);
		System.err.println("Nombre d'Emprunts:" + emprunts.size());
		m.addAttribute("ls", emprunts);



		return "index.html";
	}

	@RequestMapping(
			value = "/admin-actions", 
			method = RequestMethod.POST
			)
	public String playActions(
			Model m,
			@RequestParam long id,
			@RequestParam String typeAction,
			@RequestParam String model
			) {
		if ("SUPPRIMER".equals(typeAction)) {
			switch(model) {
			case "Utilisateur":
				utilisateurServiceImpl.deleteById(id);
				break;

			case "Adresse":
				adresseServiceImpl.deleteById(id);
				break;
			}
		} else if ("MODIFIER".equals(typeAction)) {
			switch(model) {
			case "Utilisateur":
				m.addAttribute("utilisateurCourant", utilisateurServiceImpl.findById(id).getId());
				m.addAttribute("modele", "Utilisateur");
				break;

			case "Adresse":
				m.addAttribute("adresseCourant", adresseServiceImpl.findById(id).getId());
				m.addAttribute("modele", "Adresse");
				break;

			case "Emprunteur":
				m.addAttribute("enprunteurCourant", emprunteurServiceImpl.getEmprunteurDao());
				m.addAttribute("modele", "Emprunteur");
				break;
			}
			m.addAttribute("afficherModal", true);
		}
		return this.home(m);
	}

	@RequestMapping(
			value = "/admin-ajout-utilisateur", 
			method = RequestMethod.POST
			)
	public String ajouterUtilisateur(
			Model m,
			@RequestParam String prenom,
			@RequestParam String nom,
			@RequestParam String email,
			@RequestParam String dateNaissance
			) {

		Utilisateur u = new Utilisateur();
		u.setNom(nom);
		u.setPrenom(prenom);
		u.setEmail(email);
		try {
			u.setDateNaissance(formatter.parse(dateNaissance));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		utilisateurServiceImpl.save(u);

		return home(m);
	}

	@RequestMapping(
			value = "/admin-ajout-adresse", 
			method = RequestMethod.POST
			)
	public String ajouterAdresse(
			Model m,
			@RequestParam String numero,
			@RequestParam TypeVoieEnum typeVoie,
			@RequestParam String nomVoie,
			@RequestParam String codePostal,
			@RequestParam String ville
			) {

		Adresse a = new Adresse();
		a.setNumero(numero);
		a.setTypeVoie(typeVoie);
		a.setNomVoie(nomVoie);
		a.setCodePostal(codePostal);
		a.setVille(ville);

		adresseServiceImpl.save(a);

		return home(m);
	}

	@RequestMapping(
			value = "/admin-ajout-client", 
			method = RequestMethod.POST
			)
	public String ajouterClient(
			Model m,
			@RequestParam long id,
			@RequestParam long utilisateurId,
			@RequestParam long adresseResidenceId,
			@RequestParam long adresseLivraisonId
			) {

		EmprunteurDao e = (EmprunteurDao) new Emprunteur();
		if (id > 0) {
			((Emprunteur) e).setId(id);
		}
		long u = utilisateurServiceImpl.findById(utilisateurId).getId();
		long a1 = adresseServiceImpl.findById(adresseResidenceId).getId();
		long a2 = adresseServiceImpl.findById(adresseLivraisonId).getId();




		emprunteurServiceImpl.setEmprunteurDao(e);
		m.addAttribute("emprunteurCourant", null);

		return home(m);
	}
}
