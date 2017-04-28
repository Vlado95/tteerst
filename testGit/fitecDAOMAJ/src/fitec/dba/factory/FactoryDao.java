/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitec.dba.factory;


import fitec.dba.dao.DaoUser;
import fitec.dba.dao.IDao;
import fitec.dba.jpa.JPAUser;
import fitec.dba.metier.Metier;
import fitec.dba.metier.User;


/**
 *
 * @author Formateur 
 * @version 2.0
 * 
 * Le r�le de la factory est d'instancier un ou plusieurs objet d'un m�me type
 * Et de le renvoyer au demandeur
 */
public class FactoryDao {
	/* Publique Enum�ration qui sert de param�tre � l'appel de la Factory*/
	public enum typeDao  { JDBC, JPA };

	/*
	 * method factory 
	 * valeur de retour = type IDao => interface IDao qui est impl�ment�e par toutes les DAO   
	 *  
	 */
	public static IDao<?> getDAO(Metier objet) {

		//        r�cup�ration du nom de la classe metier de l'objet re�u en param�tre via getObject.getSimpleName
		//        + concat�nation avec le package dao et le pr�fixe DAO au nom de la classe r�cuper�e
		String className = "fitec.dbo.dao.Dao" + objet.getClass().getSimpleName();

		Class<?> cl;
		Object o = null;

		try {
			//            r�cup de .Class
			cl = Class.forName(className);
			//            instance de l'objet DAo...
			o = cl.newInstance();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();

		}
		return (IDao<?>) o;
	}

	public static IDao<?> getDAO(String classe) {

		String className = "fitec.dba.dao.Dao" + classe;

		Class<?> cl = null ;
		Object o = null;

		try {
			cl = Class.forName(className);
			o = cl.newInstance();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();

		}
		return (IDao<?>) o;
	}
	
	/**
	 * Methode Factory : Renvoie : Un Type de DAO en fonction de l'entité choisie : Livre, Auteur, Editeur
	 * 
	 * @param classe : String nom de l'entité : Editeur, Livre, Auteur
	 * @param type   : Type de DAO (JDBC, JPA)
	 * @return : Un Objet DAO contenant tous les accés concernée
	 */
	public static <T extends IDao<?>> T getDAO(String classe,FactoryDao.typeDao type) {

		String className = null;
		
		if ( type == typeDao.JDBC){
			className = "fitec.dba.dao.Dao" + classe;
		}else{
			className = "fitec.dba.jpa.JPA" + classe;
		}

		System.out.println("Classe a instancier : "+className);
		
		Class<?> cl = null ;
		Object o = null;

		try {
			cl = Class.forName(className);
			o = cl.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();

		}
		return (T) (o);
	}
	
	/**
	 * M�thode statique de Factory effectuant une authentification applicative
	 * pour un User donn� (check email/password)
	 * Et qui passe soit par la couche JDBC ou par la couche JPA
	 * @param user : 
	 * @param typeDao
	 * @return
	 */
	public static User authenticate(User user, typeDao typeDao ){
		User u = null ;
		if ( typeDao == typeDao.JPA ){
			u=JPAUser.selectLogin(user);
		}else{
			u=DaoUser.selectLogin(user);
		}
		return u;	
	}
}
