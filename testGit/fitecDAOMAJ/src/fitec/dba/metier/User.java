/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/
package fitec.dba.metier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of User.
 * 
 * @author Developpeur
 */
@Entity
public class User extends Metier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2518696209539662335L;

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

	/**
	 * Description of the property prenom.
	 */
	private String prenom;

	/**
	 * Description of the property email.
	 */
	private String email;

	/**
	 * Description of the property password.
	 */
	private String password;

	// Start of user code (user defined attributes for User)

	// End of user code

	/**
	 * The constructor.
	 */
	public User() {
		// Start of user code constructor for User)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for User)

	public User(Integer id, String nom, String prenom, String email, String password) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
	}

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

	/**
	 * Returns email.
	 * @return email 
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets a value to attribute email. 
	 * @param newEmail 
	 */
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	/**
	 * Returns password.
	 * @return password 
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets a value to attribute password. 
	 * @param newPassword 
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + "]";
	}

}
