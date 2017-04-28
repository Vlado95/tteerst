package fitec.dba.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import fitec.dba.dao.IDao;
import fitec.dba.factory.JpaFactory;
import fitec.dba.metier.Livre;


public class JPALivre implements IDao<Livre> {

	/**
	 * em Represente une référence à Entity Manager qui est géré par la Factory 
	 * JpaFactory
	 */
	EntityManager em ;

	
	/**
	 * On récupere une référence sur EntityManager qui sera valable dans toutes les
	 * méthodes
	 */
	public JPALivre() {
		try {
			em = JpaFactory.getDefaultConnection();
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public List<Livre> selectAll() {
		em.getTransaction().begin();
		Query req = em.createQuery("select l from Livre l");
		em.getTransaction().commit();
		return req.getResultList();
	}

	@Override
	public Livre selectById(Livre livre) {
		

		em.getTransaction().begin();
        livre = em.find(Livre.class, livre.getId());
        em.getTransaction().commit();
		
		
		return livre;

	}

	
	@Override
	public int insert(Livre livre) {

		em.getTransaction().begin();
		em.persist(livre);
		em.getTransaction().commit();

		return livre.getId();
	}

	
	
	@Override
	public boolean update(Livre livre) {
		em.getTransaction().begin();
		Livre l = em.merge(livre);
		em.getTransaction().commit();
		return l.getId() != null ? true : false;//
	}

	
	
	@Override
	public boolean delete(Livre livre) {
		em.getTransaction().begin();
		Livre ll = em.find(Livre.class, livre.getId());
		em.remove(ll);
		em.getTransaction().commit();
		return selectAllByResultSet() != null ? false : true;
	}


	
	
	@Override
	public ResultSet selectAllByResultSet() {
		return null;
	}

	
	
	@Override
	public List<Livre> searchLike(String str) {

		em.getTransaction().begin();
		Query req = em.createQuery("select l from Livre l where l.titre like '%" + str + "%'");
		em.getTransaction().commit();
		return req.getResultList();
	}

}
