package singletons;

import java.util.ArrayList;
import java.util.Iterator;


import grandModele.Marqueur;
import grandModele.Personne;

public class Utilisateur extends Personne{
	private static ArrayList<Marqueur> marqueursAEnvoyer;
	public static ArrayList<Marqueur> getMarqueursAEnvoyer() {
		return marqueursAEnvoyer;
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
	public static void ajouterMarqueursAEnvoyer(Marqueur marqueurAEnvoyer) {
		Utilisateur.marqueursAEnvoyer.add(marqueurAEnvoyer);
	}
	private static Personne _utilisateur;
	
	private static Personne getInstance() {
        if(_utilisateur==null) {
        	_utilisateur = new Personne();
        	marqueursAEnvoyer = new ArrayList<Marqueur>();
        }
        return _utilisateur;
    }
	public Utilisateur(Personne pers){
		Personne utili = getInstance();
		utili = pers;
		_utilisateur = utili;
		marqueursAEnvoyer = new ArrayList<Marqueur>();
	}
	public static Personne getUtilisateur(){
		return _utilisateur;
	}
}