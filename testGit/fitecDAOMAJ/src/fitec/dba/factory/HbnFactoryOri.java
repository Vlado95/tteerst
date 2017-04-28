package fitec.dba.factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fitec.dba.dao.IDao;
import fitec.dba.metier.Auteur;
import fitec.dba.metier.User;

@Component
public class HbnFactoryOri {

		static IDao<Auteur> daoAuteur ;
			
		static IDao<User> daoUser ;
		
		
		public static IDao<User> getDaoUser() {
			return daoUser;
		}

		@Autowired
		public static void setDaoUser(IDao<User> daoUser) {
			HbnFactoryOri.daoUser = daoUser;
		}

		public enum entityDao  { AUTEUR, USER };
		
	    private static SessionFactory sessionFactory ; 
		private static Session session ;

		public static Session getSession() {
			if ( session == null ){
				session = sessionFactory.openSession();
			}
			return session;
		}
		
	    private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	            return new Configuration().configure().buildSessionFactory();
	        }
	        catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    
	    /**
	     * Methode utilisée par Spring pour faire l'injection de la sessionFactory
	     * @param sessionFactory
	     */
	    public static void setSessionFactory(SessionFactory sessionFactory) {
	    	HbnFactoryOri.sessionFactory = sessionFactory;
		}

		public static SessionFactory getSessionFactory() {
	    	if ( sessionFactory != null){
	    		sessionFactory = buildSessionFactory();    
	    	}else{
	    		System.out.println("La session est déjà instanciée !");
	    	}
	        return sessionFactory;
	    }

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
	    
	    public static IDao<?> getDAO(entityDao entity) {
	    	if ( entity == entityDao.AUTEUR){
	    		System.out.println("Return daoAuteur ...");
	    		return daoAuteur  ;
	    	}else if ( entity == entityDao.USER ){
	    		System.out.println("Return daoUser ...");
	    		return daoUser  ;
	    	}else{
	    		System.err.println("ERROR TYPE ENTITY "+entity);
	    		return null;
	    	}
	    }
	}
