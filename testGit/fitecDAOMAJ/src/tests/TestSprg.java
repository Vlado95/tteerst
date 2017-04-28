package tests;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.core.net.SyslogOutputStream;
import fitec.dba.dao.IDao;
import fitec.dba.factory.HbnFactory;
import fitec.dba.hbn.HbnDaoAuteur;
import fitec.dba.hbn.HbnDaoUser;
import fitec.dba.metier.Auteur;
import fitec.dba.metier.Editeur;
import fitec.dba.metier.Livre;
import fitec.dba.metier.User;
import junit.framework.TestCase;

public class TestSprg extends TestCase {

	private ApplicationContext context ;
	private Integer count = 0;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("spring-config.xml");	
		count++;
	}
	
//	@Test
//	public void test1() {
//		
//		System.out.println("Test Authenticate No:"+count);
//		
//		HbnFactory hbn = (HbnFactory) context.getBean("HbnFactory");
//		User user = hbn.authenticate("dd", "mdp");
//		
//		if ( user != null && ! user.getId().equals(0)){
//			System.out.println("User "+user+" CONNECTED !!");
//		}else{
//			System.err.println("ERROR CONNECTION : "+user);
//		}
//		
//		IDao<User> usr = (IDao<User>) hbn.getDAO(HbnFactory.entityDao.USER);
//		
//		assertNotNull("ERROR Accés User DAO ",usr);
//		
//		if ( usr == null )
//			System.err.println("Error RETURN UserDao :");
//		
//		List<User> usrs = usr.selectAll();
//		
//		usrs.forEach(u->System.out.println(u) );
//				
//	}
//	
//	@Test
//	public void test2() {
//		IDao<Auteur> auteur = (IDao<Auteur>) HbnFactory.getDAO(HbnFactory.entityDao.AUTEUR);
//		
//		List<Auteur> auteurs = auteur.selectAll();
//		
//		auteurs.forEach(a->System.out.println(a));
//	}
//	
//	@Test
//	public void testAuteur2() {
//		
//		System.out.println("------> START Of Auteur TESTS "+count);
//		
//		assertNotNull("Spring Context Loading ERROR ", context);
//		
//		HbnFactory factory =  (HbnFactory) context.getBean("HbnFactory");
//		
//		assertNotNull("Factory Bean ERROR  ", factory);
//		
//		System.out.println("----->Factory HBN is OK ");
//		
//		IDao<Auteur> daoAuteur = (IDao<Auteur>) factory.getDAO(HbnFactory.entityDao.AUTEUR);
//		
//		assertNotNull("Dao Auteur ERROR  ", daoAuteur);
//		
//		List<Auteur> auteurs = daoAuteur.selectAll();
//		
//		auteurs.forEach(a->{
//					System.out.println(a);
//		});
//		
//		Auteur auteur = new Auteur(null,"XXXPRENOMXXX","XXXNOMXXX");
//		
//		Integer id = daoAuteur.insert(auteur);
//		
//		assertTrue("Insert ERROR for Auteur no Id returned",id!=0);
//		
//		auteur.setId(id);
//		auteur.setPrenom("Prénom TEST");
//		
//		if ( daoAuteur.update(auteur))
//			System.out.println("Auteur UPDATE OK :"+auteur.getId());
//		else
//			System.err.println("Auteur UPDATE KO :"+auteur.getId());
//		
//		auteur = daoAuteur.selectById(auteur) ;
//		
//		if ( auteur != null && ! auteur.getId().equals(0) ){
//			System.out.println("Auteur select By ID OK :"+auteur);
//		}else{
//			System.err.println("Auteur select By ID KO :"+auteur);
//		}
//		
//		// First Delete Auteur TEST occurence
//		try {
//			daoAuteur.delete(auteur);
//			System.out.println("FIRST DELETE Auteur OK :"+auteur);
//		} catch (Exception e) {
//			System.err.println("Auteur DELETE TEST KO :"+e.getLocalizedMessage());
//		}
//		
//		// Second Delete Auteur TEST occurence twice for generate an Exception Error
//		try {
//			daoAuteur.delete(auteur);
//		} catch (Exception e) {
//			System.err.println("Auteur DOUBLE DELETE TEST Must return an Exception :"+e.getLocalizedMessage());
//		}
//		
//		System.out.println("------> END Of Auteur TESTS");
//
//	}	
//	
//	@Test
//	public void testUser3() {
//		
//		System.out.println("Test User No:"+count);
//
//		assertNotNull("Spring Context Loading ERROR ", context);
//
//		HbnDaoUser hbnUser = (HbnDaoUser) context.getBean("HbnDaoUser");
//		
//		List<User> users = hbnUser.selectAll();
//		
//		users.forEach(u->System.out.println(u) );	
//	}
//	
//	@Test
//	public void test3() {
//		
//		System.out.println("Test No:"+count);
//
//		assertNotNull("Spring Context Loading ERROR ", context);
//		
//		HbnFactory factory =  (HbnFactory) context.getBean("HbnFactory");
//		
//		assertNotNull("Factory Bean ERROR  ", factory);
//		
//		System.out.println("----->Factory HBN is OK ");
//		
//		IDao<Editeur> daoEditeur = (IDao<Editeur>) factory.getDAO(HbnFactory.entityDao.EDITEUR);
//		
//		List<Editeur> editeurs = daoEditeur.selectAll();
//		
//		editeurs.forEach(e->System.out.println(e));		
//	}	
//	
//	@Test
//	public void testEditeur() {
//		
//		System.out.println("------> START Of Auteur TESTS "+count);
//		
//		assertNotNull("Spring Context Loading ERROR ", context);
//		
//		HbnFactory factory =  (HbnFactory) context.getBean("HbnFactory");
//		
//		assertNotNull("Factory Bean ERROR  ", factory);
//		
//		System.out.println("----->Factory HBN is OK ");
//		
//		IDao<Editeur> daoEditeur = (IDao<Editeur>) factory.getDAO(HbnFactory.entityDao.EDITEUR);
//		
//		assertNotNull("Dao Auteur ERROR  ", daoEditeur);
//		
//		List<Editeur> editeurs = daoEditeur.selectAll();
//		
//		editeurs.forEach(e->{
//					System.out.println(e);
//		});
//		
//		Editeur editeur = new Editeur(null,"XXXNOMXXX");
//		
//		Integer id = daoEditeur.insert(editeur);
//		
//		assertTrue("Insert ERROR for Editeur no Id returned",id!=0);
//		
//		editeur.setId(id);
//		editeur.setNom("Nom Editeur TESTXXXX");
//		
//		if ( daoEditeur.update(editeur))
//			System.out.println("Editeur UPDATE OK :"+editeur.getId());
//		else
//			System.err.println("Editeur UPDATE KO :"+editeur.getId());
//		
//		editeur = daoEditeur.selectById(editeur) ;
//		
//		if ( editeur != null && ! editeur.getId().equals(0) ){
//			System.out.println("Editeur select By ID OK :"+editeur);
//		}else{
//			System.err.println("Editeur select By ID KO :"+editeur);
//		}
//		
//		// First Delete Auteur TEST occurence
//		try {
//			daoEditeur.delete(editeur);
//			System.out.println("FIRST DELETE editeur OK :"+editeur);
//		} catch (Exception e) {
//			System.err.println("FIRST Editeur DELETE TEST KO :"+e.getLocalizedMessage());
//		}
//		
//		// Second Delete Auteur TEST occurence twice for generate an Exception Error
//		try {
//			daoEditeur.delete(editeur);
//		} catch (Exception e) {
//			System.err.println("Editeur DOUBLE DELETE TEST Must return an Exception :"+e.getLocalizedMessage());
//		}
//		
//		System.out.println("------> END Of Editeur TESTS");
//	}
	
	@Test
	public void testLivre() {
		
		System.out.println("------> START Of Livre TESTS "+count);
		
		assertNotNull("Spring Context Loading ERROR ", context);
		
		HbnFactory factory =  (HbnFactory) context.getBean("HbnFactory");
		
		assertNotNull("Factory Bean ERROR  ", factory);
		
		System.out.println("----->Factory HBN is OK ");
		
		IDao<Livre> daoLivre = (IDao<Livre>) factory.getDAO(HbnFactory.entityDao.LIVRE);
		
		assertNotNull("Dao Livre ERROR  ", daoLivre);
		
		List<Livre> livres = daoLivre.selectAll();
		
		livres.forEach(l->{
					System.out.println(l);
		});
		
//		Editeur editeur = new Editeur();
//		editeur.setId(22);
//		Auteur auteur = new Auteur(1,"","");
//		
//		Livre livre = new Livre(null,"XXX Livre TEST XXXX",auteur,editeur,300,17.90f);
//		
//		Integer id = daoLivre.insert(livre);
//		
//		assertTrue("Insert ERROR for Livre no Id returned",id!=0);
		
		
		Livre livr = new Livre();
		livr.setId(3);
		Livre livre =daoLivre.selectById(livr) ;
		livre.setTitre("XXX UPDATE Livre TEST");
		
		if ( daoLivre.update(livre))
			System.out.println("Livre UPDATE OK :"+livre);
		else
			System.err.println("Livre UPDATE KO :"+livre.getId());
		
		
		Livre l = new Livre();
		l.setId(livre.getId());
		livre = daoLivre.selectById(l) ;
		
		if ( livre != null && ! livre.getId().equals(0) ){
			System.out.println("Livre select By ID OK :"+livre);
		}else{
			System.err.println("Livre select By ID KO :"+livre);
		}
		
		// First Delete Auteur TEST occurence
		/*try {
			daoLivre.delete(livre);
			System.out.println("FIRST DELETE Livre OK :"+livre);
		} catch (Exception e) {
			System.err.println("FIRST Livre DELETE TEST KO :"+e.getLocalizedMessage());
		}*/
		
		// Second Delete Auteur TEST occurence twice for generate an Exception Error
	/*	try {
			daoLivre.delete(livre);
		} catch (Exception e) {
			System.err.println("Livre DOUBLE DELETE TEST Must return an Exception :"+e.getLocalizedMessage());
		}
		*/
		System.out.println("------> END Of Livre TESTS");
	}
		
}
