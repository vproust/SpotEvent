package singletons;

import java.util.ArrayList;
import java.util.Iterator;

import com.mapquest.android.maps.GeoPoint;


import grandModele.Marqueur;
import grandModele.Personne;

public class Utilisateur extends Personne{
	
	private static Personne _utilisateur;
	private static ArrayList<Marqueur> marqueursAEnvoyer;
	
	public Utilisateur(Personne pers){
		Personne utili = getInstance();
		utili = pers;
		_utilisateur = utili;
		marqueursAEnvoyer = new ArrayList<Marqueur>();
	}
	
	private static Personne getInstance() {
		if(_utilisateur==null) {
			_utilisateur = new Personne();
			marqueursAEnvoyer = new ArrayList<Marqueur>();
		}
		return _utilisateur;
	}
	
	public static Personne getUtilisateur(){
		return _utilisateur;
	}

	public static ArrayList<Marqueur> getMarqueursAEnvoyer() {
		return marqueursAEnvoyer;
	}
	
	public static Marqueur getDernierMarqueurAEnvoyer() {
		int nombreDeMarqueursAEnvoyer = marqueursAEnvoyer.size();
		return marqueursAEnvoyer.get(nombreDeMarqueursAEnvoyer-1);
	}
	

	public static void ajouterMarqueursAEnvoyer(Marqueur marqueurAEnvoyer) {
		Utilisateur.marqueursAEnvoyer.add(marqueurAEnvoyer);
	}
	
	public static void videMarqueursAEnvoyer(){
		Utilisateur.marqueursAEnvoyer.clear();
	}

	//TTT
	public static String getMarqueursAEnvoyerSystemOut(){
		Iterator<Marqueur> iterator = marqueursAEnvoyer.iterator();
		String chaineMarqueursAEnvoyer = new String();
		while (iterator.hasNext()){
			Marqueur evt = (Marqueur) iterator.next();
			chaineMarqueursAEnvoyer = chaineMarqueursAEnvoyer + " - " + evt.getInfosMarqueur();
		}
		return chaineMarqueursAEnvoyer;
	}
	
}