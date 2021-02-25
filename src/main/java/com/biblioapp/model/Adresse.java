package com.biblioapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.biblioapp.model.Enum.TypeVoieEnum;

@Entity
public class Adresse {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String numero;
	
	@NotNull
	private TypeVoieEnum typeVoie;
	
	@NotNull
	private String nomVoie;
	
	@NotNull
	private String codePostal;
	
	@NotNull
	private String ville;
	
	

	public Adresse() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TypeVoieEnum getTypeVoie() {
		return typeVoie;
	}

	public void setTypeVoie(TypeVoieEnum typeVoie) {
		this.typeVoie = typeVoie;
	}

	public String getNomVoie() {
		return nomVoie;
	}

	public void setNomVoie(String nomVoie) {
		this.nomVoie = nomVoie;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
	
	@Override
	public String toString() {
		return "Adresse [numero=" + numero + ", typeVoie=" + typeVoie + ", nomVoie=" + nomVoie
				+ ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}

}
