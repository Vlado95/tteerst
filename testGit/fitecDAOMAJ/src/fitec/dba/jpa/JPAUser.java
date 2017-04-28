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
import fitec.dba.metier.User;

public class JPAUser implements IDao<User> {

	static EntityManager em;
	
	public JPAUser(){
		//persistence-unit
		try {
			em = JpaFactory.getDefaultConnection();
		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger(this.getClass().getName(), "Connexion EntityManager ERROR :"+e.getLocalizedMessage());
		}
	}
	
	
	@Override
	public List<User> selectAll() {
		// "User" ici dans cette requete, reference l'entite User et non la table
		Query query = (Query) em.createQuery("select u from User u");
		List<User> listU = query.getResultList();	
		return listU;
	}

	@Override
	public User selectById(User user) {
		Query query = em.createQuery("select u from User u where u.id = :id");
		query.setParameter("id", user.getId());
		
		user = (User) query.getSingleResult();
		
		if(user == null) {
			return null;
		}
		return user;
	}

	@Override
	public ResultSet selectAllByResultSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(User user) {
		em.getTransaction().begin();
		user = em.merge(user);
		em.flush();
		em.getTransaction().commit();
		return user.getId();
	}

	@Override
	public boolean update(User user) {
		boolean status = false;
		try {
			em.getTransaction().begin();
			
			User u = em.find(User.class, user.getId());
			
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setId(user.getId());
			u.setNom(user.getNom());
			u.setPrenom(user.getPrenom());
			
			em.flush();
			em.getTransaction().commit();
			status = true;
		} catch (Exception e) {
			System.err.println("Methode UPDATE User : " + e.getLocalizedMessage());
		}
		//em.close();
		//System.out.println("Fin UPDATE USER");
		
		return status;

	}

	@Override
	public boolean delete(User user) {
		boolean status = false;
		EntityTransaction transac = em.getTransaction();
		try{
			transac.begin();
			user = em.find(User.class, user.getId());
			em.remove(user);
			em.flush();
			transac.commit();
			status = true;
		} catch (Exception e){
			System.err.println("Methode DELETE USER : " + e.getLocalizedMessage());
		}


		return status;
	}

	@Override
	public List<User> searchLike(String str) {

		Query query = em.createQuery("select u from User u where u.nom like :nom ");
		query.setParameter("nom", "%" +str + "%");
		
		List<User> list =  query.getResultList();
		

		if(list == null) {
			return null;
		}

		return list;
	}
	
	/**
	 * Vérifie la connexion d'un Utilisateur au syst�me 
	 * 
	 * @param user
	 * @return complete User OR null if no login/password corresponding
	 */
	public static User selectLogin(User user) {
		try {
			em = JpaFactory.getDefaultConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Query query = em.createQuery("Select u From User u  where u.email = :email and u.password = :password");
	
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		User u = null ;
		try {
			u = (User) query.getSingleResult() ;
		} catch (Exception e) {
			Logger.getLogger(JPAUser.class.getName()).log(Level.INFO, "User Connexion ERROR for:"+user.getEmail());
		}

		return u;
	}

}
