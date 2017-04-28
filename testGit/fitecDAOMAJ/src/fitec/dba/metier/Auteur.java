/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/
package fitec.dba.metier;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Auteur.
 * 
 * @author Developpeur
 */
@Entity
public class Auteur extends Metier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6431746195226795801L;

	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Description of the property nom.
	 */
	private String nom ;

	/**
	 * Description of the property prenom.
	 */
	private String prenom ;

	// Start of user code (user defined attributes for Auteur)

	// End of user code

	private Set<Livre> livres ;
	
	/**
	 * The constructor.
	 */
	public Auteur() {
		// Start of user code constructor for Auteur)
		super();
		// End of user code
	}

	public Auteur(Integer id, String nom, String prenom) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}

	// Start of user code (user defined methods for Auteur)

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

	/**
	 * Returns prenom.
	 * @return prenom 
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * Sets a value to attribute prenom. 
	 * @param newPrenom 
	 */
	public void setPrenom(String newPrenom) {
		this.prenom = newPrenom;
	}


	public Set<Livre> getLivres() {
		return livres;
	}

	public void setLivres(Set<Livre> livres) {
		this.livres = livres;
	}

	@Override
	public String toString() {
		return "Auteur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
}
