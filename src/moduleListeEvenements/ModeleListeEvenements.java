package moduleListeEvenements;

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

public class ModeleListeEvenements extends Observable {

	private Context context;
	protected int resultatEnvoiMarqueurs;

	//dateActuelle est la date du serveur incrementee par le systeme android toutes les minutes
	private long dateActuelle;
	private ArrayList<Evenement> arrayEvenementsAVenir = new ArrayList<Evenement>();
	private ArrayList<Evenement> arrayEvenementsEnCours = new ArrayList<Evenement>();
	private ArrayList<Evenement> arrayEvenementsPasses = new ArrayList<Evenement>();

	protected LocationManager locationManagerAutomatic;
	protected LocationListener locationListenerAutomatic;
	
	private AccesBDDAjouterMarqueurs accesBDDAjouterMarqueurs;

	//methode de rafraichissement automatique de la position
	//http://developer.android.com/guide/topics/location/strategies.html
	public void managerDePositionAutomatique() {

		// Acquire a reference to the system Location Manager
		locationManagerAutomatic = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		locationListenerAutomatic = new LocationListener() {

			public void onProviderEnabled(String provider) {
				//on envoie toutes les positions en attente

				//on vide la liste des marqueurs si c'est une reussite
			}

			public void onProviderDisabled(String provider) {}

			@Override
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location provider.

				//si aucun evenement est en cours, on stop l'ecouteur de position
				//PLUS SI ON PARTAGE SA POSITION POUR L'EVENEMENT
				if(arrayEvenementsEnCours.size()==0){
					System.out.println("Aucun événement en cours, on stop la mise a jour automatique de la position");
					stopManagerDePositionAutomatique();
					return;
				}

				int nombreDevenementsEnCours;
				nombreDevenementsEnCours = arrayEvenementsEnCours.size();

				for(int j=0;j<nombreDevenementsEnCours;j++){
					GeoPoint geoPointActuel = new GeoPoint(location.getLatitude(),location.getLongitude());
					Marqueur marqueurPositionActuelle = new Marqueur(geoPointActuel);
					marqueurPositionActuelle.setParticipant(Utilisateur.getUtilisateur());
					marqueurPositionActuelle.setEvenement(arrayEvenementsEnCours.get(j));
					Utilisateur.ajouterMarqueursAEnvoyer(marqueurPositionActuelle);
				}
				//si il y a une connexion internet
				if(TesteurConnexionInternet.estConnecte(context)){
					new EnvoyerMarqueurAutomaticAsync().execute();
					System.out.println("Position mise a jour automatiquement et doit etre envoyee sur le serveur");
				}
				//sinon on enregistre la position dans les marqueurs a envoyer
				else{
					System.out.println("Position mise a jour automatiquement stockée dans la liste car pas internet");
				}
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};

		// Register the listener with the Location Manager to receive location updates
		//au minimum 2 minutes et 3 mètres
		locationManagerAutomatic.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 3, locationListenerAutomatic);
		locationManagerAutomatic.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 3, locationListenerAutomatic);
		//Toast.makeText(context,"Ecouteur de position automatique démarré",Toast.LENGTH_SHORT).show();

		//on l'ajoute a la liste des Manager automatiques ainsi que son listener
		//listeLocationManagerAutomatic.add(locationManagerAutomatic);
		//listeListenerManagerAutomatic.add(locationListenerAutomatic);
		//}
		//si true alors
		//on essaie d'envoyer les positions stockées dans marqueursAEnvoyer
		//retour 1 : succes, les positions en attente ont été envoyées au serveur, on peut raffraichir la carte et vider la liste marqueurAEnvoyer
		//retour 0 : echec, on réessaie, on ne vide pas la liste
		//si false alors
		//on affiche un message toast "Position enregistrée. Elle sera envoyée à la prochaine connexion à internet."
	}

	class EnvoyerMarqueurAutomaticAsync extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			//on doit rendre le bouton mise a jour de position inclicable

		}
		protected String doInBackground(String... args) {
			resultatEnvoiMarqueurs = accesBDDAjouterMarqueurs.ajouterMarqueur(Utilisateur.getMarqueursAEnvoyer());
			return null;
		}
		protected void onPostExecute(String file_url) {
			//on reactive le bouton de mise a jour de position

			if(resultatEnvoiMarqueurs==1){
				//envoi reussi
				Toast.makeText(context,"Envoi du marqueur réussi",Toast.LENGTH_SHORT).show();
				System.out.println("Position mise a jour automatiquement");
				//on vide le tableau de marqueurs a envoyer
				Utilisateur.videMarqueursAEnvoyer();
				return;
			}
			else{
				Toast.makeText(context,R.string.probleme_internet,Toast.LENGTH_SHORT).show();
				return;
			}
		}
	}

	public void stopManagerDePositionAutomatique(){
		if(locationManagerAutomatic!=null){
			locationManagerAutomatic.removeUpdates(locationListenerAutomatic);
			//Toast.makeText(context,"Ecouteur de position automatique arrété",Toast.LENGTH_SHORT).show();
		}
	}

	public ModeleListeEvenements(Context context){
		this.context = context;
		this.accesBDDAjouterMarqueurs = new AccesBDDAjouterMarqueurs();
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
		//si la date actuelle a ete initialisée
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
			//si l'événement finit apres la date actuelle
			if(evt.getDateFinEvt()>this.dateActuelle){
				//si en plus, il commence apres la date actuelle, c'est un evenement A VENIR
				if(evt.getDateDebutEvt()>this.dateActuelle){
					arrayEvenementsAVenir.add(evt);
				}
				//sinon c'est un événement EN COURS 
				else{
					arrayEvenementsEnCours.add(evt);
				}
			}
			//sinon c'est un événement PASSE
			else{
				arrayEvenementsPasses.add(evt);
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
			stringEvenements[i] = evt.getNomEvt()+" - "+CVPAvecDate+"/"+evt.getRole();
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
