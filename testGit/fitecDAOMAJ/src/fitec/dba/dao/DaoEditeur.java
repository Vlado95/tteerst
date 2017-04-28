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
import fitec.dba.metier.Editeur;

/**
 * CLASSE DAO Pour les Objets de type Editeur
 * 
 * @author Fitec
 */
public class DaoEditeur implements IDao<Editeur> {

	private Connection cnx;
	
	@Override
	public List<Editeur> selectAll() {
		List<Editeur> l = new ArrayList<>();
		String sql = "Select * From editeur";
		try {
			cnx = BddFactory.getDefaultConnection();
			Statement stat = this.cnx.createStatement();

			ResultSet res = stat.executeQuery(sql);

			while (res.next()) {
				Editeur e = new Editeur(res.getInt(1), res.getString(2));
				l.add(e);
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return l;
	}

	@Override
	public Editeur selectById(Editeur objet) {

		String sql = "Select * From editeur where id = ?";
		try {
			cnx = BddFactory.getDefaultConnection();
			PreparedStatement stat = this.cnx.prepareStatement(sql);

			stat.setInt(1, objet.getId());
			ResultSet res = stat.executeQuery();

			if ( res != null ){
				res.first();
				objet.setId(res.getInt("id"));
				objet.setNom(res.getString("nom"));
			}

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return objet;
	}

	/**
	 * INSERT : Récupération de l'Id de l'objet nouvellement créé.
	 * 
	 */
	@Override
	public int insert(Editeur objet) {
		int id = 0;	
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "INSERT INTO `editeur`(`nom`) VALUES (?)";

			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setString(1, objet.getNom());

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

	@Override
	public boolean update(Editeur objet) {
		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();

			String sql = "update editeur set nom= ? where id = ?";

			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setString(1, objet.getNom());
			stat.setInt(2, objet.getId());

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

	@Override
	public boolean delete(Editeur objet) {
		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "delete from editeur where id = ? ";
			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setInt(1, objet.getId());
			stat.executeUpdate();
			int res = stat.getUpdateCount();

			status = res == 1 ? true : false;

		} catch (ClassNotFoundException | SQLException e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public ResultSet selectAllByResultSet() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<Editeur> searchLike(String str) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
