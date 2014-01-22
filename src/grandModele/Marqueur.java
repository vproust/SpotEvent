package grandModele;

import singletons.Utilisateur;
import com.mapquest.android.maps.GeoPoint;

public class Marqueur {
	protected String idMqr;
	protected GeoPoint geo;
	protected long date;
	protected Evenement evenement;
	protected Personne participant;
	
	public GeoPoint getGeo() {
		return geo;
	}
	
	public long getDate() {
		return date;
	}
	
	public Personne getParticipant() {
		return participant;
	}

	public Marqueur(GeoPoint geoPoint) {
		idMqr = "mqr";
		geo = geoPoint;
		
		//TTT tests de dates
		int lower = 1000;
		int higher = 5000000;
		int random = (int)(Math.random() * (higher-lower)) + lower;
		date = System.currentTimeMillis()-random;
		//date = System.currentTimeMillis();
	}
	//TTT
	public String getInfosMarqueur(){
		String infosMarqueur = new String();
		infosMarqueur = geo.toString();
		return infosMarqueur;
	}
	

	public Evenement getEvenement() {
		return evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	public String getIdMqr() {
		return idMqr;
	}

	public void setIdMqr(String idMqr) {
		this.idMqr = idMqr;
	}

	public void setGeo(GeoPoint geo) {
		this.geo = geo;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setParticipant(Personne participant) {
		this.participant = participant;
		this.participant.getMarqueurs().add(this);
	}

}