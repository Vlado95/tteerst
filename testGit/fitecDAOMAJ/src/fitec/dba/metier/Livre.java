/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/
package fitec.dba.metier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Livre extends Metier implements Serializable {
	
	private static final long serialVersionUID = 2839187564837878273L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	private String titre;

	@ManyToOne
	@JoinColumn(name="id_auteur")
	private Auteur auteur;

	@ManyToOne
	@JoinColumn(name="id_editeur")
	private Editeur editeur;

	
	private int nbPages = 0;

	private Float prix;

	public Livre() {
		
		super();
		
	}

	public Livre(Integer id, String titre, Auteur auteur, Editeur editeur, int nbpages, Float prix) {
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
		this.editeur = editeur;
		this.nbPages = nbpages;
		this.prix = prix;
	}

	
	public Integer getId() {
		return this.id;
	}

	
	public void setId(Integer newId) {
		this.id = newId;
	}

	
	public String getTitre() {
		return this.titre;
	}

	
	public void setTitre(String newTitre) {
		this.titre = newTitre;
	}

	
	public Auteur getAuteur() {
		return this.auteur;
	}

	
	public void setAuteur(Auteur newAuteur) {
		this.auteur = newAuteur;
	}

	
	public Editeur getEditeur() {
		return this.editeur;
	}

	
	public void setEditeur(Editeur newEditeur) {
		this.editeur = newEditeur;
	}

	
	public int getNbPages() {
		return nbPages;
	}

	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	public Integer getIdAuteur() {
		return this.auteur.getId();
	}

	
	public Integer getIdEditeur() {
		return this.editeur.getId();
	}

	public String getNomAuteur() {
		return this.auteur.getNom();
	}

	@Override
	public String toString() {
		return "Livre [id=" + id + ", titre=" + titre + ", auteur=" + auteur + ", editeur=" + editeur + ", nb_Pages="
				+ nbPages + ", prix=" + prix + "]";
	}

	public String getPreNomAuteur() {
		return this.auteur.getPrenom();
	}

	public String getNomEditeur() {
		return this.editeur.getNom();
	}
}
