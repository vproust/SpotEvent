package moduleCarte;

import java.util.ArrayList;
import java.util.Iterator;

import moduleConnexion.ActiviteConnexion;

import outils.Diff;
import outils.TesteurConnexionInternet;
import grandModele.Marqueur;
import grandModele.Personne;
import singletons.EvenementCourant;
import singletons.Utilisateur;
import spotevent.fabien.un.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
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

	//Manager et ecouteur de position pour la mise a jour automatique
	protected LocationManager locationManagerAutomatic;
	protected LocationListener locationListenerAutomatic;

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
		locationManagerAutomatic = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		//on lance le manager de mise a jour de position automatique
		managerDePositionAutomatique();
	}

	public void setMap(){
		carte.getController().setZoom(14);
		carte.getController().setCenter(new GeoPoint(48.109638,-1.651318));
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
	
	public void miseAJourPositionManuelle(){

		// Define a listener that responds to location updates
		locationListenerManual = new LocationListener() {

			public void onProviderEnabled(String provider) {
				//on envoie toutes les positions en attente
			}

			public void onProviderDisabled(String provider) {}

			@Override
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location provider.
				GeoPoint geoPointActuel = new GeoPoint(location.getLatitude(),location.getLongitude());
				Marqueur marqueurPositionActuelle = new Marqueur(geoPointActuel);
				marqueurPositionActuelle.setEstDernier(true);
				marqueurPositionActuelle.ajouterMarqueur(Utilisateur.getUtilisateur());
				//si il y a une connexion internet
				if(TesteurConnexionInternet.estConnecte(context)){
					placerMarqueur(marqueurPositionActuelle);
					System.out.println("Position mise a jour manuellement");
					carte.getController().animateTo(geoPointActuel);
					carte.getController().setZoom(14);
				}
				//sinon on enregistre la position dans les marqueurs a envoyer
				else{
					Utilisateur.ajouterMarqueursAEnvoyer(marqueurPositionActuelle);
					Toast.makeText(context, "Votre position a été enregistrée. Elle sera envoyée lors de la prochaine connexion à Internet.",Toast.LENGTH_LONG).show();
				}
				//on stoppe le manager de mise a jour manuelle dès qu'une position a été trouvée
				locationManagerManual.removeUpdates(locationListenerManual);
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};

		// Register the listener with the Location Manager to receive location updates
		//locationManagerManual.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerManual);
		locationManagerManual.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerManual);
	}
	
	public void stopManagerDePositionAutomatique(){
		locationManagerAutomatic.removeUpdates(locationListenerAutomatic);
	}

	//http://developer.android.com/guide/topics/location/strategies.html
	public void managerDePositionAutomatique() {

		// Acquire a reference to the system Location Manager
		locationManagerAutomatic = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		locationListenerAutomatic = new LocationListener() {

			public void onProviderEnabled(String provider) {
				//on envoie toutes les positions en attente
			}

			public void onProviderDisabled(String provider) {}

			@Override
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location provider.
				GeoPoint geoPointActuel = new GeoPoint(location.getLatitude(),location.getLongitude());
				Marqueur marqueurPositionActuelle = new Marqueur(geoPointActuel);
				marqueurPositionActuelle.setEstDernier(true);
				marqueurPositionActuelle.ajouterMarqueur(Utilisateur.getUtilisateur());
				//si il y a une connexion internet
				if(TesteurConnexionInternet.estConnecte(context)){
					placerMarqueur(marqueurPositionActuelle);
					//petite bidouille qui centre la carte sur son centre pour la prevenir d'un changement
					carte.setFocalPoint(carte.getFocalPoint());
					System.out.println("Position mise a jour automatiquement");
				}
				//sinon on enregistre la position dans les marqueurs a envoyer
				else{
					Utilisateur.ajouterMarqueursAEnvoyer(marqueurPositionActuelle);
				}
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};

		// Register the listener with the Location Manager to receive location updates
		//au minimum 2 minutes et 3 mètres
		//locationManagerAutomatic.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120000, 3, locationListenerAutomatic);
		locationManagerAutomatic.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 3, locationListenerAutomatic);
		
		//si true alors
		//on essaie d'envoyer les positions stockées dans marqueursAEnvoyer
		//retour 1 : succes, les positions en attente ont été envoyées au serveur, on peut raffraichir la carte et vider la liste marqueurAEnvoyer
		//retour 0 : echec, on réessaie, on ne vide pas la liste
		//si false alors
		//on affiche un message toast "Position enregistrée. Elle sera envoyée à la prochaine connexion à internet."
	}

	public void afficherDerniersMarqueurs(){
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

		// initialize the annotation to be shown later
		if(mrq.EstDernier()){
			icon = context.getResources().getDrawable(mrq.getParticipant().getDernierMarqueurIconePersonne());
		}
		else{
			icon = context.getResources().getDrawable(mrq.getParticipant().getPasDernierMarqueurIconePersonne());
		}
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
