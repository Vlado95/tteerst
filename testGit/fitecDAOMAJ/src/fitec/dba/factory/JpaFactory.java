/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitec.dba.factory;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe Technique permet la connexion à la base de données
 * Cet Objet doit  être unique pour 1 Application et retourne une Connexion Authentifi�e
 * A l'appelant pour les accès subsequents � la Base de données.
 * Pattern : Singleton
 * 
 * @author Utilisateur
 *
 */
public class JpaFactory {
	/**
	 * CONSTRUCTEUR PRIVE 
	 */
	private JpaFactory() {
			em = initJpa();	
	}
	
	private static EntityManager em ;
	
	/**
	 * Initialize un Manager d'Entit�s EntityManager 
	 * @return : EntityManger
	 */
	protected EntityManager initJpa() {	
		
		EntityManagerFactory factory =Persistence.createEntityManagerFactory("fitecJPA");
		em = factory.createEntityManager();
		return em;
	}

	
	
	/**
	 * M�thode statique faisant partie du Design Pattern Factory et Singleton
	 * Afin de recup�rer une r�f�rence � la connexion par D�faut.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static EntityManager getDefaultConnection() throws ClassNotFoundException, SQLException {
		if ( em == null ){
			new JpaFactory();
		}		
		return em;
	}
	
	/**
	 * Donne le statut de l'Entity Manager
	 * @return
	 * @throws SQLException
	 */
	public static boolean isOK() throws SQLException{
		return ( em != null? em.isOpen():false );
	}
}
