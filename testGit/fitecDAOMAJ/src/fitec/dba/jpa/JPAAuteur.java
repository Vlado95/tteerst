package fitec.dba.jpa;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fitec.dba.dao.IDao;
import fitec.dba.metier.Auteur;
import fitec.dba.metier.Livre;

public class JPAAuteur implements IDao<Auteur> {

	EntityManager em;
	
	private EntityManager getEntityFactory()
	{
		if (em == null)
		{
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("fitecJPA");
			em = factory.createEntityManager();
		}
		
		return em;
	}
	
	@Override
	public List<Auteur> selectAll() {

		em = getEntityFactory();
		Query query = em.createQuery("select a from Auteur a");
		List<Auteur> auteurs = query.getResultList();
		
		return auteurs;
	}

	@Override
	public ResultSet selectAllByResultSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auteur selectById(Auteur objet) {
		em = getEntityFactory();
		return getEntityFactory().find(Auteur.class, objet.getId());
	}

	@Override
	public int insert(Auteur objet) {
		em = getEntityFactory();
		
		EntityTransaction transac = em.getTransaction();
		transac.begin();

		Auteur auNew = em.merge(objet);
		//em.persist(aut);
		
		transac.commit();
		
		return auNew.getId();
	}

	@Override
	public boolean update(Auteur objet) {
		em = getEntityFactory();
		Auteur aut = selectById(objet);
		
		EntityTransaction transac = em.getTransaction();
		transac.begin();

		Auteur auNew = em.merge(aut);
		
		transac.commit();
		
		return auNew.getId() != null ? true : false;
	}

	@Override
	public boolean delete(Auteur auteur) throws Exception {
		boolean status = false;
		
		em = getEntityFactory();	
		EntityTransaction transac = em.getTransaction();
		
		try {
			transac.begin();
			Query query = em.createQuery("select l from Livre l where l.auteur.id = :id");
			query.setParameter("id", auteur.getId());
			List<Livre> l = query.getResultList();
			if (l != null && l.size() > 0) {
				transac.rollback();
				throw new Exception("Auteur encore référencé par au moins un livre");
			}else{
				Auteur edToDelete = em.find(Auteur.class, auteur.getId());
				em.remove(edToDelete);
				transac.commit();
			}
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode DELETE AUTEUR : " + e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	public List<Auteur> searchLike(String str) {
		em = getEntityFactory();
		List<Auteur> auteurListe =em.createQuery("select A from Auteur A where A.nom like ?1")
			.setParameter(1, "%"+str+"%")
			.getResultList();
		return auteurListe;
	}

}
