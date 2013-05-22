package accesBDD;

import grandModele.Marqueur;
import grandModele.Personne;
import singletons.EvenementCourant;
import singletons.Utilisateur;
import spotevent.fabien.un.R;

import com.mapquest.android.maps.GeoPoint;


public class EvenementBDD {
	//m師hode qui r残up俊e dans la bdd tous les marqueurs de l'思始ement, les instancie un par un et les ajoute a l'思始ement
	public static void setMarqueurs(){
		//TTT
		Personne prs3 = new Personne();
	    prs3.setIdPersonne("prs3");
	    prs3.setNomPersonne("Richard");
	    prs3.setPrenomPersonne("Fabien");
	    prs3.setDernierMarqueurIconePersonne(R.drawable.marqueur3);
	    prs3.setPasDernierMarqueurIconePersonne(R.drawable.dot3);
	    prs3.ajouterEvenement(EvenementCourant.getEvenementCourant());
	    prs3.setNumPersonne(3);
	    
	    Personne prs2 = new Personne();
	    prs2.setIdPersonne("prs2");
	    prs2.setNomPersonne("Richard");
	    prs2.setPrenomPersonne("Lucie");
	    prs2.setDernierMarqueurIconePersonne(R.drawable.marqueur2);
	    prs2.setPasDernierMarqueurIconePersonne(R.drawable.dot2);
	    prs2.ajouterEvenement(EvenementCourant.getEvenementCourant());
	    prs2.setNumPersonne(2);
		
		Marqueur mrq1 = new Marqueur(new GeoPoint(48.110913,-1.635268));mrq1.setEstDernier(false);mrq1.ajouterMarqueur(prs3);
	    Marqueur mrq2 = new Marqueur(new GeoPoint(48.112245,-1.636963));mrq2.setEstDernier(false);mrq2.ajouterMarqueur(prs3);
	    Marqueur mrq3 = new Marqueur(new GeoPoint(48.112073,-1.643014));mrq3.setEstDernier(true);mrq3.ajouterMarqueur(prs3);
	    Marqueur mrq4 = new Marqueur(new GeoPoint(48.111529,-1.645052));mrq4.setEstDernier(false);mrq4.ajouterMarqueur(prs2);
	    Marqueur mrq5 = new Marqueur(new GeoPoint(48.109638,-1.651318));mrq5.setEstDernier(false);mrq5.ajouterMarqueur(prs2);
	    Marqueur mrq6 = new Marqueur(new GeoPoint(48.109566,-1.652091));mrq6.setEstDernier(true);mrq6.ajouterMarqueur(prs2);
	    
	    EvenementCourant.getEvenementCourant().ajouterParticipant(prs2);
	    EvenementCourant.getEvenementCourant().ajouterParticipant(prs3);
	    EvenementCourant.getEvenementCourant().ajouterMarqueur(mrq1);
	    EvenementCourant.getEvenementCourant().ajouterMarqueur(mrq2);
	    EvenementCourant.getEvenementCourant().ajouterMarqueur(mrq3);
	    EvenementCourant.getEvenementCourant().ajouterMarqueur(mrq4);
	    EvenementCourant.getEvenementCourant().ajouterMarqueur(mrq5);
	    EvenementCourant.getEvenementCourant().ajouterMarqueur(mrq6);
	}
}
