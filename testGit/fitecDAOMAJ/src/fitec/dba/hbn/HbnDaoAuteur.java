package fitec.dba.hbn;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import fitec.dba.dao.IDao;
import fitec.dba.metier.Auteur;
import fitec.dba.metier.Livre;


public class HbnDaoAuteur implements IDao<Auteur> {

	// Ajout de la sessionFactory pour Injection par Spring
	private static SessionFactory sessionFactory ; 
	
	private Session session ;
	
	/**
	 * Création des setter/getter pour injection par Spring
	 * 
	 * @return : SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		HbnDaoAuteur.sessionFactory = sessionFactory;
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

	public HbnDaoAuteur() {
	}

	public List<Auteur> selectAll() {
		/**
		 * Utilisation des interfaces Criteria et Builder
		 */
		Session session = getSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Auteur> criteria = (CriteriaQuery<Auteur>) cb.createQuery(Auteur.class);
		criteria.from(Auteur.class);
		List<Auteur> auteurs = session.createQuery(criteria).list();
		return auteurs;

	}

	@Override
	public Auteur selectById(Auteur auteur) {
		Session session = getSession();
		try {
			int id = auteur.getId();
			auteur = session.find(Auteur.class, id);
			if (auteur == null) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"Problème sur selectById auteur, Auteur introuvable " + auteur);
			}

		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur selectById auteur : " + e.getLocalizedMessage());
		}
		return auteur;

	}

	@Override
	public ResultSet selectAllByResultSet() {

		return null;
	}

	@Override
	public int insert(Auteur auteur) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.save(auteur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème d'insertion sur auteur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return auteur.getId();

	}

	@Override
	public boolean update(Auteur auteur) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.merge(auteur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur update de auteur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}

		return (auteur != null ? true : false);
	}

	@Override
	public boolean delete(Auteur auteur) throws Exception {
		boolean status = false;
		Session session = getSession();
		// Vérifier si un livre référence ancore l'objet Auteur
		// Gestion de la contrainte référentielle au niveau de la couche
		// Persistence
		
		Query query = session.createQuery("from Livre as l where l.auteur.id = :id");
		query.setParameter("id", auteur.getId());

		List<Livre> l = query.getResultList();

		if (l != null && l.size() > 0) {
			throw new Exception("Auteur encore référencé par au moins un livre");
		}
		
		try {
			session.getTransaction().begin();
			auteur = session.find(Auteur.class, auteur.getId());
			session.delete(auteur);
			session.getTransaction().commit();
			status = true;

		} catch (Exception e) {

			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Methode DELETE AUTEUR : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return status;
	}

	@Override
	public List<Auteur> searchLike(String str) {
		Session session = getSession();
		List<Auteur> auteurs=null;
		try {

			Query req = session.createQuery("from Auteur as e where e.nom like  '%" + str + "%' ");
			auteurs = req.getResultList();
			
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"SearchLike error! : " + e.getLocalizedMessage());
		}
		return auteurs;
	}
}
