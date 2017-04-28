/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/
package fitec.dba.metier;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Editeur.
 * 
 * @author Developpeur
 */
 @Entity
public class Editeur extends Metier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1583693605369206809L;

	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Description of the property nom.
	 */
	private String nom;

	// Start of user code (user defined attributes for Editeur)

	// End of user code

	private Set<Livre> livres ;
	
	/**
	 * The constructor.
	 */
	public Editeur() {
		// Start of user code constructor for Editeur)
		super();
		// End of user code
	}

	public Editeur(Editeur e) {
		this(e.getId(), e.getNom());
	}

	public Editeur(Integer id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	// Start of user code (user defined methods for Editeur)

	// End of user code
	/**
	 * Returns id.
	 * @return id 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets a value to attribute id. 
	 * @param newId 
	 */
	public void setId(Integer newId) {
		this.id = newId;
	}

	/**
	 * Returns nom.
	 * @return nom 
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Sets a value to attribute nom. 
	 * @param newNom 
	 */
	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public Set<Livre> getLivres() {
		return livres;
	}

	public void setLivres(Set<Livre> livres) {
		this.livres = livres;
	}

	@Override
	public String toString() {
		return "Editeur [id=" + id + ", nom=" + nom + "]";
	}
}
