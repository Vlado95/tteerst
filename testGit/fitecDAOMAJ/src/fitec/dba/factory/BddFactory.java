/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fitec.dba.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe Technique permet la connexion à la base de donn�e
 * Cet Objet doit  être unique pour 1 Application et retourne une Connexion Authentifi�e
 * A l'appelant pour les accée subsequents à la Base de données.
 * Pattern : Singleton
 * 
 * @author Utilisateur
 *
 */
public class BddFactory {
	/**
	 * CONSTRUCTEUR PRIVE 
	 */
	private BddFactory() {
			connection = initConnection();	
	}
	
	private static Connection connection ;
	
	protected Connection initConnection() {	
		Properties props = new Properties();
		InputStream is = getClass().getClassLoader().getResourceAsStream("biblio.properties");
		
		// Charge le fichier Properties de l'application
		if ( is != null ){
			try {
				props.load(is);
				is.close();
			} catch (IOException e) {
				System.err.println("Le fichier properties n'est pas présent ! :"+e.getLocalizedMessage());
			}
		}
		
		// Charge le driver JDBC pour notre type de base (MySQL ici)
		// Vérifie que le Driver est présent dans le Path, pour le ClassLoader
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Le driver JDBC n'est pas disponible:"+e.getLocalizedMessage());
		}
		
		String host = props.getProperty("HOST");
		String port = props.getProperty("PORT");
		String dbName = props.getProperty("DB_NAME");
		String login = props.getProperty("LOGIN");
		String password = props.getProperty("PASSWORD");
		
		Connection cnx = null ;
		String URL = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
		try {
			cnx = DriverManager.getConnection(URL, login, password);
		} catch (SQLException e) {
			System.err.println("Erreur dans les paramètres de connexion ou serveur indisponible :"+e.getLocalizedMessage());
		}
		
		return cnx;
	}

	//
	// Nullify connection Object 
	// Garbage Collector
	//
	public void deconnecter() throws SQLException {
		if ( connection != null ){
			connection.close();
			connection = null;
		}
	}
	
	/**
	 * Méthode statique faisant partie du Design Pattern Factory et Singleton
	 * Afin de recupérer une référence à la connexion par Défaut.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getDefaultConnection() throws ClassNotFoundException, SQLException {
		if ( connection == null ){
			new BddFactory();
		}		
		return connection;
	}
	
	public static boolean isOK() throws SQLException{
		return ( connection != null?!connection.isClosed():false );
	}
}
