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
import fitec.dba.metier.User;

/**
 *
 * @author Fitec
 */
public class DaoUser implements IDao<User> {

	private static Connection cnx;

	
//	private DaoUser(){		
//	}
	
	@Override
	public List<User> selectAll() {

		List<User> l = new ArrayList<>();
		String sql = "Select * From user";
		try {
			cnx = BddFactory.getDefaultConnection();
			Statement stat = cnx.createStatement();

			ResultSet res = stat.executeQuery(sql);

			while (res.next()) {
				User u = new User(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5));
				l.add(u);

			}

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return l;
	}

	/**
	 * SELECT BY ID :
	 * Dans cet exemple on montre comment on peut accéder les colonnes par leur nom dans le ResultSet
	 * 
	 */
	@Override
	public User selectById(User objet) {
		String sql = "Select * From user where id = ?";
		try {
			cnx = BddFactory.getDefaultConnection();
			PreparedStatement stat = cnx.prepareStatement(sql);

			stat.setInt(1, objet.getId());
			ResultSet res = stat.executeQuery();

				if ( res.first() ){
					objet.setId(res.getInt("id"));
					objet.setNom(res.getString("nom"));
					objet.setPrenom(res.getString("prenom"));
					objet.setPrenom(res.getString("email"));
					objet.setPrenom(res.getString("password"));
				}else{
					objet = null;
				}
		
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return objet;
	}

	
	/**
	 * INSERT : Récupération de l'Id généré
	 * 
	 */
	@Override
	public int insert(User objet) {
		int id = 0;
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "INSERT INTO `user`(`nom`, `prenom`, `email`, `password`) VALUES (?,?,?,?)";

			PreparedStatement stat = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setString(1, objet.getNom());
			stat.setString(2, objet.getPrenom());
			stat.setString(3, objet.getEmail());
			stat.setString(4, objet.getPassword());

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
	 * 
	 */
	@Override
	public boolean update(User objet) {
		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "UPDATE `user` SET `nom`=?,`prenom`=?,`email`=?,`password`=? WHERE id = ?";

			PreparedStatement stat = cnx.prepareStatement(sql);

			stat.setString(1, objet.getNom());
			stat.setString(2, objet.getPrenom());
			stat.setString(3, objet.getEmail());
			stat.setString(4, objet.getPassword());
			stat.setInt(5, objet.getId());

			stat.executeUpdate();

			int res = stat.getUpdateCount();

			status = ( res != 0 ? true : false );
			
			return status;

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	@Override
	public boolean delete(User objet) {
		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "delete from user where id = ? ";
			
			PreparedStatement stat = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setInt(1, objet.getId());
			stat.executeUpdate();
			int res = stat.getUpdateCount();

			status = ( res != 0 ? true : false );

		} catch (ClassNotFoundException | SQLException e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	public static User selectLogin(User user) {
		String sql = "Select * From user where email = ? and password = ?";
		try {
			cnx = BddFactory.getDefaultConnection();
			PreparedStatement stat = cnx.prepareStatement(sql);

			stat.setString(1, user.getEmail());
			stat.setString(2, user.getPassword());
			ResultSet res = stat.executeQuery();

			if ( res.first() ){
				user.setId(res.getInt("id"));
				user.setNom(res.getString("nom"));
				user.setPrenom(res.getString("prenom"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
			}else{
				user = null;
			}

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return user;
	}

	@Override
	public ResultSet selectAllByResultSet() {
		String sql = "Select * From user";
		ResultSet res = null;
		try {
			cnx = BddFactory.getDefaultConnection();
			Statement stat = cnx.createStatement();

			res = stat.executeQuery(sql);

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return res;
	}

	@Override
	public List<User> searchLike(String str) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
