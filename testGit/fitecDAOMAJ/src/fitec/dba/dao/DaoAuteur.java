/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitec.dba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fitec.dba.factory.BddFactory;
import fitec.dba.metier.Auteur;

/**
 * COUCHE Data Acces Object ( DAO )
 * De l'application Bibliothèque 
 * FITEC
 *
 *La classe implemente l'interface définit par IDAO et la spécialise en Auteur
 *
 * @author Fitec
 */
public class DaoAuteur implements IDao<Auteur> {

	// Attribut représentant un Object Connexion
	
	private Connection cnx;

	@Override
	public List<Auteur> selectAll() {
		List<Auteur> l = new ArrayList<>();
		String sql = "Select * From auteur";
		try {
			cnx = BddFactory.getDefaultConnection();
			Statement stat = this.cnx.createStatement();

			ResultSet res = stat.executeQuery(sql);

			while (res.next()) {
				Auteur a = new Auteur(res.getInt(1), res.getString(2), res.getString(3));
				l.add(a);
			}

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return l;
	}

	@Override
	public Auteur selectById(Auteur objet) {

		String sql = "Select * From auteur where id = ?";
		try {
			cnx = BddFactory.getDefaultConnection();
			PreparedStatement stat = this.cnx.prepareStatement(sql);

			stat.setInt(1, objet.getId());
			ResultSet res = stat.executeQuery();

			res.first();

			objet.setId(res.getInt("id"));
			objet.setNom(res.getString("nom"));
			objet.setPrenom(res.getString("prenom"));

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return objet;
	}

	/**
	 * Insert renvoie l'Id de l'objet venant d'être créé ou 0 si l'opération 
	 * s'est mal passée
	 */
	@Override
	public int insert(Auteur objet) {
		int id = 0; 
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "INSERT INTO `auteur`(`nom`, `prenom`) VALUES (?,?)";

			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setString(1, objet.getNom());
			stat.setString(2, objet.getPrenom());

			stat.executeUpdate();
			
			ResultSet res = stat.getGeneratedKeys();
			res.first();
			id = res.getInt(1);
			
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id;
	}

	/**
	 * UPDATE - Effectue une mise à jour d'un Objet passé en paramètre
	 * Renvoie : True si MAJ, false si problème
	 */
	@Override
	public boolean update(Auteur objet) {

		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();

			String sql = "update auteur set nom=?, prenom=? where id = ? ";

			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setString(1, objet.getNom());
			stat.setString(2, objet.getPrenom());
			stat.setInt(3, objet.getId());

			stat.executeUpdate();

			int count = stat.getUpdateCount();

			status = (count != 0 ? true : false);

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return status;
	}

	/**
	 *  DELETE : Effectue une suppression pour un objet donné (sur Id)
	 *  Renvoie : True si MAJ, false si problème
	 */
	@Override
	public boolean delete(Auteur objet) {
		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "delete from auteur where id = ? ";
			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setInt(1, objet.getId());
			stat.executeUpdate();
			int res = stat.getUpdateCount();

			status = res != 0 ? true : false;

		} catch (ClassNotFoundException | SQLException e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * SELECT ALL : Renvoie un ResultSet au lieu d'une liste
	 * Pour l'exemple
	 * 
	 */
	@Override
	public ResultSet selectAllByResultSet() {
		String sql = "Select * From auteur";
		ResultSet res = null;
		try {
			cnx = BddFactory.getDefaultConnection();
			Statement stat = this.cnx.createStatement();

			res = stat.executeQuery(sql);

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}

	@Override
	public List<Auteur> searchLike(String str) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
