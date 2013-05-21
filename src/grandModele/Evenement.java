package grandModele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import android.content.Context;

import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;

public class Evenement {
	protected String idEvt;
	protected String nomEvt;
	protected Personne organisateur;
	protected Set<Personne> participants;
	protected Set<Marqueur> marqueurs;
	
	public Evenement(){
		idEvt = "evt12";
		nomEvt = "Regatte";
		participants = new HashSet<Personne>();
		marqueurs = new HashSet<Marqueur>();
	}
	public String getNomEvt(){
		return nomEvt;
	}
	
	public String getIdEvt() {
		return idEvt;
	}
	public void setIdEvt(String idEvt) {
		this.idEvt = idEvt;
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
	
}
