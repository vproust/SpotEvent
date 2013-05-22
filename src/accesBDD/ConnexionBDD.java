package accesBDD;

import singletons.Utilisateur;
import grandModele.Evenement;
import grandModele.Personne;

public class ConnexionBDD {
	public static String essayerConnexion(String email, String mdp) {
		String idPers = "";
		idPers = "prs1";
		return idPers;
	}
	public static Utilisateur instancierUtilisateur(String idPers){
		Personne personne = new Personne();
		personne.setIdPersonne(idPers);
		personne.setNomPersonne("Proust");
		personne.setPrenomPersonne("Valentin");
		
		Evenement evt1 = new Evenement();
		evt1.setIdEvt("evt1");
		evt1.setNomEvt("Regatte1");
		
		Evenement evt2 = new Evenement();
		evt2.setIdEvt("evt2");
		evt2.setNomEvt("Festival2");
		
		Evenement evt3 = new Evenement();
		evt3.setIdEvt("evt3");
		evt3.setNomEvt("Festival3");
		
		personne.ajouterEvenement(evt1);
		personne.ajouterEvenement(evt2);
		personne.ajouterEvenement(evt3);
		
		Utilisateur userInstance = new Utilisateur(personne);
		return userInstance;
	}
	
	public static Utilisateur instancierUtilisateurAnonyme(){
		Personne personne = new Personne();
		personne.setIdPersonne("prs0");
		
		Utilisateur userInstance = new Utilisateur(personne);
		return userInstance;
	}
}
