package grandModele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moduleCarte.ActiviteCarte;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.OverlayItem;



import singletons.EvenementCourant;

public class Personne{
	protected Set<Evenement> evenements;
	protected ArrayList<Marqueur> marqueurs;
	protected int idPersonne;
	protected String nomPersonne;
	protected String prenomPersonne;
	protected long dateMiseAJour;
	protected int numPersonne;
	protected Drawable dernierMarqueurIconePersonne;
	
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
		Drawable icon = context.getResources().getDrawable(marqueurIcone);
		this.setDernierMarqueurIconePersonne(icon);	
	}
	public void setDernierMarqueurIconePersonne(Drawable icon) {
		this.dernierMarqueurIconePersonne = icon;
	}

	public Drawable getDernierMarqueurIconePersonne() {
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
	public int getIdPersonne() {
		return idPersonne;
	}
	public void setIdPersonne(int i) {
		this.idPersonne = i;
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

	public long getDateMiseAJour() {
		return dateMiseAJour;
	}

	public void setDateMiseAJour(long dateMiseAJour) {
		this.dateMiseAJour = dateMiseAJour;
	}

}
