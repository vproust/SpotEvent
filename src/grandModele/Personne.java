package grandModele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moduleCarte.ActiviteCarte;

import android.content.Context;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.OverlayItem;


import spotevent.fabien.un.R;

import singletons.EvenementCourant;

public class Personne{
	protected Set<Evenement> evenements;
	protected ArrayList<Marqueur> marqueurs;
	protected String idPersonne;
	protected String nomPersonne;
	protected String prenomPersonne;

	protected int numPersonne;
	protected int dernierMarqueurIconePersonne;

	public ArrayList<Marqueur> getMarqueurs() {
		return marqueurs;
	}

	public ArrayList<GeoPoint> getGeoPoints() {
		ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		Iterator<Marqueur> iterator = marqueurs.iterator();
		while (iterator.hasNext()){
			Marqueur mrq = (Marqueur) iterator.next();
			geoPoints.add(mrq.geo);
		}
		return geoPoints;
	}



	public int getNumPersonne() {
		return numPersonne;
	}

	public void setNumPersonne(int numPersonne,Context context) {
		this.numPersonne = numPersonne;
		String marqueurIconeChaine = "drawable/marqueur"+numPersonne;
		int marqueurIcone = context.getResources().getIdentifier(marqueurIconeChaine, "drawable", context.getPackageName());
		this.setDernierMarqueurIconePersonne(marqueurIcone);	
	}
	public void setDernierMarqueurIconePersonne(int dernierMarqueurIconePersonne) {
		this.dernierMarqueurIconePersonne = dernierMarqueurIconePersonne;
	}

	public int getDernierMarqueurIconePersonne() {
		return dernierMarqueurIconePersonne;
	}

	public Set<Evenement> getEvenements() {
		return evenements;
	}

	public Personne(){
		evenements = new HashSet<Evenement>();
		marqueurs = new ArrayList<Marqueur>();
	}
	public String getNomPersonne() {
		return nomPersonne;
	}
	public void setNomPersonne(String nomPersonne) {
		this.nomPersonne = nomPersonne;
	}
	public String getPrenomPersonne() {
		return prenomPersonne;
	}
	public void setPrenomPersonne(String prenomPersonne) {
		this.prenomPersonne = prenomPersonne;
	}
	public String getIdPersonne() {
		return idPersonne;
	}
	public void setIdPersonne(String idPersonne) {
		this.idPersonne = idPersonne;
	}
	public void ajouterEvenement(Evenement evt){
		this.evenements.add(evt);
		evt.participants.add(this);
	}
	public void ajouterMarqueur(Marqueur mrq){
		this.marqueurs.add(mrq);
		mrq.participant = this;
		mrq.evenement = EvenementCourant.getEvenementCourant();
	}

	public ArrayList<Evenement> getArrayListeEvenements(){
		Iterator<Evenement> iterator = evenements.iterator();
		ArrayList<Evenement> listeEvenements = new ArrayList<Evenement>();
		while (iterator.hasNext()){
			Evenement evt = (Evenement) iterator.next();
			listeEvenements.add(evt);
		}
		return listeEvenements;
	}

	public String[] arrayListeEvenementToString(ArrayList<Evenement> arrayListeEvenements){
		Iterator<Evenement> iterator = arrayListeEvenements.iterator();
		String[] listeEvenements = {};
		listeEvenements = new String[arrayListeEvenements.size()];
		int i = 0;
		while (iterator.hasNext()){
			Evenement evt = (Evenement) iterator.next();
			listeEvenements[i] = evt.getNomEvt();
			i++;
		}
		return listeEvenements;
	}

}
