package fitec.dba.factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fitec.dba.dao.IDao;
import fitec.dba.metier.Auteur;
import fitec.dba.metier.Editeur;
import fitec.dba.metier.Livre;
import fitec.dba.metier.User;

@Component
public class HbnFactory {

	    private static SessionFactory sessionFactory ;

		private static Session session ;

		public static Session getSession() {
			if ( session == null ){
				session = sessionFactory.openSession();
			}
			return session;
		}
		
	    public void setSessionFactory(SessionFactory sessionFactory) {
			HbnFactory.sessionFactory = sessionFactory;
		}

	    public static SessionFactory getSessionFactory() {	
	        return sessionFactory;
	    }

	    public enum entityDao  { USER, AUTEUR, EDITEUR, LIVRE  };
	    
	    /**
	     * Check Authentification for a Application User
	     * @param email
	     * @param password
	     * @return : User if LOGIN/PASSWORD Checked
	     */
	    public static User authenticate(String email, String password){
	    	Session session = getSession();    	
	    	String sql = "From User u where u.email = :email and u.password = :password";    	
	    	User user = null;
			try {
				user = (User) session.createQuery(sql).setParameter("email", email)
							.setParameter("password", password).getSingleResult();
			} catch (Exception e) {
				System.err.println("Error LOGIN/PASSWORD ..."+e.getLocalizedMessage());
			}
	    	
	    	return user;
	    }
	    
	    private static IDao<Auteur> daoAuteur;
	    public IDao<Auteur> getDaoAuteur() {
			return daoAuteur;
		}

	    @Autowired
		public void setDaoAuteur(IDao<Auteur> daoAuteur) {
			HbnFactory.daoAuteur = daoAuteur;
		}

	    private static IDao<Editeur> daoEditeur;
	    public IDao<Editeur> getDaoEditeur() {
			return daoEditeur;
		}

	    @Autowired
		public void setDaoEditeur(IDao<Editeur> daoEditeur) {
			HbnFactory.daoEditeur = daoEditeur;
		}

	    private static IDao<Livre> daoLivre;
	    public IDao<Livre> getDaoLivre() {
			return daoLivre;
		}

	    @Autowired
		public void setDaoLivre(IDao<Livre> daoLivre) {
			HbnFactory.daoLivre = daoLivre;
		}

		private static IDao<User> daoUser;
	    
	    public IDao<User> getDaoUser() {
			return daoUser;
		}

	    @Autowired
		public void setDaoUser(IDao<User> daoUser) {
			HbnFactory.daoUser = daoUser;
		}

		public static <T> IDao<?> getDAO(entityDao entity) {
	    	IDao<?> dao = null;    	
	    	switch(entity)
	    	{
		    	case USER:
		    		dao = daoUser;
		    		break;
		    		
		    	case EDITEUR:
		    		dao = daoEditeur;
		    		break;
		    		
		    	case AUTEUR:
		    		dao = daoAuteur;
		    		break;
		    		
		    	case LIVRE:
		    		dao = daoLivre;
		    		break;
	    		default :
	    			System.err.println("Erreur Type d'entité demandée !!!");
	    	} 
	    	
	    	return dao;
	    }
	}
