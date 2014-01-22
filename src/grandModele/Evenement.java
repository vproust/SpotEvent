package grandModele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;

public class Evenement {
	protected int idEvt;
	protected String nomEvt;
	protected Personne organisateur;
	protected long dateDebutEvt;
	protected long dateFinEvt;
	protected String role;
	protected boolean partagePosition;
	protected Set<Personne> participants;
	protected Set<Marqueur> marqueurs;
	protected int confV;
	protected int confP;
	
	//pour l'ecoute du changement de position
	protected LocationManager locationManagerEvt;
	protected LocationListener locationListenerEvt;
	
	public Evenement(){
		participants = new HashSet<Personne>();
		marqueurs = new HashSet<Marqueur>();
	}
	public String getNomEvt(){
		return nomEvt;
	}
	
	public int getIdEvt() {
		return idEvt;
	}
	public void setIdEvt(int i) {
		this.idEvt = i;
	}
	public Personne getOrganisateur() {
		return organisateur;
	}
	public void setOrganisateur(Personne organisateur) {
		this.organisateur = organisateur;
	}
	public Set<Personne> getParticipants() {
		return participants;
	}
	public void setParticipants(Set<Personne> participants) {
		this.participants = participants;
	}
	public void ajouterParticipant(Personne participant) {
		this.participants.add(participant);
	}
	public void setNomEvt(String nomEvt) {
		this.nomEvt = nomEvt;
	}
	public void ajouterMarqueur(Marqueur mrq) {
		this.marqueurs.add(mrq);
	}
	public Set<Marqueur> getMarqueurs() {
		return this.marqueurs;
	}
	public void setDateFinEvt(long dateFinEvt) {
		this.dateFinEvt = dateFinEvt;
	}
	public void setDateDebutEvt(long dateDebutEvt) {
		this.dateDebutEvt = dateDebutEvt;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setPartagePosition(boolean partagePosition) {
		this.partagePosition = partagePosition;
	}
	public long getDateDebutEvt() {
		return dateDebutEvt;
	}
	public long getDateFinEvt() {
		return dateFinEvt;
	}
	public String getRole() {
		return role;
	}
	public boolean isPartagePosition() {
		return partagePosition;
	}
	public LocationManager getLocationManagerEvt() {
		return locationManagerEvt;
	}
	public void setLocationManagerEvt(LocationManager locationManagerEvt) {
		this.locationManagerEvt = locationManagerEvt;
	}
	public LocationListener getLocationListenerEvt() {
		return locationListenerEvt;
	}
	public void setLocationListenerEvt(LocationListener locationListenerEvt) {
		this.locationListenerEvt = locationListenerEvt;
	}
	public void setConfV(int confV) {
		this.confV = confV;
		
	}
	public int getConfP() {
		return confP;
	}
	public void setConfP(int confP) {
		this.confP = confP;
	}
	public int getConfV() {
		return confV;
	}
	
}
