package fitec.dba.hbn;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import fitec.dba.dao.IDao;
import fitec.dba.metier.Editeur;
import fitec.dba.metier.Livre;

public class HbnDaoEditeur implements IDao<Editeur> {
	
	private Session session ;
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		if ( session == null ){
			session = sessionFactory.openSession();
		}
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public HbnDaoEditeur() {
	}

	public List<Editeur> selectAll() {
		Session session = getSession();
		List<Editeur> editeurs = null;
		try {
			String query = "From Editeur ";
			editeurs = session.createQuery(query).list();
		} catch (Exception e) {
			System.out.println("problème sur selectAll editeur : " + e.getLocalizedMessage());
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur selectAll editeur : " + e.getLocalizedMessage());
		}
		return editeurs;
	}

	@Override
	public Editeur selectById(Editeur editeur) {
		Session session = getSession();
		try {
			int id = editeur.getId();
			editeur = session.find(Editeur.class, id);
			if (editeur == null) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"Problème sur selectById editeur, Editeur introuvable " + editeur.getId());
			}

		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur selectById editeur : " + e.getLocalizedMessage());
		}
		return editeur;

	}

	@Override
	public ResultSet selectAllByResultSet() {

		return null;
	}

	@Override
	public int insert(Editeur editeur) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.save(editeur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème d'insertion sur editeur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return editeur.getId();

	}

	@Override
	public boolean update(Editeur editeur) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.merge(editeur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur update de editeur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}

		return (editeur != null ? true : false);
	}

	@Override
	public boolean delete(Editeur editeur) throws Exception {
		Session session = getSession();
		boolean status = false;
		// Vérifier si un livre référence ancore l'objet Editeur
		// Gestion de la contrainte référentielle au niveau de la couche
		// Persistence
		
		Query query = session.createQuery("from Livre as l where l.editeur.id = :id");
		query.setParameter("id", editeur.getId());

		List<Livre> l = query.getResultList();

		if (l != null && l.size() > 0) {
			throw new Exception("Editeur encore référencé par au moins un livre");
		}
		
		try {
			session.getTransaction().begin();
			editeur = session.find(Editeur.class, editeur.getId());
			session.delete(editeur);
			session.getTransaction().commit();
			status = true;

		} catch (Exception e) {

			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Methode DELETE EDITEUR : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return status;
	}

	@Override
	public List<Editeur> searchLike(String str) {
		Session session = getSession();
		List<Editeur> editeurs= null;
		try {
			Query req = session.createQuery("from Editeur as e where e.nom like  '%" + str + "%' ");
			editeurs = (List<Editeur>) req.getResultList();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"SearchLike error! : " + e.getLocalizedMessage());
		}
		return editeurs;
	}
}
