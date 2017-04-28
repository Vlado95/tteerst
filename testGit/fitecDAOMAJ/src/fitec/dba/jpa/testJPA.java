package fitec.dba.jpa;

import fitec.dba.dao.IDao;
import fitec.dba.factory.FactoryDao;
import fitec.dba.factory.FactoryDao.typeDao;
import fitec.dba.metier.Editeur;
import fitec.dba.metier.User;


public class testJPA {

	public static void main(String[] args) {
//		EntityManagerFactory factory =Persistence.createEntityManagerFactory("fitecJPA");
//
//		EntityManager em = factory.createEntityManager();
//		
//		if ( em != null )
//			System.out.println(" Manager JPA OK");
//		else
//			System.err.println(" Manager JPA KO");
		
//		IDao<Editeur> edtDao =   (IDao<Editeur>) FactoryDao.getDAO("Editeur", FactoryDao.typeDao.JPA);
//		
//		Editeur editeur = new Editeur(null,"XXXEDITEURXXXX");
//		
//		int id = edtDao.insert(editeur);
//		
//		System.out.println("Editeur ("+id+") bien inséré !");
//		
//		editeur.setId(id);
//		
//		try {
//			edtDao.delete(editeur);
//			System.out.println("Editeur supprimé :"+id);
//		} catch (Exception e) {
//			System.err.println("-->"+e.getLocalizedMessage());
//		}
//		
		// TEST MODIF DAO JPA - CONTRAINTE INTEGRITE REFERENTIELLE
		
//		Auteur auteur = new Auteur(10,"","");
//		
//		IDao<Auteur> autDao =   (IDao<Auteur>) FactoryDao.getDAO("Auteur", FactoryDao.typeDao.JPA);
//		
//			try {
//				autDao.delete(auteur);
//				System.out.println("Auteur supprimé");
//			} catch (Exception e) {
//				System.err.println("-->"+e.getLocalizedMessage());
//			}
		
		
		
//		Query query = em.createQuery("select l from Livre l where l.editeur.id = :id");
//		query.setParameter("id", editeur.getId());
//		
//		List<Livre> l = query.getResultList();
//		
//		if ( l != null && l.size() > 0 )
//				System.err.println("Une référence existe pour cet éditeur");
//		else
//				System.out.println("Suppression autorisée pour cet éditeur");
		
//		Query query = em.createQuery("select a from Auteur a"); 
		
//		List<Auteur> auteurs = query.getResultList() ;
//		// Java 8 Lamda Expression
//		auteurs.forEach(x->{
//			System.out.println(x);
//		});
//	
//		IDao<User> user =  FactoryDao.getDAO("User", FactoryDao.typeDao.JPA);
		
//		DaoUser user = new DaoUser();
		
		
//		User u  = ((DaoUser) user).selectLogin(new User(null, null, null,"dd","mdp"));
		
//		if ( u != null && ! u.getId().equals(0)){
//			System.out.println("Connecté !!"+u.getNom());
//		}else{
//			System.err.println("Connection KO ");
//		}
		
		// Forme minimale Lamda Expression 
//		auteurs.forEach(x->System.out.println(x));
//		
//		for ( Auteur a : auteurs ){
//			System.out.println(a);
//		}
//		
		//		query = em.createQuery("select a from Auteur a where a.id = :id");
		//		query.setParameter("id", 1);
		
		//		Auteur auteur = (Auteur) query.getSingleResult();
		
		//		System.out.println("Auteur sur clef :"+auteur);
		
//				EntityTransaction transac = em.getTransaction();
//		
//				transac.begin();
//
//					Auteur a = new Auteur(null,"Auteur","Inseré 4");
//			
//					a = em.merge(a);
//					
//					em.flush();
//		
//				transac.commit();
//				
//				System.out.println(">>"+a);
		
				
		
		//===========
		//REMOVE AUTEUR
		
		
		
//		EntityTransaction transac = em.getTransaction();
//		transac.begin();
//		
//			Auteur aut = em.find(Auteur.class, 13);
//			em.remove(aut);
//			em.flush();
//			
//		transac.commit();
		
		//UPDATE Objet JPA
//		EntityTransaction transac = em.getTransaction();
//		try {
//			transac.begin();
//			Auteur aut = em.find(Auteur.class, 15);
//			if ( aut != null ){
//				aut.setPrenom("Prochain delete XXXX");
//				em.flush();
//				transac.commit();
//			}else{
//				System.err.println("Objet passé en paramètre n'existe plus");
//			}
//		} catch (Exception e) {
//			System.err.println("Catch sur UPDATE : "+e.getLocalizedMessage()+" ->");
//		}
		
//		IDao<User> daou =  (IDao<User>) FactoryDao.getDAO("User", FactoryDao.typeDao.JPA);
//		IDao<Editeur> e =   (IDao<Editeur>) FactoryDao.getDAO("Editeur", FactoryDao.typeDao.JPA);
//		IDao<Auteur> a2 =   (IDao<Auteur>) FactoryDao.getDAO("Auteur", FactoryDao.typeDao.JPA );   
//		IDao<Livre> daol = (IDao<Livre>) FactoryDao.getDAO("Livre", FactoryDao.typeDao.JPA ); 
//
//		System.out.println("ACCES JPA ->>>");
//		List<Auteur> laut1 = a2.selectAll();
//		laut1.forEach(a->System.out.println(a));
//		System.out.println("JPA FIN->>>");
//		
//		List<Editeur> editeurs = e.selectAll();
//		editeurs.forEach(edt->System.out.println(edt));
		
	
//		List<Livre> livres = daol.selectAll();
//		livres.forEach(l->System.out.println(l));
		
//		Editeur edt = new Editeur(null,"Larousse");
//		int id  = e.insert(edt);
//		System.out.println("Editeur ID:"+id);
//		
//		Livre li = new Livre(null,"Java SE",new Auteur(1,null,null),new Editeur(2,null),212,123.56f);
//		daol.insert(li);
		
//		List<User> ulist = daou.selectAll();
//		ulist.forEach(u->System.out.println(u));
		
		User user = new User(null,null,null,"vendeuse@gmail.com","mdp");
		
		User usr = FactoryDao.authenticate(user, typeDao.JPA);
		
		if ( usr != null ){
			System.out.println("Connected :"+usr.getNom()+ " Bienvenue "+usr.getPrenom());
		}else{
			System.out.println("Connexion ERROR see log file ...");
		}
		
//		ulist = daou.searchLike("EL");
//		ulist.forEach(u->System.out.println(u));
	}
}
