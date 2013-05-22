package grandModele;

import singletons.Utilisateur;
import com.mapquest.android.maps.GeoPoint;

public class Marqueur {
	protected String idMqr;
	protected GeoPoint geo;
	protected boolean estDernier;
	protected Evenement evenement;
	protected Personne participant;
	
	public boolean EstDernier() {
		return estDernier;
	}

	public void setEstDernier(boolean estDernier) {
		this.estDernier = estDernier;
	}
	
	public GeoPoint getGeo() {
		return geo;
	}
	protected long date;
	
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
	
	public void ajouterMarqueur(Personne participant){
		this.participant = participant;
		this.participant.getMarqueurs().add(this);
	}

}