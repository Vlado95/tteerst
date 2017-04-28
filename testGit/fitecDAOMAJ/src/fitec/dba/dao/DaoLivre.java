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
import fitec.dba.factory.FactoryDao;
import fitec.dba.metier.Auteur;
import fitec.dba.metier.Editeur;
import fitec.dba.metier.Livre;

/**
 * CLASSE DAO Pour les Objets Livre - Impl�mente l'interface IDAO 
 *
 * @author Fitec
 */
public class DaoLivre implements IDao<Livre> {

	private Connection cnx;

	@Override
	public List<Livre> selectAll() {
		List<Livre> l = new ArrayList<>();
		String sql = "Select * From livre";
		try {
			cnx = BddFactory.getDefaultConnection();
			Statement stat = this.cnx.createStatement();

			ResultSet res = stat.executeQuery(sql);

			IDao daoAuteur = FactoryDao.getDAO("Auteur");
			IDao daoEditeur = FactoryDao.getDAO("Editeur");

			while (res.next()) {
				Auteur a = new Auteur();
				a.setId(res.getInt("id_auteur"));
				a = (Auteur) daoAuteur.selectById(a);

				Editeur e = new Editeur();
				e.setId(res.getInt("id_editeur"));
				e = (Editeur) daoEditeur.selectById(e);

				Livre livre = new Livre(res.getInt("id"), res.getString("titre"), a, e, res.getInt("nb_pages"),
						res.getFloat("prix"));
				l.add(livre);
			}

		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return l;

	}

	@Override
	public Livre selectById(Livre objet) {
		Livre livre = null;
		String sql = "Select * From livre where id = ?";
		try {
			cnx = BddFactory.getDefaultConnection();
			PreparedStatement stat = this.cnx.prepareStatement(sql);

			stat.setInt(1, objet.getId());
			ResultSet res = stat.executeQuery();
			
			IDao daoAuteur = FactoryDao.getDAO("Auteur");
			IDao daoEditeur = FactoryDao.getDAO("Editeur");

			while (res.next()) {
				Auteur a = new Auteur();
				a.setId(res.getInt("id_auteur"));
				a = (Auteur) daoAuteur.selectById(a);

				Editeur e = new Editeur();
				e.setId(res.getInt("id_editeur"));
				e = (Editeur) daoEditeur.selectById(e);

				livre = new Livre(res.getInt("id"), res.getString("titre"), a, e, res.getInt("nb_pages"),
						res.getFloat("prix"));
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return livre;
	}

	@Override
	public int insert(Livre objet) {
		int id = 0 ;
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "INSERT INTO `livre`(`id_auteur`, `id_editeur`, `titre`, `nb_pages`, `prix`) VALUES (?,?,?,?,?)";

			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setInt(1, objet.getAuteur().getId());
			stat.setInt(2, objet.getEditeur().getId());
			stat.setString(3, objet.getTitre());
			stat.setInt(4, objet.getNbPages());
			stat.setFloat(5, objet.getPrix());

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
	public boolean update(Livre objet) {
		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();

			String sql = "update livre set id_auteur=?, id_editeur=? , titre=?, nb_pages=?, prix=? where id=?";

			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setInt(1, objet.getIdAuteur());
			stat.setInt(2, objet.getIdEditeur());
			stat.setString(3, objet.getTitre());
			stat.setInt(4, objet.getNbPages());
			stat.setFloat(5, objet.getPrix());
			stat.setInt(6, objet.getId());

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
	public boolean delete(Livre objet) {
		boolean status = false;
		try {
			cnx = BddFactory.getDefaultConnection();
			String sql = "delete from livre where id = ? ";
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
	public List<Livre> searchLike(String str) {
		List<Livre> l = new ArrayList<>();
		String sql = "Select * From livre where titre like '%" + str + "%' ";
		try {
			cnx = BddFactory.getDefaultConnection();
			PreparedStatement stat = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet res = stat.executeQuery();

			IDao daoAuteur = FactoryDao.getDAO("Auteur");
			IDao daoEditeur = FactoryDao.getDAO("Editeur");

			while (res.next()) {
				Auteur a = new Auteur();
				a.setId(res.getInt("id_auteur"));
				a = (Auteur) daoAuteur.selectById(a);

				Editeur e = new Editeur();
				e.setId(res.getInt("id_editeur"));
				e = (Editeur) daoEditeur.selectById(e);

				Livre livre = new Livre(res.getInt("id"), res.getString("titre"), a, e, res.getInt("nb_pages"),
						res.getFloat("prix"));
				l.add(livre);
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return l;

	}
}
