package moduleOrganiser;

import grandModele.Evenement;
import grandModele.Marqueur;
import grandModele.Personne;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

import accesBDD.AccesBDDAjouterMarqueurs;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.mapquest.android.maps.GeoPoint;

import outils.Diff;
import outils.TesteurConnexionInternet;

import singletons.EvenementCourant;
import singletons.Utilisateur;
import spotevent.fabien.un.R;

public class ModeleOrganiser extends Observable {

	private Context context;
	protected int resultatEnvoiMarqueurs;

	//dateActuelle est la date du serveur incrementee par le systeme android toutes les minutes
	private long dateActuelle;
	private ArrayList<Evenement> arrayEvenementsAVenir = new ArrayList<Evenement>();
	private ArrayList<Evenement> arrayEvenementsEnCours = new ArrayList<Evenement>();
	private ArrayList<Evenement> arrayEvenementsPasses = new ArrayList<Evenement>();

	protected LocationManager locationManagerAutomatic;
	protected LocationListener locationListenerAutomatic;

	public ModeleOrganiser(Context context){
		this.context = context;
		this.dateActuelle = 0;
	}

	public void modifierRafraichissementPosition(long frequenceDeRafraichissement){
		//on se sert de EvenementCourant pour le changement de la frequence de rafraichissement

	}

	public long getDateActuelle() {
		return dateActuelle;
	}

	public void setDateActuelle(long dateActuelle) {
		this.dateActuelle = dateActuelle;
		miseAJourArrayListeEvenements();
	}

	public void plusUneMinute(){
		//si la date actuelle a ete initialis仔
		if(this.dateActuelle!=0){
			this.dateActuelle = this.dateActuelle+60;
			miseAJourArrayListeEvenements();
		}
	}

	public void miseAJourArrayListeEvenements(){
		//on commence par vider les 3 ArrayListe
		arrayEvenementsAVenir.clear();
		arrayEvenementsEnCours.clear();
		arrayEvenementsPasses.clear();
		Set<Evenement> evenements = Utilisateur.getUtilisateur().getEvenements();
		Iterator<Evenement> iterator = evenements.iterator();
		while (iterator.hasNext()){
			Evenement evt = (Evenement) iterator.next();
			//si l'utilisateur est l'organisateur de l'evenement
			String role = evt.getRole();
			if(role.equals("O")){
				//si l'思始ement finit apres la date actuelle
				if(evt.getDateFinEvt()>this.dateActuelle){
					//si en plus, il commence apres la date actuelle, c'est un evenement A VENIR
					if(evt.getDateDebutEvt()>this.dateActuelle){
						arrayEvenementsAVenir.add(evt);
					}
					//sinon c'est un 思始ement EN COURS 
					else{
						arrayEvenementsEnCours.add(evt);
					}
				}
				//sinon c'est un 思始ement PASSE
				else{
					arrayEvenementsPasses.add(evt);
				}
			}
		}
		this.setChanged();
		this.notifyObservers();
	}

	public String[] arrayListeEvenementToString(ArrayList<Evenement> arrayListeEvenements,String CVP){
		String CVPAvecDate = "";		
		Iterator<Evenement> iterator = arrayListeEvenements.iterator();
		String[] stringEvenements = {};
		stringEvenements = new String[arrayListeEvenements.size()];
		int i = 0;
		while (iterator.hasNext()){
			Evenement evt = (Evenement) iterator.next();
			if(CVP=="C"){
				CVPAvecDate = "Se termine dans "+Diff.friendlyTimeDiff(dateActuelle, evt.getDateFinEvt());
			}
			else if(CVP=="V"){
				CVPAvecDate = "Commence dans "+Diff.friendlyTimeDiff(dateActuelle, evt.getDateDebutEvt());
			}
			else if(CVP=="P"){
				DateFormat df = DateFormat.getDateInstance();
				String dateFormatee = df.format(evt.getDateFinEvt()*1000);
				CVPAvecDate = dateFormatee;
			}
			stringEvenements[i] = evt.getNomEvt()+" - "+CVPAvecDate;
			System.out.println(evt.getDateFinEvt()-dateActuelle);
			i++;
		}
		return stringEvenements;
	}

	public ArrayList<Evenement> getArrayEvenementsEnCours() {
		return arrayEvenementsEnCours;
	}

	public ArrayList<Evenement> getArrayEvenementsAVenir() {
		return arrayEvenementsAVenir;
	}

	public ArrayList<Evenement> getArrayEvenementsPasses() {
		return arrayEvenementsPasses;
	}
}
