package moduleCarte;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import moduleConnexion.ActiviteConnexion;

import outils.Diff;
import outils.TesteurConnexionInternet;
import grandModele.Marqueur;
import grandModele.Personne;
import singletons.EvenementCourant;
import singletons.Utilisateur;
import spotevent.fabien.un.R;
import accesBDD.AccesBDDAjouterMarqueurs;
import accesBDD.AccesBDDCompleterEvenement;
import accesBDD.AccesBDDconnexion;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;

public class ModeleCarte {
	protected MyLocationOverlay myLocationOverlay;
	protected MapView carte;
	protected Context context;
	protected final AnnotationView annotation;
	protected final ArrayList<Overlay> overlayTraces;
	protected boolean trace;

	//Manager et ecouteur de position pour la mise a jour manuelle
	protected LocationManager locationManagerManual;
	protected LocationListener locationListenerManual;
	protected ArrayList<Marqueur> listeMarqueurManuel;
	protected int resultatEnvoiMarqueurs;
	
	protected AccesBDDAjouterMarqueurs accesBDDAjouterMarqueurs;

	public ModeleCarte(Context context,MapView map){
		this.carte = map;
		this.context = context;
		myLocationOverlay = new MyLocationOverlay(context, map);
		annotation = new AnnotationView(carte);
		overlayTraces = new ArrayList<Overlay>();
		//la trace est désactivée par défaut
		this.trace = false;
		// Acquire a reference to the system Location Manager
		locationManagerManual = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		listeMarqueurManuel = new ArrayList<Marqueur>();
		
		accesBDDAjouterMarqueurs = new AccesBDDAjouterMarqueurs();
	}

	public void setMap(){
		carte.getController().setZoom(14);
		carte.getController().setCenter(new GeoPoint(47.302516,-1.485268));
		carte.setBuiltInZoomControls(true);
		//ajout du listener qui fermera l'annotation ouverte lorsqu'on clique sur la carte
		carte.addMapViewEventListener(new MapView.MapViewEventListener() {
			@Override
			public void longTouch(MapView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mapLoaded(MapView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void move(MapView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void moveEnd(MapView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void moveStart(MapView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void touch(MapView arg0) {
				annotation.hide();
			}

			@Override
			public void zoomEnd(MapView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void zoomStart(MapView arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void envoyerMarqueursEnAttente(){

	}
	
	public void completerEvenementAsync(){
		new CompleterEvenementAsync().execute();
	}
	
	class CompleterEvenementAsync extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
		}
		protected String doInBackground(String... args) {
			AccesBDDCompleterEvenement accesBDDCompleterEvenement = new AccesBDDCompleterEvenement(context);
			accesBDDCompleterEvenement.completerEvenement(EvenementCourant.getEvenementCourant().getIdEvt());
			return null;
		}
		protected void onPostExecute(String file_url) {
			
			afficherDerniersMarqueurs();
			miseAJourTraces();
			//Toast.makeText(context, "Evt complété",Toast.LENGTH_SHORT).show();
		}
	}

	public void miseAJourPositionManuelle(){

		// Define a listener that responds to location updates
		locationListenerManual = new LocationListener() {

			public void onProviderEnabled(String provider) {
				//on envoie toutes les positions en attente
			}

			public void onProviderDisabled(String provider) {}

			@Override
			public void onLocationChanged(Location location) {
				//A FAIRE EN BACKGROUND
				
				// Called when a new location is found by the network location provider.
				GeoPoint geoPointActuel = new GeoPoint(location.getLatitude(),location.getLongitude());
				Marqueur marqueurPositionActuelle = new Marqueur(geoPointActuel);
				marqueurPositionActuelle.setParticipant(Utilisateur.getUtilisateur());
				marqueurPositionActuelle.setEvenement(EvenementCourant.getEvenementCourant());
				listeMarqueurManuel.add(marqueurPositionActuelle);

				//si il y a une connexion internet
				if(TesteurConnexionInternet.estConnecte(context)){
					System.out.println("Envoi manuel de la position");
					new EnvoyerMarqueurManuelAsync().execute();
				}
				//sinon on enregistre la position dans les marqueurs a envoyer
				else{
					Toast.makeText(context, "Votre position a été enregistrée. Elle sera envoyée lors de la prochaine connexion à Internet.",Toast.LENGTH_LONG).show();
				}
				//on stoppe le manager de mise a jour manuelle dès qu'une position a été trouvée
				locationManagerManual.removeUpdates(locationListenerManual);
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};

		// Register the listener with the Location Manager to receive location updates
		locationManagerManual.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerManual);
		locationManagerManual.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerManual);
	}

	class EnvoyerMarqueurManuelAsync extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			//on doit rendre le bouton mise a jour de position inclicable

		}
		protected String doInBackground(String... args) {
			resultatEnvoiMarqueurs = accesBDDAjouterMarqueurs.ajouterMarqueur(listeMarqueurManuel);
			return null;
		}
		protected void onPostExecute(String file_url) {
			//on reactive le bouton de mise a jour de position

			if(resultatEnvoiMarqueurs==1){
				//envoi reussi
				Toast.makeText(context,"Envoi du marqueur réussi",Toast.LENGTH_SHORT).show();
				System.out.println("Position mise a jour manuellement");
				completerEvenementAsync();
				carte.getController().animateTo(listeMarqueurManuel.get(0).getGeo());
				carte.getController().setZoom(14);
				//on met les traces a jours
				miseAJourTraces();
				//on vide le tableau de marqueurs a envoyer
				listeMarqueurManuel.clear();
				return;
			}
			else{
				Toast.makeText(context,R.string.probleme_internet,Toast.LENGTH_SHORT).show();
				//on vide le tableau de marqueurs a envoyer
				listeMarqueurManuel.clear();
				return;
			}
		}
	}

	public void afficherDerniersMarqueurs(){
		//on commence par vider la carte de ses marqueurs
		carte.getOverlays().clear();
		Iterator<Personne> iterator = EvenementCourant.getEvenementCourant().getParticipants().iterator();
		while (iterator.hasNext()){
			Personne prs = (Personne) iterator.next();
			int nombreMarqueurs = prs.getMarqueurs().size();
			if(nombreMarqueurs>0){
				placerMarqueur(prs.getMarqueurs().get(nombreMarqueurs-1));
			}
		}
	}

	public void afficherTraceParticipant(Personne prs){
		System.out.println("Veut afficher la trace");
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		int numPersonne = (prs.getNumPersonne()%5);
		int couleurTrace;
		switch (numPersonne){
		case 0:
			couleurTrace = context.getResources().getColor(R.color.ROSE);break;
		case 1:
			couleurTrace = context.getResources().getColor(R.color.ROUGE);break;
		case 2:
			couleurTrace = context.getResources().getColor(R.color.ORANGE);break;
		case 3:
			couleurTrace = context.getResources().getColor(R.color.VERT);break;
		case 4:
			couleurTrace = context.getResources().getColor(R.color.BLEU);break;
		default:
			couleurTrace = context.getResources().getColor(R.color.ROSE);
		}
		paint.setColor(couleurTrace);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(4);
		LineOverlay traces = new LineOverlay(paint);
		traces.setData(prs.getGeoPoints());
		overlayTraces.add(traces);
		carte.getOverlays().add(traces);
	}

	public void afficherTracesParticipants(){
		//Toast.makeText(context, "Veut afficher les traces",Toast.LENGTH_SHORT).show();
		Iterator<Personne> iterator = EvenementCourant.getEvenementCourant().getParticipants().iterator();
		while (iterator.hasNext()){
			Personne participant = (Personne) iterator.next();
			int nombreMarqueurs = participant.getMarqueurs().size();
			if(nombreMarqueurs>0){
				afficherTraceParticipant(participant);
			}
		}
	}

	public void cacherTracesParticipants(){
		//Toast.makeText(context, "Veut cacher les traces",Toast.LENGTH_SHORT).show();
		System.out.println("Veut cacher les traces");
		Iterator<Overlay> iteratorOverlay = overlayTraces.iterator();
		while (iteratorOverlay.hasNext()){
			Overlay overlayTrace = (Overlay) iteratorOverlay.next();
			overlayTrace.destroy();
		}
	}

	public void miseAJourTraces(){
		//si l'utilisateur a activé les traces
		if(trace){
			afficherTracesParticipants();
			//petite bidouille qui centre la carte sur son centre pour la prevenir d'un changement
			carte.setFocalPoint(carte.getFocalPoint());
		}
	}

	public void toogleTraces(){
		//si l'utilisateur veut que les traces soient cachées alors qu'elles étaient affichées
		if(trace){
			cacherTracesParticipants();
			//petite bidouille qui centre la carte sur son centre pour la prevenir d'un changement
			carte.setFocalPoint(carte.getFocalPoint());
			overlayTraces.clear();
			trace = false;
		}
		//si l'utilisateur veut que les traces soient affichées alors qu'elles étaient cachées
		else{
			afficherTracesParticipants();
			//petite bidouille qui centre la carte sur son centre pour la prevenir d'un changement
			carte.setFocalPoint(carte.getFocalPoint());
			trace = true;
		}
	}

	public void placerMarqueur(Marqueur mrq){

		//sert pour l'annotation
		final Marqueur marqueur = mrq;
		Drawable icon;

		icon = mrq.getParticipant().getDernierMarqueurIconePersonne();

		final DefaultItemizedOverlay poiOverlay = new DefaultItemizedOverlay(icon);

		//creation d'un objet diff pour le calcul de il y a
		Diff diff = new Diff();

		// set GeoPoints and title/snippet to be used in the annotation view 
		OverlayItem poi = new OverlayItem(mrq.getGeo(), mrq.getParticipant().getPrenomPersonne()+" "+mrq.getParticipant().getNomPersonne(), diff.il_y_a(mrq.getDate()));
		poiOverlay.addItem(poi);

		// add a tap listener for the POI overlay 
		poiOverlay.setTapListener(new ItemizedOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint pt, MapView mapView) {
				// when tapped, show the annotation for the overlayItem 
				int lastTouchedIndex = poiOverlay.getLastFocusedIndex();
				if(lastTouchedIndex>-1){
					OverlayItem tapped = poiOverlay.getItem(lastTouchedIndex);
					System.out.println(lastTouchedIndex);
					annotation.hide();
					annotation.showAnnotationView(tapped);
				}
			}
		});
		carte.getOverlays().add(poiOverlay);
	}
}
