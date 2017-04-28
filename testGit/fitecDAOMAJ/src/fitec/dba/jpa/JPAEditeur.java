package fitec.dba.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fitec.dba.dao.IDao;
import fitec.dba.factory.JpaFactory;
import fitec.dba.metier.Editeur;
import fitec.dba.metier.Livre;

public class JPAEditeur implements IDao<Editeur>{

	EntityManager em;
	
	
	public JPAEditeur() {
		//persistence-unit
		  try {
			em = JpaFactory.getDefaultConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Editeur> selectAll(){
		// "Editeur" ici dans cette requete, reference l'entite Editeur et non la table
		Query query = (Query) em.createQuery("select e from Editeur e");
		List<Editeur> listE = query.getResultList();
		return listE;
	}
	
	@Override
	public Editeur selectById(Editeur editeur) {
		
		Query query = em.createQuery("select e from Editeur e where e.id = :id");
		query.setParameter("id", editeur.getId());
		
		editeur = (Editeur) query.getSingleResult();
		
		if(editeur == null) {
			return null;
		}
		return editeur;
		
	}

	@Override
	public ResultSet selectAllByResultSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Editeur editeur) {
		em.getTransaction().begin();
		editeur = em.merge(editeur);
		em.flush();
		em.getTransaction().commit();
		return editeur.getId();
	}

	@Override
	public boolean update(Editeur editeur) {
		boolean status = false;
		try {
			em.getTransaction().begin();

			Editeur e = em.find(Editeur.class, editeur.getId());

			e.setNom(editeur.getNom());
			em.flush();
			em.getTransaction().commit();
			status = true;
		} catch (Exception e) {
			System.err.println("Methode UPDATE Editeur : " + e.getLocalizedMessage());
		}

		return status;
	}

	@Override
	public boolean delete(Editeur editeur) throws Exception {
		boolean status = false;
		EntityTransaction transac = em.getTransaction();
		try{
			transac.begin();
				
			// Vérifier si un livre référence ancore l'objet Editeur
			// Gestion de la contrainte référentielle au niveau de la couche Persistence 
			
			Query query = em.createQuery("select l from Livre l where l.editeur.id = :id");
			query.setParameter("id", editeur.getId());
			
			List<Livre> l = query.getResultList();
			
			if ( l != null && l.size() > 0){
				transac.rollback();
				throw new Exception("Editeur encore référencé par au moins un livre");
			}else{	
				editeur = em.find(Editeur.class, editeur.getId());
				em.remove(editeur);
				em.flush();
				transac.commit();
				status = true;
			}
		} catch (Exception e){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode DELETE EDITEUR : " + e.getLocalizedMessage());
		}

		return status;
	}

	@Override
	public List<Editeur> searchLike(String str) {
		
		Query query = em.createQuery("select e from Editeur e where e.nom like :nom ");
		query.setParameter("nom", "%" +str + "%");
		
		List<Editeur> list =  query.getResultList();
		
		return list;

	}

}
